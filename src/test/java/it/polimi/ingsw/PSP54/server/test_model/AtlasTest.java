package it.polimi.ingsw.PSP54.server.test_model;

import it.polimi.ingsw.PSP54.server.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtlasTest {
    Game gameDemo;
    Box [][] board;

    @Before
    public void setUp() throws Exception{
        gameDemo = new Game();
        gameDemo.startGame();
        board = gameDemo.getBoard();
        gameDemo.newPlayer("Matteo",22,null);
        gameDemo.getPlayers().get(0).setPower(Player.ATLAS);
        gameDemo.getPlayers().get(0).setInitialPosition(0,board[2][2]);
    }

    @After
    public void tearDown() throws Exception {
        gameDemo = null;
        board = null;
    }

    @Test
    public void validBuilding_normalBuilding_setBuilding() throws Exception {
        gameDemo.setTurns(0);
        gameDemo.getPlayers().get(0).build(0,board[2][3],false);
        assertEquals(board[2][3].getLevel(),1);
    }

    @Test
    public void validBuilding_domeBuilding_setBuilding() throws Exception {
        gameDemo.setTurns(0);
        gameDemo.getPlayers().get(0).build(0,board[2][3],true);
        assertTrue(board[2][3].isDome());
    }

    @Test
    public void validBuilding_boxIsOccupied_throwsInvalidBuildingException() throws Exception {
        gameDemo.setTurns(0);
        board[2][3].setWorker(new Worker(null,null,0));
        try{
            gameDemo.getPlayers().get(0).build(0,board[2][3],true);
        } catch (InvalidBuildingException e){
            assertTrue(true);
        }
    }

    @Test
    public void validBuilding_boxIsCompleted_throwsInvalidBuildingException() throws Exception {
        gameDemo.setTurns(0);
        board[2][3].setDome(true);
        try{
            gameDemo.getPlayers().get(0).build(0,board[2][3],true);
        } catch (InvalidBuildingException e){
            assertTrue(true);
        }
    }
}