package it.polimi.ingsw.PSP54.client;


import it.polimi.ingsw.PSP54.client.view.*;
import it.polimi.ingsw.PSP54.observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client extends Observable<Object> {

    private final String ip;
    private final int port;
    private final CliView view = new CliView();

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
        addObserver(view);
    }

    private boolean active = true;

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    /**
     * Thread per la lettura di ciò che arriva dal server
     * Per ogni tipo di messaggio ricevuto viene eseguita un azione della view o una scrittura su terminale
     * @param socketIn
     * @return
     */
    public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        Client.this.notify(inputObject);
                        //System.out.println("Indice nell'array: " + player_ind);
                    }
                } catch (Exception e) {
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    //TODO: make client use View methods to parse input and instantiate the message objects
    /**
     * Thread per la scrittura verso il server
     * Fino a quando il thread è attivo il client invia ciò che viene scritto da terminale
     * @param stdin
     * @param socketOut
     * @return
     */
    public Thread asyncWriteToSocket(final Scanner stdin, final PrintWriter socketOut){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        String inputLine = stdin.nextLine();
                        socketOut.println(inputLine);
                        socketOut.flush();
                    }
                } catch(Exception e) {
                    setActive(false);
                }
            }
        });
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
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(stdin, socketOut);
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

}