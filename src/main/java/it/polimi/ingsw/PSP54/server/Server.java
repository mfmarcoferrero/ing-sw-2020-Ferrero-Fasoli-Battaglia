package it.polimi.ingsw.PSP54.server;


import it.polimi.ingsw.PSP54.server.controller.Controller;
import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.server.virtualView.VirtualView;
import it.polimi.ingsw.PSP54.utils.PlayerAction;
import it.polimi.ingsw.PSP54.utils.choices.PlayerCredentials;
import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT= 12345;
    private final ServerSocket serverSocket;

    private final ExecutorService executor = Executors.newFixedThreadPool(100);

    private final List<Connection> connections = new ArrayList<>();
    private final Map<PlayerCredentials, Connection> lobbyBuffer = new HashMap<>(0);
    private final Map<PlayerCredentials, Connection> waitingConnection = new HashMap<>();
    private final Vector<Connection> playingConnection = new Vector<>(0,1);
    private final Vector<VirtualView> virtualViews = new Vector<>(0, 1);
    protected Vector<Connection> currentConnections = new Vector<>(0,1);
    protected Vector<String> opponents = new Vector<>();
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
            GameMessage tryAgain = new StringMessage(null, "I'm sorry but you lose, wish you good luck for the next time");
            c.asyncSend(tryAgain);
            playingConnection.remove(c);
            if(playingConnection.size()>=1)
            {
                for (Connection connection : playingConnection){
                    GameMessage noMoreOpponent = new StringMessage(null, c.getName() + " is not your opponent anymore");
                    connection.send(noMoreOpponent);
                }
            }
            if (playingConnection.size()==1){
                GameMessage youWon = new StringMessage(null, StringMessage.winMessage);
                playingConnection.firstElement().asyncSend(youWon);
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
    public synchronized void lobby(Connection c, PlayerCredentials p) {

        if (numberOfPlayers < 2 || numberOfPlayers > 3)
            lobbyBuffer.put(p, c);

        else {
            waitingConnection.put(p, c);

            if (lobbyBuffer.size() > 0)
                freeBuffer(lobbyBuffer);

            if (waitingConnection.size() == numberOfPlayers ) {
                List<PlayerCredentials> credentialsChoices = new ArrayList<>(waitingConnection.keySet());
                List<PlayerCredentials> opponentsKeys = new ArrayList<>(waitingConnection.keySet());
                List<PlayerAction> playersCredentials = new ArrayList<>();
                for (int i = 0; i < credentialsChoices.size(); i++) {
                    Connection client = waitingConnection.get(credentialsChoices.get(i));
                    currentConnections.remove(client);
                    PlayerAction credentials = new PlayerAction(i, credentialsChoices.get(i));
                    playersCredentials.add(credentials);
                    opponentsKeys.remove(credentialsChoices.get(i));
                    for (PlayerCredentials opponentsKey : opponentsKeys) {
                        opponents.add(opponentsKey.getPlayerName());
                    }
                    opponentsKeys.add(i,credentialsChoices.get(i));
                    VirtualView view = new VirtualView(i,playersCredentials.get(i),client,opponents);
                    opponents.clear();
                    virtualViews.add(i,view);
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
    @SuppressWarnings("InfiniteLoopStatement")
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

    private void freeBuffer(Map<PlayerCredentials, Connection> buffer){
        Vector<PlayerCredentials> bufferKeys= new Vector<>(buffer.keySet());
        Vector<Connection> bufferValues = new Vector<>(buffer.values());
        while (waitingConnection.size()<numberOfPlayers && lobbyBuffer.size()>0){
            waitingConnection.put(bufferKeys.get(0),bufferValues.get(0));
            lobbyBuffer.remove(bufferKeys.get(0),bufferValues.get(0));
            bufferKeys.remove(0);
            bufferValues.remove(0);
        }
        while(lobbyBuffer.size()>0){
            currentConnections.remove(bufferValues.get(0));
            GameMessage closedLobby = new StringMessage(null, StringMessage.closedLobby);
            bufferValues.get(0).asyncSend(closedLobby);
            bufferValues.get(0).closeConnection();
            lobbyBuffer.remove(bufferKeys.get(0),bufferValues.get(0));
            bufferValues.remove(0);
            bufferKeys.remove(0);
        }
    }

    public boolean CheckName(String name){
        Vector<PlayerCredentials> players;
        boolean outcome=false;
        if(numberOfPlayers<2 || numberOfPlayers>3 )
            players = new Vector<>(lobbyBuffer.keySet());
        else
            players = new Vector<>(waitingConnection.keySet());
        for (PlayerCredentials player : players)
            if (Objects.equals(player.getPlayerName(), name)) {
                outcome = true;
                break;
            }
        return outcome;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
