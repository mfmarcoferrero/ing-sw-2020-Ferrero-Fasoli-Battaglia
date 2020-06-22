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

    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final List<Connection> connections = new ArrayList<>();
    private final Map<PlayerCredentials, Connection> lobbyBuffer = new HashMap<>(0);
    private final Map<PlayerCredentials, Connection> waitingConnection = new HashMap<>();
    private final Vector<Connection> playingConnection = new Vector<>(0,1);
    private final Vector<VirtualView> virtualViews = new Vector<>(0, 1);
    protected final Vector<Connection> currentConnections = new Vector<>(0,1);
    protected final Vector<String> opponents = new Vector<>();
    private int numberOfPlayers;
    private int numberOfGames = 0;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    /**
     * Adds a connection to the Server's Vector.
     * @param c the Connection that is going to be registered.
     */
    private synchronized void registerConnection(Connection c){
        connections.add(c);
    }

    /**
     * Removes a connection from the Server's Vectors either if the associated Player is waiting in the lobby or playing.
     * @param c the Connection that is going to be unregistered.
     */
    public synchronized void deregisterConnection(Connection c){
        currentConnections.remove(c);

        if (waitingConnection.containsValue(c))
            waitingConnection.keySet().removeIf(playerMessage -> waitingConnection.get(playerMessage) == c);

        if (playingConnection.contains(c)){
            for (Connection connection : playingConnection){
                if (connection.getGameID() == c.getGameID()) {
                    GameMessage noMoreOpponent = new StringMessage(null, StringMessage.endForDisconnection);
                    connection.send(noMoreOpponent);
                }
            }
            virtualViews.clear();
        }
    }

    /**
     * Associates every connected Client to his Player credentials after checking for usernames' uniqueness.
     * When the selected number of player for a game is satisfied instantiates the MVC pattern instances and starts the game.
     * @param c the connection associated to a newly registered Player.
     * @param p the player's credentials.
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
                    client.setGameID(getNumberOfGames());
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
                setNumberOfGames(numberOfGames + 1);
                controller.startGame();
            }
        }
    }

    /**
     * Initializes the Server. For each accepted Client a thread starts in order to handle the connection.
     * When the first Client connects it's set to GameMaster (he will chose the number of player for his game)
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

    /**
     * Handles pending Clients that have been added in the lobbyBuffer HashMap.
     * @param buffer the HashMap containing the pending connections.
     */
    private void freeBuffer(Map<PlayerCredentials, Connection> buffer){
        Vector<PlayerCredentials> bufferKeys= new Vector<>(buffer.keySet());
        Vector<Connection> bufferValues = new Vector<>(buffer.values());

        while (waitingConnection.size() < numberOfPlayers && lobbyBuffer.size() > 0){
            waitingConnection.put(bufferKeys.get(0), bufferValues.get(0));
            lobbyBuffer.remove(bufferKeys.get(0), bufferValues.get(0));
            bufferKeys.remove(0);
            bufferValues.remove(0);
        }
        while(lobbyBuffer.size() > 0){
            currentConnections.remove(bufferValues.get(0));
            GameMessage closedLobby = new StringMessage(null, StringMessage.closedLobby);
            bufferValues.get(0).asyncSend(closedLobby);
            bufferValues.get(0).closeConnection();
            lobbyBuffer.remove(bufferKeys.get(0), bufferValues.get(0));
            bufferValues.remove(0);
            bufferKeys.remove(0);
        }
    }

    /**
     * Checks for usernames' uniqueness.
     * @param name the name selected by the Player.
     * @return true if the name is already taken, false otherwise.
     */
    public boolean checkName(String name){
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

    //getters & setters

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }
}
