package it.polimi.ingsw.PSP54.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket client;
    boolean gameMaster;
    public ClientHandler(Socket player, boolean gameMaster)
    {
        this.client = player;
        this.gameMaster = gameMaster;
    }

    @Override
    public void run() {
        try {
            clientTask();
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }
    }

    private void clientTask() throws  IOException{
        System.out.println("Server has accepted your request");
        ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(client.getInputStream());
        System.out.println("choose a nickname");
        Object object= null;
        try {
            object = input.readObject();
            String name = (String) object;
            System.out.println("set your age");
            object = input.readObject();
            int age = (int) object;
            /*if (matchMaster == true) {
                System.out.println("how many players for the match?");
                object = input.readObject();
                int playersNumber = (int) object;
            }*/
        }catch (ClassNotFoundException e) {
            System.out.println("invalid instance of object");
        }

    }
}


