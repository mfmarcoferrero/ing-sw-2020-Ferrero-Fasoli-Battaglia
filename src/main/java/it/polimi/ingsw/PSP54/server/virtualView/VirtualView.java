package it.polimi.ingsw.PSP54.server.virtualView;

import it.polimi.ingsw.PSP54.observer.Observable;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.Connection;
import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.utils.*;


public class VirtualView extends Observable implements Observer {

    private Box [][] board;
    private boolean moveDone = false, buildDone = false, firstWorkerSetDone = false;
    private int id;
    private Connection connection;
    private MessageReceiver messageReceiver;
    private PlayerMessage player;
    private String opponent;

    /*public VirtualView(int virtualViewId, Vector<PlayerMessage> players, Connection connection){
        this.virtualViewId = virtualViewId;
        this.connection = connection;
        this.messageReceiver = new MessageReceiver(this.connection,this);
        this.players = players;
        connection.addObserver(this.messageReceiver);
    }*/

    /**
     * Instantiates a VirtualView with corresponding Connection and MessageReceiver for a 2 players game
     * @param id
     * @param p
     * @param connection
     */
    public VirtualView(int id, PlayerMessage p, Connection connection, String opponent) {
        this.id = id;
        this.connection = connection;
        this.messageReceiver = new MessageReceiver(this.connection,this);
        this.player = p;
        this.opponent = opponent;
        connection.addObserver(this.messageReceiver);
        connection.asyncSend("opponent is: " + this.opponent + "\n");
    }

    /**
     * Instantiates a VirtualView with corresponding Connection and MessageReceiver for a 3 players game
     * @param id
     * @param p
     * @param connection
     */
    public VirtualView(int id, PlayerMessage p, Connection connection, String opponent1, String opponent2) {
        this.id = id;
        this.connection = connection;
        this.messageReceiver = new MessageReceiver(this.connection,this);
        this.player = p;
        this.opponent = opponent1;
        connection.addObserver(this.messageReceiver);
        connection.asyncSend("opponent 1 is: " + opponent1 + "\nopponent 2 is: " + opponent2 + "\n");
    }

    /**
     * Notifica il controller con un oggetto di tipo Player che contiene solo le
     * credenziali
     */
    public void addPlayer() {
        notify(player);
    }
    
    public void selectCard(String cardName) {

        CardChoice choice = new CardChoice(id, cardName);
        notify(choice);

    } 

    /**
     * Notifica il controller con un oggetto di tipo Move verificando che la mossa
     * sia un set iniziale di un worker
     * @param move
     */
    public void setWorker(Move move){
        while (!firstWorkerSetDone && move.isSetFirstPos()){
            notify(move);
        }
    }

    /**
     * Notifica il controller con un oggetto di tipo Move verificando che la mossa
     * non sia un set iniziale di un worker
     * @param move
     */
    public void handleMove(Move move) {
        while (!moveDone) {
            if (!(move.isSetFirstPos())) {
                notify(move);
            }
        }
    }

    /**
     * Notifica il controller con un oggetto di tipo Build
     * @param build
     */
    public void handleBuild(Build build) {
        while (!buildDone) {
            notify(build);
        }
    }

    public void setMoveDone(boolean moveDone) {
        this.moveDone = moveDone;
    }

    public void setBuildDone(boolean buildDone) {
        this.buildDone = buildDone;
    }

    public void setFirstWorkerSetDone(boolean workerSet) {
        this.firstWorkerSetDone = workerSet;
    }

    public int getId() {
        return id;
    }

    public void showMessage(Object message) {
        connection.asyncSend(message);
    }

    public void showBoard() {
        connection.asyncSend(board);
    }

    public Box[][] getBoard() {
        return board;
    }

    /**
     * Update the VirtualView's side board
     * @param message the clone of model's board
     */
    @Override
    public void update(Box[][] message) {
        this.board = message;
    }

    @Override
    public void update(CardDisplayed message) {
        if (message.getVirtualViewID() == id)
            showMessage(message.getToDisplay());
    }

    @Override
    public void update(GameMessage message) {
        if (message.getVirtualViewID()==getId())
            showMessage(message.getMessage());
    }

    /**
     * Update the message to be shown to the player
     * @param message the message to be shown
     */
    @Override
    public void update(String message) {
        showMessage(message);
    }

    @Override
    public void update(CardChoice message) {

    }

    @Override
    public void update(Move message) {

    }

    @Override
    public void update(Build message) {

    }

    @Override
    public void update(PlayerMessage message) {

    }

}