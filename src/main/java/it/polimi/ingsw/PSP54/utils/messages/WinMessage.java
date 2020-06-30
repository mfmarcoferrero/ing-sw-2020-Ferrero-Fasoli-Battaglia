package it.polimi.ingsw.PSP54.utils.messages;

import it.polimi.ingsw.PSP54.server.model.Player;

import java.io.Serializable;

/**
 * Represents the notification of a player's victory.
 */
public class WinMessage extends GameMessage implements Serializable, Cloneable {

    private final Player p;

    public WinMessage(Integer virtualViewID, Player pl) {
        super(virtualViewID);
        this.p = pl;
    }

    public Player getPlayer() {
        return p;
    }
}
