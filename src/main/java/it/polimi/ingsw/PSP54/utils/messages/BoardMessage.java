package it.polimi.ingsw.PSP54.utils.messages;

import it.polimi.ingsw.PSP54.server.model.Box;

public class BoardMessage extends GameMessage {

    private Box[][] board;

    public BoardMessage(Integer virtualViewID, Box[][] board){
        super(virtualViewID);
        this.board = board;
    }

    public Box[][] getBoard() {
        return board;
    }

    public void setBoard(Box[][] board) {
        this.board = board;
    }
}
