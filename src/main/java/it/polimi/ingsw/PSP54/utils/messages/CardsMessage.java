package it.polimi.ingsw.PSP54.utils.messages;

import java.util.HashMap;

public class CardsMessage extends GameMessage{

    private final int virtualViewID;
    private final HashMap<Integer, String> cards;

    public CardsMessage(int virtualViewID, HashMap<Integer, String> cards) {
        this.virtualViewID = virtualViewID;
        this.cards = cards;
    }

    public int getVirtualViewID() {
        return virtualViewID;
    }

    public HashMap<Integer, String> getCards() {
        return cards;
    }
}
