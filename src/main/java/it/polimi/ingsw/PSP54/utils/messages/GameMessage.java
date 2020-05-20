package it.polimi.ingsw.PSP54.utils.messages;

import java.io.Serializable;

/**
 * Represents a message generated from the game
 */
public class GameMessage implements Serializable,Cloneable {


    private final Integer virtualViewID;

    public GameMessage(Integer virtualViewID) {
        this.virtualViewID = virtualViewID;
    }

    public Integer getVirtualViewID() {
        return virtualViewID;
    }
}
