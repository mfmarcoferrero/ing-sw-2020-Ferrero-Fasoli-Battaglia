package it.polimi.ingsw.PSP54.utils;

import java.io.Serializable;
import java.util.HashMap;

public class CardsToDisplay implements Serializable,Cloneable {

    private HashMap <Integer ,String > extractedCards;
    private int virtualViewID, currentPlayerID;

    public void setExtractedCards(HashMap<Integer, String> extractedCards) {
        this.extractedCards = extractedCards;
    }

    public HashMap<Integer, String> getExtractedCards() {
        return extractedCards;
    }

    public int getVirtualViewID() {
        return virtualViewID;
    }

    public void setVirtualViewID(int virtualViewID) {
        this.virtualViewID = virtualViewID;
    }

    public int getCurrentPlayerID() {
        return currentPlayerID;
    }

    public void setCurrentPlayerID(int currentPlayerID) {
        this.currentPlayerID = currentPlayerID;
    }
}

