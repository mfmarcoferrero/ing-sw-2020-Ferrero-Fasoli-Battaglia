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

        x = 3;
        y= 3;
        currentWorker = game.getPlayers().get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[2][2]);
        expected.add(board[2][3]);
        expected.add(board[2][4]);
        expected.add(board[3][2]);
        expected.add(board[3][4]);
        expected.add(board[4][2]);
        expected.add(board[4][3]);
        expected.add(board[4][4]);


        ArrayList<Box> result = game.getPlayers().get(0).setWorkerBoxesToMove(currentWorker);

        assertEquals(expected, result );
    }

    @Test
    public void setWorkerBoxesToBuild() {
    }
}