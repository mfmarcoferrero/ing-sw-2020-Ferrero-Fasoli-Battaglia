package it.polimi.ingsw.PSP54.utils.messages;

import java.io.Serializable;

public class EndGameMessage extends GameMessage implements Serializable,Cloneable {

    private Boolean closeConnection;

    public EndGameMessage(Integer virtualViewId, Boolean closeConnection){
        super(virtualViewId);
        this.closeConnection = closeConnection;
    }

    public Boolean getCloseConnection() {
        return closeConnection;
    }
}
