package it.polimi.ingsw.PSP54.utils.messages;

import it.polimi.ingsw.PSP54.server.model.Player;

import java.io.Serializable;

public class WinMessage extends GameMessage implements Serializable, Cloneable {
    private final Player p;
    private String message;

    public WinMessage(Integer virtualViewID, Player pl) {
        super(virtualViewID);
        this.p =pl;
    }

    public Player getPlayer(){
        return p;
    }
}
