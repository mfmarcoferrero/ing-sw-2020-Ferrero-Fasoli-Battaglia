package it.polimi.ingsw.PSP54;

import it.polimi.ingsw.PSP54.client.Client;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) {
        int i =1;
        Client client = new Client("127.0.0.1", 12345);
        try {
            client.startClient(i);
            if(i<3)
                i++;
            else
                i=1;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
