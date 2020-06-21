package it.polimi.ingsw.PSP54.server;

import it.polimi.ingsw.PSP54.observer.*;
import it.polimi.ingsw.PSP54.utils.choices.PlayerChoice;
import it.polimi.ingsw.PSP54.utils.choices.PlayerCredentials;
import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Represents the connection between Server and a Client. It can send an read object.
 */
public class Connection extends Observable<PlayerChoice> implements Runnable {

    private final Socket socket;
    private ObjectOutputStream out;
    private final Server server;
    private String name;
    private boolean active = true;
    private boolean gameMaster  = false;
    int numberOfPlayers;

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
        GameMessage connectionClosed = new StringMessage(null, StringMessage.closedConnection);
        asyncSend(connectionClosed);
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
     * Each instantiated connection is launched as a thread.
     * Each connection calls the Server.lobby() method.
     * As long as the connection is active the thread listens for what is sent from the client.
     * The MessageReceiver is notified every time a message arrives.
     */
    @Override
    public void run() {
        int i=0;
        try {
            socket.setSoTimeout(5000);
        } catch (SocketException e) {
            this.close();
        }
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
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
                this.name = credentials.getPlayerName();
                namExist = server.checkName(name);
                i++;
            } while (namExist);
            if (gameMaster || this == server.currentConnections.firstElement()) {
                GameMessage setPlayersNumber = new StringMessage(null, StringMessage.setNumberOfPlayersMessage);
                asyncSend(setPlayersNumber);
                numberOfPlayers = (int) in.readObject();
                server.setNumberOfPlayers(numberOfPlayers);
            }
            server.lobby(this, credentials);
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

    public void setGameMaster(boolean gameMaster) {
        this.gameMaster = gameMaster;
    }

    public String getName(){
        return name;
    }

    private synchronized boolean isActive(){
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
