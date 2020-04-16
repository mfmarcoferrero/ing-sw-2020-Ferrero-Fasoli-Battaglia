package it.polimi.ingsw.PSP54.test_model;

import it.polimi.ingsw.PSP54.model.Box;
import it.polimi.ingsw.PSP54.model.Game;
import it.polimi.ingsw.PSP54.model.Player;
import it.polimi.ingsw.PSP54.model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {
    Game gameDemo;
    Box [][] board;

    @Before
    public void setUp() throws Exception {
        gameDemo = new Game();
        gameDemo.startGame();
        board = gameDemo.getBoard();
        gameDemo.newPlayer("Matteo",0,null);
        gameDemo.getPlayers().get(0).setGodID(Player.NORMAL_POWER);
        gameDemo.getPlayers().get(0).setPower();
        gameDemo.getPlayers().get(0).setInitialPosition(0,board[2][2]);
    }

    @After
    public void tearDown() {
        gameDemo = null;
        board = null;
    }

    @Test
    public void canWorkerMove_normalMoveWithAllEmptyBoxes_returnTrue(){
        gameDemo.setTurns(0);
        assertTrue(gameDemo.getPlayers().get(0).getWorkerList().get(0).canWorkerMove());
    }

    @Test
    public void canWorkerMove_normalMoveWithOneEmptyBox_returnTrue(){
        gameDemo.setTurns(0);
        board[1][1].setDome(true);
        board[1][2].setDome(true);
        board[1][3].setDome(true);
        board[2][1].setDome(true);
        board[3][1].setDome(true);
        board[3][2].setDome(true);
        board[3][3].setDome(true);
        assertTrue(gameDemo.getPlayers().get(0).getWorkerList().get(0).canWorkerMove());
    }

    @Test
    public void canWorkerMove_normalMove_returnFalse(){
        gameDemo.setTurns(0);
        board[1][1].setDome(true);
        board[1][2].setDome(true);
        board[1][3].setDome(true);
        board[2][1].setDome(true);
        board[2][3].setDome(true);
        board[3][1].setDome(true);
        board[3][2].setDome(true);
        board[3][3].setDome(true);
        assertFalse(gameDemo.getPlayers().get(0).getWorkerList().get(0).canWorkerMove());
    }

    @Test
    public void canWorkerMove_normalMoveOnEdge_returnFalse(){
        gameDemo.setTurns(0);
        gameDemo.getPlayers().get(0).setInitialPosition(0,board[0][0]);
        board[0][1].setDome(true);
        board[1][1].setDome(true);
        board[1][0].setDome(true);
        assertFalse(gameDemo.getPlayers().get(0).getWorkerList().get(0).canWorkerMove());
    }

    @Test
    public void canWorkerMove_normalMoveWithOccupiedBoxes_returnFalse(){
        gameDemo.setTurns(0);
        board[1][1].setDome(true);
        board[1][2].setDome(true);
        board[1][3].setDome(true);
        board[2][1].setWorker(new Worker(null,null,0));
        board[2][3].setDome(true);
        board[3][1].setWorker(new Worker(null,null,0));
        board[3][2].setDome(true);
        board[3][3].setWorker(new Worker(null,null,0));
        assertFalse(gameDemo.getPlayers().get(0).getWorkerList().get(0).canWorkerMove());
    }

    @Test
    public void canWorkerBuild_normalBuildWithAllEmptyBoxes_returnTrue(){
        gameDemo.setTurns(0);
        assertTrue(gameDemo.getPlayers().get(0).getWorkerList().get(0).canWorkerBuild());
    }
    @Test
    public void canWorkerBuild_normalMoveWithOneBuildingBox_returnTrue(){
        gameDemo.setTurns(0);
        board[1][1].setDome(true);
        board[1][2].setDome(true);
        board[1][3].setLevel(2);
        board[2][1].setDome(true);
        board[3][1].setLevel(3);
        board[3][2].setDome(true);
        assertTrue(gameDemo.getPlayers().get(0).getWorkerList().get(0).canWorkerBuild());
    }

    @Test
    public void canWorkerBuild_normalBuildWithOccupiedBoxes_returnFalse(){
        gameDemo.setTurns(0);
        board[1][1].setDome(true);
        board[1][2].setDome(true);
        board[1][3].setDome(true);
        board[2][1].setWorker(new Worker(null,null,0));
        board[2][3].setDome(true);
        board[3][1].setWorker(new Worker(null,null,0));
        board[3][2].setDome(true);
        board[3][3].setWorker(new Worker(null,null,0));
        assertFalse(gameDemo.getPlayers().get(0).getWorkerList().get(0).canWorkerMove());
    }
    @Test
    public void canWorkerBuild_normalBuildOnEdge_returnFalse(){
        gameDemo.setTurns(0);
        gameDemo.getPlayers().get(0).setInitialPosition(0,board[0][0]);
        board[0][1].setDome(true);
        board[1][1].setDome(true);
        board[1][0].setDome(true);
        assertFalse(gameDemo.getPlayers().get(0).getWorkerList().get(0).canWorkerMove());
    }
}