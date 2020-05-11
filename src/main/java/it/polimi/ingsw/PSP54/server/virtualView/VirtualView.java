package it.polimi.ingsw.PSP54.server.virtualView;

import it.polimi.ingsw.PSP54.observer.Observable;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.Connection;
import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.utils.PlayerAction;
import it.polimi.ingsw.PSP54.utils.choices.CardChoice;
import it.polimi.ingsw.PSP54.utils.choices.MoveChoice;
import it.polimi.ingsw.PSP54.utils.messages.CardsMessage;
import it.polimi.ingsw.PSP54.utils.messages.GameMessage;


public class VirtualView extends Observable<PlayerAction> implements Observer<GameMessage> {

    private boolean moveDone = false, buildDone = false, firstWorkerSetDone = false;
    private final int id;
    private Connection connection;
    private MessageReceiver messageReceiver;
    private PlayerAction player;
    private String opponent;

    /**
     * Instantiates a VirtualView with corresponding Connection and MessageReceiver for a 2 players game
     * @param id
     * @param p
     * @param connection
     */
    public VirtualView(int id, PlayerAction p, Connection connection, String opponent) {
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
    public VirtualView(int id, PlayerAction p, Connection connection, String opponent1, String opponent2) {
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

    public synchronized void sendMessage(GameMessage message) {
        connection.asyncSend(message);
    }

    /**
     * Called whenever the observed object is changed.
     *
     * @param message an argument passed to the notify method.
     */
    @Override
    public void update(GameMessage message) {
        if (message.getVirtualViewID() == getId() || message.getVirtualViewID() == null) {
            sendMessage(message);
        }
    }
}