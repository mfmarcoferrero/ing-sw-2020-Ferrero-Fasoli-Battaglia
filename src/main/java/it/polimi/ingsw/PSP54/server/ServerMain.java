package it.polimi.ingsw.PSP54.server;
import it.polimi.ingsw.PSP54.server.controller.*;
import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.server.virtualView.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class ServerMain {

    Vector<Socket> connectedclients = new Vector<>();
    //Vector<Socket>waitingclients = new Vector<>();
    //int playersformatch=0;
    public static void main(String[] args) {

        ServerSocket socket;
        try{
            socket= new ServerSocket(2000);
        } catch (IOException e) {
            System.out.println("server can't be reached");
            System.exit(1);
            return;
        }
        try {
            while (true){
                Socket client=socket.accept();
                WaitingRoom(client);
            }
        } catch (IOException e) {
            System.out.println("connection refused");
        }
    }
    /*public void newConnection(Socket c){
        connectedclients.add(c);
    }*/

    public void stopConnection(Socket c){
        connectedclients.remove(c);
        /*if(clientVector.size()==1)
            messaggio di vittoria
        */
        if(connectedclients.size()<1)
            System.exit(1);
    }
    public void WaitingRoom(Socket c) {
        connectedclients.add(c);
        ClientHandler client = new ClientHandler(c, false);
        if (connectedclients.size()==2){
            VirtualView virtualView = new VirtualView();
            Game game = new Game();
            Controller controller = new Controller(game,virtualView);
            virtualView.addObserver(controller);
            game.addObserver(virtualView);
        }


    }


}
