package it.polimi.ingsw.PSP54.test_model;

import it.polimi.ingsw.PSP54.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApolloTest {
    Game gameDemo;
    Box [][] board;

    @Before
    public void setUp() {
        gameDemo = new Game();
        gameDemo.startGame();
        board = gameDemo.getBoard();
        gameDemo.newPlayer("Alessandro",22,null);
        gameDemo.newPlayer("Marco",22,null);
        gameDemo.getPlayers().get(0).setPower(Player.APOLLO);
        gameDemo.getPlayers().get(0).setInitialPosition(0,board[2][2]);
        gameDemo.getPlayers().get(1).setPower(Player.NORMAL_POWER);
        gameDemo.getPlayers().get(1).setInitialPosition(0,board[2][3]);
    }

    @Test
    public void validMove_normalMove_workerSetSourceNull() throws Exception {
        gameDemo.setTurns(0);
        gameDemo.getPlayers().get(0).move(0,board[3][2]);
        assertEquals(gameDemo.getPlayers().get(0).getWorkerList().get(0).pos, board[3][2]);
        assertFalse(board[2][2].isOccupied());
    }

    @Test
    public void validMove_moveOnOccupiedBox_changesWorkerPos()throws Exception {
        gameDemo.setTurns(0);
        gameDemo.getPlayers().get(0).move(0,board[2][3]);
        assertEquals(board[2][2].getWorker(),gameDemo.getPlayers().get(1).getWorkerList().get(0));
        assertEquals(board[2][3].getWorker(),gameDemo.getPlayers().get(0).getWorkerList().get(0));
    }

}