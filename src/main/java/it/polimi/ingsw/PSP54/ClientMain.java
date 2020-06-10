package it.polimi.ingsw.PSP54;

import it.polimi.ingsw.PSP54.client.Client;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Server's IP address:");
        String ip = input.next();
        Client client = new Client( ip, 12345);
        try {
            client.startClient();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
