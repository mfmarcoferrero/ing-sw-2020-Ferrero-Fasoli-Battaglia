package it.polimi.ingsw.PSP54.server.test_model;

import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.server.model.InvalidMoveException;
import it.polimi.ingsw.PSP54.server.model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArtemisTest {
    Game gameDemo;
    Box [][] board;

    @Before
    public void setUp() throws Exception {
        gameDemo = new Game();
        gameDemo.startGame();
        board = gameDemo.getBoard();
        gameDemo.newPlayer("Marco",22,null);
        gameDemo.getPlayers().get(0).setPower(Player.ARTEMIS);
        gameDemo.getPlayers().get(0).setInitialPosition(0,board[2][2]);
    }

    @After
    public void tearDown() {
        gameDemo = null;
        board = null;
    }

    @Test
    public void validMove_doubleMove_correctMove() throws InvalidMoveException {
        gameDemo.setTurns(0);
        gameDemo.getPlayers().get(0).move(0,board[2][3]);
        gameDemo.getPlayers().get(0).move(0,board[2][4]);
        assertTrue(gameDemo.getPlayers().get(0).getWorkerList().get(0).pos == board[2][4]);
    }

    @Test
    public void validMove_doubleMoveBackInPreviousBox_notAllowed() throws Exception {
        gameDemo.setTurns(0);
        gameDemo.getPlayers().get(0).move(0,board[2][3]);
        try{
            gameDemo.getPlayers().get(0).move(0,board[2][2]);
        } catch (InvalidMoveException e){
            assertTrue(true);
        }
    }

    @Test
    public void validMove_doubleMoveWithDifferentWorker_notAllowed() throws Exception {
        gameDemo.setTurns(0);
        gameDemo.getPlayers().get(0).setInitialPosition(1,board[0][0]);
        gameDemo.getPlayers().get(0).move(0,board[2][3]);
        try{
            gameDemo.getPlayers().get(0).move(1,board[1][1]);
        } catch (InvalidMoveException e){
            assertTrue(true);
        }
    }



}