package it.polimi.ingsw.PSP54.utils.messages;

import it.polimi.ingsw.PSP54.server.model.Player;

public class LoseMessage extends GameMessage {
    private Player p;

    public LoseMessage(Integer virtualViewID, Player pl) {
        super(virtualViewID);
        this.p =pl;
    }

    public Player getPlayer(){
        return p;
    }
}
