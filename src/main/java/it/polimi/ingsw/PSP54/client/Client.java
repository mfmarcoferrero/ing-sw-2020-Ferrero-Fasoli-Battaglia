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
    private int clientID;
    private boolean active = true;
    private Thread t;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.cliView = new CliView(this);
        addObserver(cliView);
        //addObserver(guiView);
    }

    /**
     * Thread per la lettura di ciò che arriva dal server
     * Per ogni tipo di messaggio ricevuto viene eseguita un azione della view o una scrittura su terminale
     * @param socketIn
     * @return
     */
    public synchronized Thread asyncReadFromSocket(final ObjectInputStream socketIn){
         t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        Client.this.notify((GameMessage)inputObject);
                    }
                } catch (Exception e) {
                    setActive(false);
                }
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
     * Invio asincrono di un oggetto al server
     * Viene istanziato un thread che esegue l'operzione di writeObject e flush
     * @param message
     */
    public void asyncWriteToSocket(final Object message) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
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

    public void SuspendThread(){
        t.suspend();
    }
}