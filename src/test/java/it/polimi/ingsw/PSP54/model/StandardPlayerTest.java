package it.polimi.ingsw.PSP54.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class StandardPlayerTest {

    private Game game;
    Box[][] board;
    Worker currentWorker;
    int x;
    int y;


    @Before
    public void setUp() throws Exception {
        game = new Game();
        board = game.getBoard();
        game.newPlayer("player");
        game.getPlayers().get(0).setGame(game);
    }

    @After
    public void tearDown() throws Exception {
        game = null;
    }

    @Test
    public void setWorkerBoxesToMove_EmptyBoardsCenter_AllAdjacentBoxes() {

        //initialize worker position
        x = 2;
        y = 2;
        currentWorker = game.getPlayers().get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        //set boxes level

        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[1][1]);
        expected.add(board[1][2]);
        expected.add(board[1][3]);
        expected.add(board[2][1]);
        expected.add(board[2][3]);
        expected.add(board[3][1]);
        expected.add(board[3][2]);
        expected.add(board[3][3]);

        ArrayList<Box> result = game.getPlayers().get(0).setWorkerBoxesToMove(currentWorker);

        assertEquals(expected, result );
    }

    @Test
    public void setWorkerBoxesToMove_EmptyBoardsEdge_CornerBoxes() {

        x = 4;
        y = 4;
        currentWorker = game.getPlayers().get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[3][3]);
        expected.add(board[3][4]);
        expected.add(board[4][3]);

        ArrayList<Box> result = game.getPlayers().get(0).setWorkerBoxesToMove(currentWorker);

        assertEquals(expected, result);
    }

    @Test
    public void setWorkerBoxesToBuild() {
    }
}