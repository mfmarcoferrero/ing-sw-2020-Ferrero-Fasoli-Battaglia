package it.polimi.ingsw.PSP54.utils.messages;

import java.io.Serializable;
import java.util.HashMap;

public class CardsPlayersMessage extends GameMessage implements Serializable,Cloneable {
    private final HashMap<String, Integer> cardsPlayersMap;

    public CardsPlayersMessage(Integer virtualViewID, HashMap<String, Integer> cardsPlayersMap) {
        super(virtualViewID);
        this.cardsPlayersMap = cardsPlayersMap;
    }

    public HashMap<String, Integer> getCardsPlayersMap() {
        return cardsPlayersMap;
    }
}
