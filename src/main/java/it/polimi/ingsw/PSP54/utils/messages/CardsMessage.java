package it.polimi.ingsw.PSP54.utils.messages;

import java.io.Serializable;
import java.util.HashMap;

public class CardsMessage extends GameMessage implements Serializable, Cloneable {

    private final HashMap<Integer, String> cards;

    public CardsMessage(Integer virtualViewID, HashMap<Integer, String> cards) {
        super(virtualViewID);
        this.cards = cards;
    }

    public HashMap<Integer, String> getCards() {
        return cards;
    }
}
