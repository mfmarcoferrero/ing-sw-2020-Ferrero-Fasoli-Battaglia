package it.polimi.ingsw.PSP54.client;

import it.polimi.ingsw.PSP54.client.gui.GuiManager;
import it.polimi.ingsw.PSP54.client.view.*;
import it.polimi.ingsw.PSP54.observer.Observable;
import it.polimi.ingsw.PSP54.utils.messages.GameMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client extends Observable<GameMessage> {

    private String ip;
    private final Scanner inputReader = new Scanner(System.in);
    private int port;
    private ObjectOutputStream socketOut;
    private CliView cliView;
    private GuiManager guiManager;
    private boolean active = true;
    private Thread t;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Instantiates a thread that reads incoming messages from the client.
     * @param socketIn the socket from which the messages arrive.
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

    /**
     * Sends an object via socket.
     * @param message the message to be sent.
     */
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
     * @param message the message to be sent.
     */
    public void asyncSend(final Object message) {
        Thread t = new Thread(() -> send(message));
        t.start();
    }

    /**
     * Initializes which user interface the player has selected.
     * @param choice the object representing the player's choice.
     */
    private void setInterfaceChoice(String choice){
        if (choice.equals("c")){
            cliView = new CliView(this);
            addObserver(cliView);
        }
        if (choice.equals("g")){
            guiManager = GuiManager.getInstance(this);
            addObserver(guiManager);
        }
    }

    /**
     * Ogni client crea due thread per la lettura e scrittura
     * Ogni thread viene eseguito in modo asincrono e il client si disconnette quando terminano
     * @throws IOException if an I/O error occurs when creating the socket.
     */
    public void startClient() throws IOException {
        System.out.println("CLI or GUI? [enter c or g]");
        String choice = inputReader.next();
        while (!choice.equals("c") && !choice.equals("g")) {
            System.out.println("ERROR [enter c or g]");
            choice = inputReader.next();
        }
        setInterfaceChoice(choice);
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        try {
            Thread t0 = asyncReadFromSocket(socketIn);
            //t0.join();
        } catch(NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            //noinspection StatementWithEmptyBody
            while (isActive()) {

            }
            if (!isActive()) {
                socketIn.close();
                socketOut.close();
                socket.close();
            }
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