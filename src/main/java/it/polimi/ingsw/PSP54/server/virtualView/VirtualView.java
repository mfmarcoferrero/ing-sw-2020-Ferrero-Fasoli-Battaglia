package it.polimi.ingsw.PSP54.server.virtualView;

import it.polimi.ingsw.PSP54.observer.Observable;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.Connection;
import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.utils.*;


public class VirtualView extends Observable<Object> implements Observer {

    private Box [][] board;
    private boolean moveDone = false, buildDone = false, firstWorkerSetDone = false;
    private final int id;
    private Connection connection;
    private MessageReceiver messageReceiver;
    private PlayerMessage player;
    private String opponent;

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
    
    public void selectCard(Choice message) {
        notify(message);
    }

    /*public void selectWorker(String message) {
        Choice choice = new Choice(id, message);
        notify(choice);
    }*/

    /**
     * Notifica il controller con un oggetto di tipo Move verificando che la mossa
     * sia un set iniziale di un worker
     * @param move
     */
    public void setWorker(Move move){
        if (!firstWorkerSetDone && move.isSetFirstPos()){
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

    public synchronized void showMessage(Object message) {
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
    public synchronized void update(Box[][] message) {
        this.board = message;
    }

    @Override
    public synchronized void update(GameMessage message) {
        if (message.getVirtualViewID() == this.id) {
            showMessage(message.getMessage());
        }
    }

    @Override
    synchronized public void update(CardsToDisplay message) {
        if (message.getCurrentPlayerID() == this.id) {
            showMessage(message);
        }
    }

    /**
     * Update the message to be shown to the player
     * @param message the message to be shown
     */
    @Override
    synchronized public void update(String message) {
        showMessage(message);
    }

    @Override
    synchronized public void update(Choice message) {

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