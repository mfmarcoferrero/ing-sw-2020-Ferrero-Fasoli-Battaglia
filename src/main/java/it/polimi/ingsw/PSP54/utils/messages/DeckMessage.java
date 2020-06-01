package it.polimi.ingsw.PSP54.utils.messages;

import java.io.Serializable;
import java.util.HashMap;

public class DeckMessage extends GameMessage implements Serializable, Cloneable {

    private final HashMap<Integer, String> deck;

    public DeckMessage(Integer virtualViewID, HashMap<Integer, String> deck) {
        super(virtualViewID);
        this.deck = deck;
    }

    public HashMap<Integer, String> getDeck() {
        return deck;
    }
}
