package it.polimi.ingsw.PSP54.server;


import it.polimi.ingsw.PSP54.server.controller.Controller;
import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.utils.PlayerMessage;
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

    private ExecutorService executor = Executors.newCachedThreadPool();

    private List<Connection> connections = new ArrayList<>();
    private Map<PlayerMessage, Connection> lobbyBuffer = new HashMap<>(0);
    private Map<PlayerMessage, Connection> waitingConnection = new HashMap<>();
    private Vector<Connection> playingConnection = new Vector<>(0,1);
    private Vector<VirtualView> virtualViews = new Vector<>(0, 1);
    protected Vector<Connection> currentConnections = new Vector<>(0,1);
    private int numberOfPlayers;


    //Register connection
    private synchronized void registerConnection(Connection c){
        connections.add(c);
    }

    //Deregister connection
    public synchronized void deregisterConnection(Connection c){
        currentConnections.remove(c);
        /*every time a client disconnects if it has already entered the lobby and is in the waitingconnections vector
         we create a collection that allows to implement the iterator interface so is possible to search the element that refers to the client
         and delete it*/
        if (waitingConnection.containsValue(c))
            waitingConnection.keySet().removeIf(playerMessage -> waitingConnection.get(playerMessage) == c);

        if (playingConnection.contains(c)){
            c.send("I'm sorry but you lose, wish to you good luck for the next time");
            playingConnection.remove(c);
            if(playingConnection.size()>=1)
            {
                for (Connection connection : playingConnection)
                    connection.send(c.getName() + " is not your opponent anymore"); //TODO: why opponent's name is no longer saved?
            }
            if (playingConnection.size()==1){
                playingConnection.firstElement().send("You won!");
            }
            virtualViews.remove(playingConnection.indexOf(c));
        }
    }

    /**
     * Ogni thread di connection inserisce nella HashMap waitingConnection un istanza del giocatore
     * con le proprie credenziali e la corrispondente connection
     * Quando i giocatori in attesa raggiungono il numero desiderato per giocare viene istanziato completamente
     * gli oggetti necessari per avviare una partita
     * @param c refernce to client
     * @param p reference to in game player associated to client
     */
    public synchronized void lobby(Connection c, PlayerMessage p) {

        if (numberOfPlayers < 2 || numberOfPlayers > 3)
            lobbyBuffer.put(p, c);

        else {
            waitingConnection.put(p, c);

            if (lobbyBuffer.size() > 0)
                freeBuffer(lobbyBuffer);

            if (waitingConnection.size() == numberOfPlayers ) {
                List<PlayerMessage> keys = new ArrayList<>(waitingConnection.keySet());

                for (int i = 0; i < keys.size(); i++) {
                    Connection client = waitingConnection.get(keys.get(i));
                    currentConnections.remove(waitingConnection.get(keys.get(i)));
                    keys.get(i).setVirtualViewID(i);

                    //initialize a VirtualView for each player, manage dispatching depending on numberOfPlayers
                    if (i == 0) {
                        VirtualView virtualView;
                        if (numberOfPlayers == 2) {
                            virtualView = new VirtualView(i, keys.get(i), client, keys.get(i + 1).getPlayerName());
                        } else { //numberOfPlayers == 3
                            virtualView = new VirtualView(i, keys.get(i), client, keys.get(i + 1).getPlayerName(), keys.get(i + 2).getPlayerName());
                        }
                        virtualViews.add(i, virtualView);
                    } else if (i == 1) {
                        VirtualView virtualView;
                        if (numberOfPlayers == 2) {
                            virtualView = new VirtualView(i, keys.get(i), client, keys.get(i - 1).getPlayerName());
                        } else {
                            virtualView = new VirtualView(i, keys.get(i), client, keys.get(i - 1).getPlayerName(), keys.get(i + 1).getPlayerName());
                        }
                        virtualViews.add(i, virtualView);
                    } else {
                        VirtualView virtualView = new VirtualView(i, keys.get(i), client, keys.get(i - 2).getPlayerName(), keys.get(i - 1).getPlayerName());
                        virtualViews.add(i, virtualView);
                    }
                    playingConnection.add(client);
                }
                Game model = new Game();
                Controller controller = new Controller(model);
                for (int i = 0; i < numberOfPlayers; i++) {
                    controller.addVirtualView(virtualViews.get(i));
                    virtualViews.get(i).addObserver(controller);
                    model.addObserver(virtualViews.get(i));
                    virtualViews.get(i).addPlayer();
                }
                waitingConnection.clear();
                controller.startGame();
            }
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
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket, this);
                currentConnections.add(connection);
                if (currentConnections.size() == 1)
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

    private void freeBuffer(Map<PlayerMessage, Connection> buffer){
        Vector<PlayerMessage> bufferKeys= new Vector<>(buffer.keySet());
        Vector<Connection> bufferValues = new Vector<>(buffer.values());
        while (waitingConnection.size()<numberOfPlayers && lobbyBuffer.size()>0){
            waitingConnection.put(bufferKeys.get(0),bufferValues.get(0));
            lobbyBuffer.remove(bufferKeys.get(0),bufferValues.get(0));
            bufferKeys.remove(0);
            bufferValues.remove(0);
        }
        while(lobbyBuffer.size()>0){
            currentConnections.remove(bufferValues.get(0));
            bufferValues.get(0).send("the lobby you were has closed, please login again");
            bufferValues.get(0).closeConnection();
            lobbyBuffer.remove(bufferKeys.get(0),bufferValues.get(0));
            bufferValues.remove(0);
            bufferKeys.remove(0);
        }
    }
}