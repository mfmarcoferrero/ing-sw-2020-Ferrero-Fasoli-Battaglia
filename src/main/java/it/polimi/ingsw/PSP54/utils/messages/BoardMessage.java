package it.polimi.ingsw.PSP54.utils.messages;

import it.polimi.ingsw.PSP54.server.model.Box;

import java.io.Serializable;

public class BoardMessage extends GameMessage implements Serializable, Cloneable {

    private final Box[][] board;

    public BoardMessage(Integer virtualViewID, Box[][] board){
        super(virtualViewID);
        this.board = board;
    }

    public Box[][] getBoard() {
        return board;
    }

}
