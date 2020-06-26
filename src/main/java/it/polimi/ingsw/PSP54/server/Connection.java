package it.polimi.ingsw.PSP54.server;

import it.polimi.ingsw.PSP54.observer.*;
import it.polimi.ingsw.PSP54.server.virtualView.VirtualView;

import it.polimi.ingsw.PSP54.utils.choices.NewGameChoice;
import it.polimi.ingsw.PSP54.utils.choices.PlayerChoice;
import it.polimi.ingsw.PSP54.utils.choices.PlayerCredentials;
import it.polimi.ingsw.PSP54.utils.choices.StopPlayingChoice;
import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;



/**
 * Represents the connection between Server and a Client. It can send an read object.
 */
public class Connection extends Observable<PlayerChoice> implements Runnable {

    private final Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final Server server;
    private PlayerCredentials credentials;
    private boolean active = true;
    private boolean gameMaster  = false;
    private int gameID;
    private VirtualView virtualView;

    public Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * Sends an object via socket.
     * @param message the object to be sent.
     */
    public synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Instantiates a thread that sends an object via socket.
     * @param message the message to be sent.
     * @return the thread that is sending the current message.
     */
    public Thread asyncSend(final Object message) {
        Thread t = new Thread(() -> send(message));
        t.start();
        return t;
    }

    /**
     * Instantiates a thread that reads incoming messages from the client.
     * @param socketIn the socket from which the messages arrive.
     * @return the thread that is reading the current incoming message.
     */
    public synchronized Thread asyncReadFromSocket(final ObjectInputStream socketIn) {
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    Object inputObject = socketIn.readObject();

                    if (inputObject instanceof StopPlayingChoice)
                        close();
                    if (inputObject instanceof NewGameChoice)
                        server.reinsertConnection(this);
                    else
                        Connection.this.notify((PlayerChoice) inputObject);
                }
            } catch (Exception e) {
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    private static boolean isReachable(Socket socket, int timeOutMillis) {
       try {
            try (Socket soc = new Socket()) {
                soc.connect(socket.getRemoteSocketAddress(), timeOutMillis);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Checks if the associated client is still reachable, if not closes the connection.
     * @param socket the socket
     */
    public synchronized void ping(Socket socket) {
        new Thread(() -> {
            boolean loop = true;
            while (loop) {
                if (!isReachable(socket, 5000))
                    loop = false;
            }
            close();
        }).start();
    }

    /**
     * Closes the connection.
     */
    public synchronized void closeConnection(){
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        active = false;
    }

    /**
     * Closes the connection and deletes its reference in the server.
     */
    private void close() {
        closeConnection();
        server.deregisterConnection(this);
    }

    /**
     *
     */
    public void getNumberOfPlayers() {
        GameMessage setPlayersNumber = new StringMessage(null, StringMessage.setNumberOfPlayersMessage);
        asyncSend(setPlayersNumber);
        try {
            int numberOfPlayers = (int) in.readObject();
            server.setNumberOfPlayers(numberOfPlayers);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Each instantiated connection is launched as a thread.
     * Each connection calls the Server.lobby() method.
     * As long as the connection is active the thread listens for what is sent from the client.
     * The MessageReceiver is notified every time a message arrives.
     */
    @Override
    public void run() {
        int i=0;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            PlayerCredentials credentials;
            boolean namExist;
            do {
                if (i == 0) {
                    GameMessage welcome = new StringMessage(null, StringMessage.welcomeMessage);
                    asyncSend(welcome);
                } else {
                    GameMessage invalidName = new StringMessage(null, StringMessage.nameAlreadyTaken);
                    asyncSend(invalidName);
                }
                credentials = (PlayerCredentials) in.readObject();
                setCredentials(credentials);
                namExist = server.checkName(getCredentials().getPlayerName());
                i++;
            } while (namExist);
            if (gameMaster || this == server.currentConnections.firstElement()) {
                GameMessage setPlayersNumber = new StringMessage(null, StringMessage.setNumberOfPlayersMessage);
                asyncSend(setPlayersNumber);
                int numberOfPlayers = (int) in.readObject();
                server.setNumberOfPlayers(numberOfPlayers);
            }
            server.lobby(this, credentials);
            ping(socket);
            Thread t0 = asyncReadFromSocket(in);
            t0.join();
        } catch(IOException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    //getters & setters

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setGameMaster(boolean gameMaster) {
        this.gameMaster = gameMaster;
    }

    private synchronized boolean isActive(){
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public PlayerCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(PlayerCredentials credentials) {
        this.credentials = credentials;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    public void setVirtualView(VirtualView virtualView) {
        this.virtualView = virtualView;
    }
}
