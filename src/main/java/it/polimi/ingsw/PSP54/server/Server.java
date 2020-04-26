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

public class Server {
    private static final int PORT= 12345;
    private ServerSocket serverSocket;

    private ExecutorService executor = Executors.newFixedThreadPool(128);

    private List<Connection> connections = new ArrayList<Connection>();
    private Map<Player, Connection> waitingConnection = new HashMap<>();
    private Map<Connection, Connection> playingConnection = new HashMap<>();

    //Register connection
    private synchronized void registerConnection(Connection c){
        connections.add(c);
    }

    //Deregister connection
    public synchronized void deregisterConnection(Connection c){
        connections.remove(c);
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
        }
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
        if(waitingConnection.size() == 2) {
            List<Player> keys = new ArrayList<>(waitingConnection.keySet());
            Connection c1 = waitingConnection.get(keys.get(0));
            Connection c2 = waitingConnection.get(keys.get(1));
            VirtualView virtualViewPlayer1 = new VirtualView(0, keys.get(0),c1,keys.get(1).getPlayerName());
            VirtualView virtualViewPlayer2 = new VirtualView(1, keys.get(1),c2,keys.get(0).getPlayerName());
            Game model = new Game();
            Controller controller = new Controller(model);
            controller.addVirtualView(virtualViewPlayer1);
            controller.addVirtualView(virtualViewPlayer2);
            virtualViewPlayer1.addObserver(controller);
            virtualViewPlayer2.addObserver(controller);
            model.addObserver(virtualViewPlayer1);
            model.addObserver(virtualViewPlayer2);
            virtualViewPlayer1.addPlayer();
            virtualViewPlayer2.addPlayer();
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
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
                registerConnection(connection);
                executor.submit(connection);
            } catch (IOException e) {
                System.err.println("Connection error!");
            }
        }
    }

}