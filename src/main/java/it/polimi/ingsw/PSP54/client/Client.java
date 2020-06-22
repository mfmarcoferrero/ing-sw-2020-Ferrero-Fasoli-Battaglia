package it.polimi.ingsw.PSP54.client;

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
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client extends Observable<GameMessage> {

    private final Scanner inputReader = new Scanner(System.in);
    private final int port;
    private ObjectOutputStream socketOut;
    private boolean active = true;
    private ObjectInputStream socketIn;
    public Thread readingTask;


    public Client(int port) {
        this.port = port;
    }

    /**
     * Instantiates a thread that reads incoming messages from the client.
     * @param socketIn the socket from which the messages arrive.
     */
    public synchronized Thread asyncReadFromSocket(final ObjectInputStream socketIn){
         readingTask = new Thread(() -> {
             try {
                 while (isActive()) {
                     Object inputObject = socketIn.readObject();
                     Client.this.notify((GameMessage)inputObject);
                 }
             } catch (Exception e) {
                 GameMessage connectionClosed = new StringMessage(null, StringMessage.closedConnection);
                 notify(connectionClosed);
             }
         });
        readingTask.start();
        return readingTask;
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
     * Verifies whether a String is a reachable IP address.
     * @param ipAddr the IP address to reach.
     * @return true if is reachable, false otherwise.
     */
    public boolean checkIpAddr(String ipAddr) {
        boolean isReachable;
        try {
            InetAddress server = InetAddress.getByName(ipAddr);
            isReachable = server.isReachable(1000);
        } catch (IOException e) {
            isReachable = false;
        }
        return isReachable;
    }

    /**
     *
     * @param socket
     */
    public synchronized void ping(Socket socket) {
        new Thread(() -> {
            InetAddress clientIP = socket.getInetAddress();
            while (true) {
                try {
                    if (!clientIP.isReachable(5000))
                        break;
                } catch (IOException e) {
                    break;
                }
            }
            System.out.println("Server unreachable, retry later.");
        });
    }

    /**
     * Once acquired the interface choice establishes a connection with the server.
     * It also starts two different thread to menage the socket reading/writing.
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
        //set & check IP
        System.out.println("Enter the IP address of a server you want to connect: ");
        String ip = inputReader.next();
        while (!checkIpAddr(ip)) {
            System.out.println("ERROR: Server unreachable, try again!");
            ip = inputReader.next();
        }
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        try {
            //ping(socket);
            Thread t = asyncReadFromSocket(socketIn);
            t.join();
        } catch(NoSuchElementException | InterruptedException e) {
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

}