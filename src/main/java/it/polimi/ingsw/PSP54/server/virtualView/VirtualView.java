package it.polimi.ingsw.PSP54.server.virtualView;

import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import it.polimi.ingsw.PSP54.server.Connection;
import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.server.model.Player;
import it.polimi.ingsw.PSP54.utils.Build;
import it.polimi.ingsw.PSP54.utils.Move;

public class VirtualView extends Observable implements Observer {

    private Box [][] board;
    private boolean moveDone = false, buildDone = false, firstWorkerSetDone = false;
    private int virtualViewId;
    private Connection connection;
    private MessageReceiver messageReceiver;
    private Player player;
    private String opponent1=null;
    private String opponent2=null;

    /**
     * Viene istanziata una virtualView con la sua connessione e messageReceiver che permette
     * la lettura degli oggetti inviati dal client
     * @param virtualViewId
     * @param p
     * @param connection
     */
    public VirtualView(int virtualViewId,Player p ,Connection connection, String opponent1, String opponent2) {
        this.virtualViewId = virtualViewId;
        this.connection = connection;
        this.messageReceiver = new MessageReceiver(this.connection,this);
        this.player = p;
        connection.addObserver(this.messageReceiver);
        connection.asyncSend("opponent 1 is:"+ opponent1+"\nopponent 2 is "+ opponent2+ "\n digit show to see your board");
    }

    /**
     * Notifica il controller con un oggetto di tipo Player che contiene solo le
     * credenziali
     */
    public void addPlayer () {
        setChanged();
        notifyObservers(player);
    }

    /**
     * Notifica il controller con un oggetto di tipo Move verificando che la mossa
     * sia un set iniziale di un worker
     * @param move
     */
    public void setWorker(Move move) {
        while (firstWorkerSetDone == false && move.isSetFirstPos()){
            setChanged();
            notifyObservers(move);
        }
    }

    /**
     * Notifica il controller con un oggetto di tipo Move verificando che la mossa
     * non sia un set iniziale di un worker
     * @param move
     */
    public void handleMove(Move move) {
        while (moveDone == false) {
            if (!(move.isSetFirstPos())) {
                setChanged();
                notifyObservers(move);
            }
        }
    }

    /**
     * Notifica il controller con un oggetto di tipo Build
     * @param build
     */
    public void handleBuild(Build build) {
        while (buildDone == false) {
            setChanged();
            notifyObservers(build);
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

    /**
     * Viene notificato dal model che invia sempre una board per ogni metodo del model chiamato dal controller
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        if(!(o instanceof Game) || !(arg instanceof Box[][])){
            throw new IllegalArgumentException();
        }
        this.board = (Box[][]) arg;
    }

    public Box[][] getBoard() {
        return board;
    }


    public void setOpponent1(String opponent1) {
        this.opponent1 = opponent1;
        connection.asyncSend("your opponent is:"+ opponent1+"\n");
    }

    public void setOpponent2(String opponent2) {
        this.opponent2 = opponent2;
        connection.asyncSend("Your opponent is: " + opponent2 + "\nDigit 'show' to show the current board");
    }
}