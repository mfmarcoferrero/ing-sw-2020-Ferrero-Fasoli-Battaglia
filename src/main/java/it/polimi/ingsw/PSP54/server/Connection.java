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

/**
 * Represents the connection between Server and a Client. It can send an read object.
 */
public class Connection extends Observable<PlayerChoice> implements Runnable {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Server server;
    private String name;
    private boolean active = true;
    boolean gameMaster  = false;
    int numberOfPlayers;

    public Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }

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
     * Invio asincrono di un oggetto al client
     * Viene istanziato un thread che esegue l'operzione di writeObject e flush
     * @param message
     */
    public void asyncSend(final Object message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    /**
     * Instantiates a thread that reads incoming messages from the client
     * @param socketIn
     * @return
     */
    public Thread asyncReadFromSocket(final ObjectInputStream socketIn) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        Connection.this.notify((PlayerChoice) inputObject);
                    }
                } catch (Exception e) {
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public synchronized void closeConnection(){
        send("Connection closed from the server side");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        active = false;
    }

    private void close() {
        closeConnection();
        server.deregisterConnection(this);
    }

    /**
     * Ogni connection istanziata viene lanciata come thread
     * Per ogni thread di connection viene chiamato il metodo server.lobby()
     * Fino a quando isActive() il thread rimane in ascolto di ciò che viene inviato dal client
     * e per ogni messaggio ricevuto notifica MessageReceiver
     */
    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            GameMessage welcome = new StringMessage(null, StringMessage.welcomeMessage);
            asyncSend(welcome);
            in = new ObjectInputStream(socket.getInputStream());
            PlayerCredentials credentials = (PlayerCredentials) in.readObject();
            this.name = credentials.getPlayerName();
            if(gameMaster || this == server.currentConnections.firstElement()) {
                send(StringMessage.setNumberOfPlayersMessage);
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

    public Socket getSocket() {
        return socket;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
