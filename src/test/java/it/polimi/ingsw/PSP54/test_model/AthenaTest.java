package it.polimi.ingsw.PSP54.test_model;

import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.server.model.InvalidMoveException;
import it.polimi.ingsw.PSP54.server.model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AthenaTest {
    Game gameDemo;
    Box[][] board;

    @Before
    public void setUp() throws Exception {
        gameDemo = new Game();
        gameDemo.startGame();
        board = gameDemo.getBoard();
        gameDemo.newPlayer("Matteo",22,null);
        gameDemo.newPlayer("Marco",27,null);
        gameDemo.newPlayer("Alessandro",24,null);
        gameDemo.getPlayers().get(0).setPower(Player.ATHENA);
        gameDemo.getPlayers().get(0).setInitialPosition(0,board[2][2]);
        gameDemo.getPlayers().get(1).setPower(Player.NORMAL_POWER);
        gameDemo.getPlayers().get(1).setInitialPosition(0,board[0][1]);
        gameDemo.getPlayers().get(2).setPower(Player.NORMAL_POWER);
        gameDemo.getPlayers().get(2).setInitialPosition(0,board[2][4]);
    }

    @After
    public void tearDown() throws Exception {
        gameDemo = null;
        board = null;
    }

    @Test
    public void validMove_athenaPowerSet_booleanSet() throws InvalidMoveException {
        gameDemo.setTurns(0);
        board[2][3].setLevel(1);
        gameDemo.getPlayers().get(0).move(0,board[2][3]);
        for (Player p : gameDemo.getPlayers()){
            if (p.getGodID() != Player.ATHENA){
                assertFalse(p.power.isCanMoveUp());
            }
            if (p.getGodID() == Player.ATHENA){
                assertTrue(p.power.isCanMoveUp());
            }
        }
    }

    @Test
    public void validMove_athenaPowerSet_throwsInvalidMoveException() throws InvalidMoveException {
        gameDemo.setTurns(0);
        board[2][3].setLevel(1);
        board[3][4].setLevel(1);
        board[0][1].setLevel(1);
        board[0][0].setLevel(2);
        gameDemo.getPlayers().get(0).move(0,board[2][3]);
        gameDemo.setTurns(1);
        try {
            gameDemo.getPlayers().get(1).move(0,board[0][0]);
        } catch (InvalidMoveException e){
            assertTrue(true);
        }
        gameDemo.setTurns(2);
        try {
            gameDemo.getPlayers().get(2).move(0,board[3][4]);
        } catch (InvalidMoveException e){
            assertTrue(true);
        }
    }

    @Test
    public void validMove_athenaResetPlayersPower_booleanReset() throws InvalidMoveException {
        gameDemo.setTurns(0);
        board[2][3].setLevel(1);
        gameDemo.getPlayers().get(0).move(0,board[2][3]);
        gameDemo.setTurns(0);
        gameDemo.getPlayers().get(0).move(0,board[2][2]);
        for (Player p : gameDemo.getPlayers()){
            assertTrue(p.power.isCanMoveUp());
        }
    }

    @Test
    public void validMove_athenaResetPlayersPower_allowsNormalMove() throws InvalidMoveException {
        gameDemo.setTurns(0);
        board[2][3].setLevel(1);
        board[3][3].setLevel(1);
        board[3][4].setLevel(1);
        board[0][1].setLevel(1);
        board[0][0].setLevel(2);
        // Athena sale di livello
        gameDemo.getPlayers().get(0).move(0,board[2][3]);
        gameDemo.setTurns(0);
        // Athena si muove di nuovo senza salire di livello
        gameDemo.getPlayers().get(0).move(0,board[3][3]);
        // Gli altri giocatori eseguono mosse normali
        gameDemo.setTurns(1);
        gameDemo.getPlayers().get(1).move(0,board[0][0]);
        gameDemo.setTurns(2);
        gameDemo.getPlayers().get(2).move(0,board[3][4]);
        assertEquals(gameDemo.getPlayers().get(1).getWorkerList().get(0).pos,board[0][0]);
        assertEquals(gameDemo.getPlayers().get(2).getWorkerList().get(0).pos,board[3][4]);
    }
}