package it.polimi.ingsw.PSP54.server;


import it.polimi.ingsw.PSP54.server.controller.Controller;
import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.server.model.Player;
import it.polimi.ingsw.PSP54.server.virtualView.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO: case: 3 already connected clients, gameMaster sets numberOfPlayers to 2

public class Server {
    private static final int PORT= 12345;
    private ServerSocket serverSocket;

    private ExecutorService executor = Executors.newCachedThreadPool();

    private List<Connection> connections = new ArrayList<>();
    private Map<Player, Connection> waitingConnection = new HashMap<>();
    private Vector<Connection> playingConnection = new Vector<>(0,1);
    private Vector<Socket> client = new Vector<>();
    private Vector<VirtualView> virtualViews = new Vector<>(0);
    private Vector<Connection> currentConnections =new Vector<>(0,1);

    private int numberOfPlayers;


    //Register connection
    private synchronized void registerConnection(Connection c){
        connections.add(c);
    }

    //Deregister connection
    public synchronized void deregisterConnection(Connection c){
        connections.remove(c);
        /*
        Connection opponent = playingConnection.get(c);
        if(opponent != null){
            opponent.closeConnection();
            playingConnection.remove(c);
            playingConnection.remove(opponent);
            //Iterator<String> iterator = waitingConnection.keySet().iterator();
            //while(iterator.hasNext()){
            //    if(waitingConnection.get(iterator.next())==c){
            //        iterator.remove();
            //    }
            //}
        }*/
        playingConnection.remove(c);
        if (playingConnection.size()==1){
            playingConnection.firstElement().send("hai vinto");
        }
        else if (playingConnection.size()<1)
            System.exit(1);
    }

    /**
     * Ogni thread di connection inserisce nella HashMap waitingConnection un istanza del giocatore
     * con le proprie credenziali e la corrispondente connection
     * Quando i giocatori in attesa raggiungono il numero desiderato per giocare viene istanziato completamente
     * gli oggetti necessari per avviare una partita
     * @param c
     * @param p
     */
    public synchronized void lobby(Connection c, Player p){
        waitingConnection.put(p, c);

        if(waitingConnection.size() == numberOfPlayers) {

            List<Player> keys = new ArrayList<>(waitingConnection.keySet());

            for(int i=0 ; i<keys.size() ; i++) {

                Connection client = waitingConnection.get(keys.get(i));
                currentConnections.remove(waitingConnection.get(keys.get(i)));

                //initialize a VirtualView for each player, manage dispatching depending on numberOfPlayers
                if (i==0){

                    if (numberOfPlayers == 2){

                        VirtualView virtualView = new VirtualView (i, keys.get(i), client, keys.get(i+1).getPlayerName());
                        virtualViews.add(i, virtualView);

                    }else { //numberOfPlayers == 3

                        VirtualView virtualView = new VirtualView(i, keys.get(i), client, keys.get(i + 1).getPlayerName(), keys.get(i + 2).getPlayerName());
                        virtualViews.add(i, virtualView);
                        /*virtualViews.get(i).setOpponent1(keys.get(1).getPlayerName());
                        virtualViews.get(i).setOpponent2(keys.get(2).getPlayerName());*/
                    }

                }else if(i==1){

                    if (numberOfPlayers == 2){

                        VirtualView virtualView = new VirtualView( i, keys.get(i), client, keys.get(i-1).getPlayerName());
                        virtualViews.add(i,virtualView);

                    }else {

                        VirtualView virtualView = new VirtualView(i, keys.get(i), client, keys.get(i - 1).getPlayerName(), keys.get(i + 1).getPlayerName());
                        virtualViews.add(i, virtualView);
                        /* virtualViews.get(i).setOpponent1(keys.get(0).getPlayerName());
                        virtualViews.get(i).setOpponent2(keys.get(2).getPlayerName());*/
                    }

                }else {

                    VirtualView virtualView = new VirtualView( i, keys.get(i), client, keys.get(i-2).getPlayerName(), keys.get(i-1).getPlayerName());
                    virtualViews.add(i,virtualView);
                   /* virtualViews.get(i).setOpponent1(keys.get(0).getPlayerName());
                    virtualViews.get(i).setOpponent2(keys.get(1).getPlayerName());*/
                }

                playingConnection.add(client);
            }

            Game model = new Game();
            Controller controller = new Controller(model);
            for (int i = 0; i< numberOfPlayers; i++){
                controller.addVirtualView(virtualViews.get(i));
                virtualViews.get(i).addObserver(controller);
                model.addObserver(virtualViews.get(i));
                virtualViews.get(i).addPlayer();
            }

            waitingConnection.clear();
        }
    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    /**
     * Viene eseguito il server
     * Per ogni client che si collega e viene accettato dal serverSocket
     * viene eseguito un thread di una connection (tramite un executor)
     */
    public void run(){
        System.out.println("Server listening on port: " + PORT);
        while(true){
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket, this);
                currentConnections.add(connection);
                if(currentConnections.size()==1)
                    connection.setGameMaster(true);
                registerConnection(connection);
                executor.submit(connection);

            } catch (IOException e) {
                System.err.println("Connection error!");
            }
        }
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}