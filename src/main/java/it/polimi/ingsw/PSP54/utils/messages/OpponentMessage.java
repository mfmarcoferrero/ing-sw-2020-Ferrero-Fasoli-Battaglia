package it.polimi.ingsw.PSP54.utils.messages;

import java.util.Vector;

/**
 * Represents the GameMessage containing containing a player's opponents names.
 */
public class OpponentMessage extends GameMessage {
    private final Vector<String> opponentNames;
    private final int numberOfPlayers;

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
