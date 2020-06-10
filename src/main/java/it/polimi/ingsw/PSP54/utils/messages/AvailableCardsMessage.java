package it.polimi.ingsw.PSP54.utils.messages;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents the GameMessage containing the available cards for the Player.
 */
public class AvailableCardsMessage extends GameMessage implements Serializable, Cloneable {

    private final HashMap<Integer, String> cards;

    public AvailableCardsMessage(Integer virtualViewID, HashMap<Integer, String> cards) {
        super(virtualViewID);
        this.cards = cards;
    }

    public HashMap<Integer, String> getCards() {
        return cards;
    }
}
