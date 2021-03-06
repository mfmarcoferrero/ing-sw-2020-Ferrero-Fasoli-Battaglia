package it.polimi.ingsw.PSP54.utils.messages;

import it.polimi.ingsw.PSP54.server.model.Player;

import java.io.Serializable;

/**
 * Class representing the loss notification for a player.
 */
public class LoseMessage extends GameMessage implements Serializable, Cloneable {
    private final Player p;

    public LoseMessage(Integer virtualViewID, Player pl) {
        super(virtualViewID);
        this.p =pl;
    }

    public Player getPlayer(){
        return p;
    }
}
