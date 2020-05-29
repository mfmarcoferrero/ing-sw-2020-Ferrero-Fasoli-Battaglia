package it.polimi.ingsw.PSP54.server.virtualView;

import it.polimi.ingsw.PSP54.observer.Observable;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.Connection;
import it.polimi.ingsw.PSP54.utils.PlayerAction;
import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;

import java.util.Vector;


public class VirtualView extends Observable<PlayerAction> implements Observer<GameMessage> {

    private final int id;
    private final Connection connection;
    private final MessageReceiver messageReceiver;
    private final PlayerAction playerCredentials;

    /**
     * Instantiates a VirtualView Object for a 2 player match.
     * @param id the unique identifier of the VirtualView.
     * @param p the PlayerAction object containing player's credentials.
     * @param connection the Connection the player is using.
     * @param opponents Vector containing all opponent's name.
     */
    public VirtualView(int id, PlayerAction p, Connection connection, Vector<String> opponents) {
        this.id = id;
        this.connection = connection;
        this.messageReceiver = new MessageReceiver(this.connection,this);
        this.playerCredentials = p;
        connection.addObserver(this.messageReceiver);
        for (String opponent : opponents) {
            GameMessage opponentMessage = new StringMessage(id, "Your opponent is:\n" + opponent + "\n");
            connection.asyncSend(opponentMessage);
        }
    }

    /**
     * Notifies the observers with a PlayerAction object containing the player's credentials.
     */
    public void addPlayer() {
        notify(playerCredentials);
    }

    /**
     * Notifies the observers with a message containing player's action.
     * @param action the player's action.
     */
    public void handleAction(PlayerAction action) {
        notify(action);
    }

    /**
     * Sends via socket a GameMessage object.
     * @param message the message to be sent.
     */
    public void sendMessage(GameMessage message) {
        try {
            connection.asyncSend(message).join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            sendMessage(message);
        }
    }

    /**
     * Called whenever the observed object is changed.
     *
     * @param message an argument passed to the notify method.
     */
    @Override
    public void update(GameMessage message) {
        if (message.getVirtualViewID() == null || message.getVirtualViewID() == getId())
            sendMessage(message);
    }

    //getters & setter

    public int getId() {
        return id;
    }
}