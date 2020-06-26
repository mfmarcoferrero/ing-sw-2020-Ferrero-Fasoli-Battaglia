package it.polimi.ingsw.PSP54.utils.messages;

import java.io.Serializable;

/**
 * Represents a GameMessage for a player who is accessing the game lobby.
 */
public class LobbyAccessMessage extends GameMessage implements Serializable, Cloneable {
    public LobbyAccessMessage(Integer virtualViewID) {
        super(virtualViewID);
    }
}
