package it.polimi.ingsw.PSP54.client;

import it.polimi.ingsw.PSP54.Ping;
import it.polimi.ingsw.PSP54.client.gui.GuiManager;
import it.polimi.ingsw.PSP54.client.cli.*;
import it.polimi.ingsw.PSP54.observer.Observable;
import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Timer;

public class Client extends Observable<GameMessage> {

    private String ip = null;
    private final Scanner inputReader = new Scanner(System.in);
    private final int port;
    private ObjectOutputStream socketOut;
    private boolean active = true;
    private Thread t;

    public Client(int port) {
        //this.ip = ip;
        this.port = port;
    }

    /**
     * Instantiates a thread that reads incoming messages from the client.
     * @param socketIn the socket from which the messages arrive.
     */
    public synchronized Thread asyncReadFromSocket(final ObjectInputStream socketIn){
         t = new Thread(() -> {
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
            CliView cliView = new CliView(this);
            addObserver(cliView);
        }
        if (choice.equals("g")){
            GuiManager guiManager = GuiManager.getInstance(this);
            addObserver(guiManager);
        }
    }

    /**
     * Verifies whether a String represents a reachable IP address.
     * @param ip the IP address to reach.
     * @return true if ip is reachable, false otherwise.
     */
    private boolean checkIpAddr(String ip) {
        boolean isReachable;

        InetAddress server = null;
        try {
            server = InetAddress.getByName(ip);
            isReachable = server.isReachable(1000);
        } catch (IOException e) {
            isReachable = false;
        }
        return isReachable;
    }


    /**
     * Once acquired the interface choice establishes a connection with the server.
     * It also starts two different thread to menage the socket reading/writing.
     */
    public void startClient() {
        System.out.println("CLI or GUI? [enter c or g]");
        String choice = inputReader.next();
        while (!choice.equals("c") && !choice.equals("g")) {
            System.out.println("ERROR [enter c or g]");
            choice = inputReader.next();
        }
        setInterfaceChoice(choice);
        //set & check IP address
        System.out.println("Enter the IP address of a server you want to connect: ");
        ip = inputReader.next();
        while (!checkIpAddr(ip)) {
            System.out.println("ERROR: Server unreachable, try again!");
            ip = inputReader.next();
        }
        Socket socket = null;
        try {
            socket = new Socket(ip, port);
            Objects.requireNonNull(socket).setSoTimeout(5000);
        } catch (IOException e) {
            GameMessage dropconnection = new StringMessage(null, StringMessage.EndForDisconnection);
            notify(dropconnection);
        }
        System.out.println("Connection established");
        ObjectInputStream socketIn = null;
        try{
            socketIn = new ObjectInputStream(Objects.requireNonNull(socket).getInputStream());
            socketOut = new ObjectOutputStream(socket.getOutputStream());
            Thread t0 = asyncReadFromSocket(socketIn);
            t0.join();
        } catch(NoSuchElementException | InterruptedException | IOException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            //noinspection StatementWithEmptyBody
            while (isActive()) {

            }
            if (!isActive()) {
                try {
                    Objects.requireNonNull(socketIn).close();
                    socketOut.close();
                    socket.close();
                }catch (IOException e){
                    e.printStackTrace();
                    System.out.println("ciao");
                }
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
        t.checkAccess();
    }


}