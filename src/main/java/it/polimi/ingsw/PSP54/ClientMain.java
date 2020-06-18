package it.polimi.ingsw.PSP54;

import it.polimi.ingsw.PSP54.client.Client;
import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) {
        Client client = new Client(12345);
        try {
            client.startClient();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
