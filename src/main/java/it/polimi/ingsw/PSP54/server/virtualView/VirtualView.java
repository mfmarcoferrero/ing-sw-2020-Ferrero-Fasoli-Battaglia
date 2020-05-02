package it.polimi.ingsw.PSP54.server.virtualView;

import it.polimi.ingsw.PSP54.observer.Observable;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.Connection;
import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.utils.Build;
import it.polimi.ingsw.PSP54.utils.Move;
import it.polimi.ingsw.PSP54.utils.PlayerMessage;


public class VirtualView extends Observable implements Observer {

    private Box [][] board;
    private boolean moveDone = false, buildDone = false, firstWorkerSetDone = false;
    private int virtualViewId;
    private Connection connection;
    private MessageReceiver messageReceiver;
    private PlayerMessage player;
    private String opponent1;

    /*public VirtualView(int virtualViewId, Vector<PlayerMessage> players, Connection connection){
        this.virtualViewId = virtualViewId;
        this.connection = connection;
        this.messageReceiver = new MessageReceiver(this.connection,this);
        this.players = players;
        connection.addObserver(this.messageReceiver);
    }*/

    /**
     * Instantiates a VirtualView with corresponding Connection and MessageReceiver for a 2 players game
     * @param virtualViewId
     * @param p
     * @param connection
     */
    public VirtualView(int virtualViewId, PlayerMessage p, Connection connection, String opponent) {
        this.virtualViewId = virtualViewId;
        this.connection = connection;
        this.messageReceiver = new MessageReceiver(this.connection,this);
        this.player = p;
        this.opponent1 = opponent;
        connection.addObserver(this.messageReceiver);
        connection.asyncSend("opponent is: " + opponent1 + "\ndigit 'show' to see the board");
    }

    /**
     * Instantiates a VirtualView with corresponding Connection and MessageReceiver for a 3 players game
     * @param virtualViewId
     * @param p
     * @param connection
     */
    public VirtualView(int virtualViewId, PlayerMessage p, Connection connection, String opponent1, String opponent2) {
        this.virtualViewId = virtualViewId;
        this.connection = connection;
        this.messageReceiver = new MessageReceiver(this.connection,this);
        this.player = p;
        this.opponent1 = opponent1;
        connection.addObserver(this.messageReceiver);
        connection.asyncSend("opponent 1 is: " + opponent1 + "\nopponent 2 is: " + opponent2 + "\ndigit 'show' to see the board");
    }

    /**
     * Notifica il controller con un oggetto di tipo Player che contiene solo le
     * credenziali
     */
    public void addPlayer() throws Exception {
        notify(player);
    }

    /**
     * Notifica il controller con un oggetto di tipo Move verificando che la mossa
     * sia un set iniziale di un worker
     * @param move
     */
    public void setWorker(Move move) throws Exception{
        while (!firstWorkerSetDone && move.isSetFirstPos()){
            notify(move);
        }
    }

    /**
     * Notifica il controller con un oggetto di tipo Move verificando che la mossa
     * non sia un set iniziale di un worker
     * @param move
     */
    public void handleMove(Move move) throws Exception{
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
    public void handleBuild(Build build) throws Exception {
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

    public int getVirtualViewId() {
        return virtualViewId;
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
     * @throws Exception ??
     */
    @Override
    public void update(Box[][] message) throws Exception {
        this.board = message;
    }

    /**
     * Update the message to be shown to the player
     * @param message
     * @throws Exception
     */
    @Override
    public void update(String message) throws Exception {
        showMessage(message);
    }

    @Override
    public void update(Move message) throws Exception {
        return;
    }

    @Override
    public void update(Build message) throws Exception {
        return;
    }

    @Override
    public void update(PlayerMessage message) throws Exception {
        return;
    }

}