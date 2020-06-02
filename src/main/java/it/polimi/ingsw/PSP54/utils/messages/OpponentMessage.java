package it.polimi.ingsw.PSP54.utils.messages;

import java.util.Vector;

public class OpponentMessage extends GameMessage {
    private Vector<String> opponentNames = new Vector<>();
    private int numberOfPlayers;

    public OpponentMessage(Integer virtualViewID, Vector<String> opponentNames, int numberOfPlayers) {
        super(virtualViewID);
        this.opponentNames = opponentNames;
        this.numberOfPlayers = numberOfPlayers;
    }

    public Vector<String> getOpponentNames() {
        return opponentNames;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

}
