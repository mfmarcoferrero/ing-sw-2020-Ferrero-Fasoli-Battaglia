package it.polimi.ingsw.PSP54.client;

import it.polimi.ingsw.PSP54.client.view.*;
import it.polimi.ingsw.PSP54.observer.Observable;
import it.polimi.ingsw.PSP54.utils.messages.GameMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

public class Client extends Observable<GameMessage> {

    private final String ip;
    private final int port;
    private ObjectOutputStream socketOut;
    private final CliView cliView;
    //private GuiView guiView = new GuiView(this);
    private int playerInd;
    private boolean active = true;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.cliView = new CliView(this);
        addObserver(cliView);
        //addObserver(guiView);
    }

    /**
     * Instantiates a thread that reads incoming messages from the client.
     * @param socketIn the socket from which the messages arrive.
     * @return the thread that is reading the current incoming message.
     */
    public synchronized Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    Object inputObject = socketIn.readObject();
                    Client.this.notify((GameMessage)inputObject);
                }
            } catch (Exception e) {
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    public void send(Object message) {
        try {
            socketOut.reset();
            socketOut.writeObject(message);
            socketOut.flush();
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Instantiates a thread that sends an object via socket.
     * @param message the message to be send.
     * @return the thread that is sending the current message.
     */
    public Thread asyncSend(final Object message) {
        Thread t = new Thread(() -> send(message));
        t.start();
        return t;
    }

    /**
     * Ogni client crea due thread per la lettura e scrittura
     * Ogni thread viene eseguito in modo asincrono e il client si disconnette quando terminano
     * @throws IOException
     */
    public void startClient() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());

        try {
            Thread t0 = asyncReadFromSocket(socketIn);
            t0.join();
        } catch(InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

}