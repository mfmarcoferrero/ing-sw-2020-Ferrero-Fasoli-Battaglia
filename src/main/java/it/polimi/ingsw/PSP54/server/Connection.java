package it.polimi.ingsw.PSP54.server;

import it.polimi.ingsw.PSP54.observer.*;
import it.polimi.ingsw.PSP54.server.model.StandardPlayer;
import it.polimi.ingsw.PSP54.utils.PlayerMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Connection extends Observable <String> implements Runnable {

    private Socket socket;
    private Scanner in;
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

    public void send(Object message) {
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
    public void asyncSend(final Object message){
        new Thread(() -> send(message)).start();
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
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("Welcome! What's your name?");
            name = in.nextLine();
            send("What's your age?");
            Integer age = null;
            while (age == null) {
                try {
                    age = in.nextInt();
                } catch (InputMismatchException e) {
                    System.err.println("Incorrect input!");
                }
            }
            if(gameMaster || this == server.currentConnections.firstElement()) {
                send("hey, set the number of player");
                numberOfPlayers =in.nextInt();
                while (numberOfPlayers <2 || numberOfPlayers >3) {
                    send("illegal number of player must be 2 or 3, insert a new number of player");
                    numberOfPlayers = in.nextInt();
                }
                server.setNumberOfPlayers(numberOfPlayers);
            }
            PlayerMessage player = new PlayerMessage(name,age,0);
            server.lobby(this, player);
            while(isActive()) {
                String read = in.next();
                notify(read);
            }
        } catch(IOException e){
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
}
