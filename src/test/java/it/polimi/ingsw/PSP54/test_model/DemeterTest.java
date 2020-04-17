package it.polimi.ingsw.PSP54.test_model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import it.polimi.ingsw.PSP54.model.*;

import static org.junit.Assert.*;

public class DemeterTest {
    Game gameDemo;
    Box [][] board;

    @Before
    public void setUp() {
        gameDemo = new Game();
        gameDemo.startGame();
        board = gameDemo.getBoard();
        gameDemo.newPlayer("Matteo",22,null);
        gameDemo.getPlayers().get(0).setPower(Player.DEMETER);
        gameDemo.getPlayers().get(0).setInitialPosition(0,board[2][2]);
    }

    @After
    public void tearDown() throws Exception {
        gameDemo = null;
        board = null;
    }

    @Test
    public void validBuilding_doubleBuildingSameBox_buildingSet() throws Exception {
        gameDemo.setTurns(0);
        gameDemo.getPlayers().get(0).build(0,board[2][3],false);
        gameDemo.getPlayers().get(0).build(0,board[2][3],false);
        assertEquals(board[2][3].getLevel(),2);
    }

    @Test
    public void validBuilding_doubleBuildingDifferentBox_buildingSet() throws Exception {
        gameDemo.setTurns(0);
        gameDemo.getPlayers().get(0).build(0,board[2][3],false);
        gameDemo.getPlayers().get(0).build(0,board[3][2],false);
        assertEquals(board[2][3].getLevel(),1);
        assertEquals(board[3][2].getLevel(),1);
    }

    @Test
    public void validBuilding_differentWorkerChoose_throwsInvalidBuildingException() throws Exception {
        gameDemo.setTurns(0);
        gameDemo.getPlayers().get(0).setInitialPosition(1,board[0][0]);
        try {
            gameDemo.getPlayers().get(0).build(0,board[2][3],false);
            gameDemo.getPlayers().get(0).build(1,board[0][1],false);
        }
        catch (InvalidBuildingException e){
            assertTrue(true); ;
        }
    }

    @Test
    public void validBuilding_boxCompleted_throwsInvalidBuildingException() throws Exception {
        gameDemo.setTurns(0);
        try {
            board[2][3].setLevel(3);
            gameDemo.getPlayers().get(0).build(0,board[2][3],false);
            gameDemo.getPlayers().get(0).build(0,board[2][3],false);
        }
        catch (InvalidBuildingException e ){
            assertTrue(true);
        }
    }
}