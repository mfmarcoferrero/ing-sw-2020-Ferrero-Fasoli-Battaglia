package it.polimi.ingsw.PSP54.server;

import it.polimi.ingsw.PSP54.observer.*;
import it.polimi.ingsw.PSP54.server.model.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection extends Observable <String> implements Runnable {

    private Socket socket;
    private Scanner in;
    private ObjectOutputStream out;
    private Server server;
    private int age;
    private String name;
    private boolean active = true;
    boolean gamemaster=false;
    int numberofplayers;

    public Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }

    public void send(Object message) {
        try {
            System.out.println("(Invio un messaggio a: " + name + ")");
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
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
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    /**
     * Ogni connection istanziata viene lanciata come thread
     * Per ogni thread di connection viene chiamato il metodo server.lobby()
     * Fino a quando isActive() il thread rimane in ascolto di ciò che viene inviato dal client
     * e per ogni messaggio ricevuto notifica MessageReceiver
     */
    public void run() {
        try {
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("Welcome! What's your name?");
            name = in.nextLine();
            send("What's your age?");
            age = in.nextInt();
            if(gamemaster==true) {
                send("hey, set the number of player");
                numberofplayers=in.nextInt();
                while (numberofplayers<2 || numberofplayers>3) {
                    send("illegal number of player must be 2 or 3, insert a new number of player");
                    numberofplayers=in.nextInt();
                }
                server.setNumberofplayer(numberofplayers);
            }
            server.lobby(this, new Player(name,age,null,null));
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

    public void setGamemaster(boolean gamemaster) {
        this.gamemaster = gamemaster;
    }

}
