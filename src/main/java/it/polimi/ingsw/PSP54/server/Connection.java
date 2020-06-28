package it.polimi.ingsw.PSP54.server;

import it.polimi.ingsw.PSP54.client.gui.NumberOfPlayersSceneController;
import it.polimi.ingsw.PSP54.observer.*;
import it.polimi.ingsw.PSP54.server.virtualView.VirtualView;

import it.polimi.ingsw.PSP54.utils.PingMessage;
import it.polimi.ingsw.PSP54.utils.choices.*;
import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.LobbyAccessMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


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
    private ScheduledExecutorService pingService = Executors.newScheduledThreadPool(1);

    public Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     *
     */
    private static class PingSender implements Runnable {

        private final ObjectOutputStream outputStream;

        public PingSender(ObjectOutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public void run() {
            synchronized (outputStream) {
                try {
                    outputStream.reset();
                    outputStream.writeObject(new PingMessage());
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @param output
     */
    public void ping(ObjectOutputStream output) {
        pingService.scheduleAtFixedRate(new PingSender(output), 0, 2500, TimeUnit.MILLISECONDS);
    }

    /**
     * Sends an object via socket.
     * @param message the object to be sent.
     */
    public synchronized void send(Object message) {
        //noinspection SynchronizeOnNonFinalField
        synchronized (out) {
            try {
                out.reset();
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                    if (inputObject instanceof NumberOfPlayers) {
                        server.setNumberOfPlayers(((NumberOfPlayers) inputObject).getNumberOfPlayers());
                        server.lobby(this, this.getCredentials());
                    }
                    if (inputObject instanceof StopPlayingChoice)
                        close();
                    else if (inputObject instanceof NewGameChoice)
                        server.reinsertConnection(this);
                    else if (inputObject instanceof PlayerChoice)
                        Connection.this.notify((PlayerChoice) inputObject);
                }
            } catch (Exception e) {
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    /**
     * Closes the connection.
     */
    public synchronized void closeConnection(){
        try {
            socket.close();
            System.out.println(getCredentials().getPlayerName() + "'s connection closed");
        } catch (IOException e) {
            e.printStackTrace();
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
            boolean namExist;
            do {
                if (i == 0) {
                    GameMessage welcome = new StringMessage(null, StringMessage.welcomeMessage);
                    asyncSend(welcome);
                } else {
                    GameMessage invalidName = new StringMessage(null, StringMessage.nameAlreadyTaken);
                    asyncSend(invalidName);
                }
                setCredentials((PlayerCredentials) in.readObject());
                namExist = server.checkName(getCredentials().getPlayerName());
                i++;
            } while (namExist);
            if (gameMaster || this == server.currentConnections.firstElement()) {
                GameMessage setPlayersNumber = new StringMessage(null, StringMessage.setNumberOfPlayersMessage);
                asyncSend(setPlayersNumber);
                server.setNumberOfPlayers(((NumberOfPlayers)in.readObject()).getNumberOfPlayers());
            }
            GameMessage lobbyAccessMessage = new LobbyAccessMessage(null);
            send(lobbyAccessMessage);
            server.lobby(this, credentials);
            ping(out);
            socket.setSoTimeout(5000);
            Thread t0 = asyncReadFromSocket(in);
            t0.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pingService.shutdown();
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
