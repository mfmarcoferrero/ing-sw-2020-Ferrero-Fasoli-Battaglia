package it.polimi.ingsw.PSP54.client;

import it.polimi.ingsw.PSP54.client.gui.GuiManager;
import it.polimi.ingsw.PSP54.client.cli.*;
import it.polimi.ingsw.PSP54.observer.Observable;
import it.polimi.ingsw.PSP54.utils.PingMessage;
import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client extends Observable<GameMessage> {

    private boolean active = true;
    private final Scanner inputReader = new Scanner(System.in);
    private final int port;
    private Socket socket;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;
    ScheduledExecutorService pingService = Executors.newScheduledThreadPool(1);
    public Thread readingTask;

    public Client(int port) {
        this.port = port;
    }

    private static class PingSender implements Runnable {

        private final ObjectOutputStream outputStream;
        private final Client client;

        public PingSender(Client client, ObjectOutputStream outputStream) {
            this.client = client;
            this.outputStream = outputStream;
        }

        /**
         * Sends a PingMessage via socket.
         */
        @Override
        public void run() {
            synchronized (outputStream) {
                try {
                    outputStream.reset();
                    outputStream.writeObject(new PingMessage());
                    outputStream.flush();
                } catch (IOException e) {
                    client.endClient();
                }
            }
        }
    }

    /**
     *
     */
    public void endClient(){
        GameMessage connectionClosed = new StringMessage(null, StringMessage.closedConnection);
        notify(connectionClosed);
        setActive(false);
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
                     if (inputObject instanceof GameMessage)
                        notify((GameMessage)inputObject);
                 }
             } catch (Exception e) {
                 endClient();
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
            //e.printStackTrace();
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
     * Launches a scheduled executor service that will handle the sending of ping messages.
     * @param output the ObjectOutputStream associated with the open socket.
     */
    public void ping(ObjectOutputStream output) {
        pingService.scheduleAtFixedRate(new PingSender(this, output), 0, 1000, TimeUnit.MILLISECONDS);
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
            System.out.println("ERROR [enter c/g]");
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
        setSocket(new Socket(ip, port));
        System.out.println("Connection established");
        socketIn = new ObjectInputStream(socket.getInputStream());
        setSocketOut(new ObjectOutputStream(socket.getOutputStream()));
        try {
            Thread t = asyncReadFromSocket(socketIn);
            t.join();
        } catch(NoSuchElementException | InterruptedException e) {
            System.out.println("Connection closed from the client side");
        }finally {
            //noinspection StatementWithEmptyBody
            while (isActive()) {

            }
            if (!isActive()) {
                pingService.shutdown();
                socketIn.close();
                socketOut.close();
                socket.close();
            }
        }
    }

    //getters & setters

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getSocketOut() {
        return socketOut;
    }

    public void setSocketOut(ObjectOutputStream socketOut) {
        this.socketOut = socketOut;
    }

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

}