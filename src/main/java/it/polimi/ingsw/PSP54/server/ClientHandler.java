package it.polimi.ingsw.PSP54.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket client;
    boolean matchMaster;
    public ClientHandler(Socket player, boolean matchmaster)
    {
        this.client = player;
        this.matchMaster=matchmaster;
    }


    @Override
    public void run() {
        try {
            clienTask();
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }
    }
    private void clienTask() throws  IOException{
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
                int playersnumber = (int) object;
            }*/
        }catch (ClassNotFoundException e) {
            System.out.println("invalid instance of object");
        }

    }
}


