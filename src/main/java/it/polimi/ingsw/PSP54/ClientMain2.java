package it.polimi.ingsw.PSP54;

import it.polimi.ingsw.PSP54.client.Client;

import java.io.IOException;

public class ClientMain2 {
    public static void main(String[] args){
        Client client = new Client("127.0.0.1", 12345);
        try {
            client.run();
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
