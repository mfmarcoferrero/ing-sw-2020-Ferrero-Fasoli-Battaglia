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

    //setWorkerBoxesToMove
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
    public void setWorkerBoxesToMove_SomeFirstLevelsBoardCenter_AllAdjacentBoxes() {

        //initialize worker position
        x = 2;
        y = 2;
        currentWorker = game.getPlayers().get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        //set boxes levels
        board[1][1].setLevel(1);
        board[1][3].setLevel(1);
        board[2][3].setLevel(1);
        board[3][2].setLevel(1);

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
    public void setWorkerBoxesToMove_SomeUpperLevelsBoardCenter_AdjacentBoxesExceptUpperLevels () {

        //initialize worker position
        x = 2;
        y = 2;
        currentWorker = game.getPlayers().get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        //set boxes levels
        board[1][1].setLevel(3);
        board[1][3].setLevel(2);
        board[2][3].setLevel(3);
        board[3][2].setLevel(2);

        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[1][2]);
        expected.add(board[2][1]);
        expected.add(board[3][1]);
        expected.add(board[3][3]);

        ArrayList<Box> result = game.getPlayers().get(0).setWorkerBoxesToMove(currentWorker);

        assertEquals(expected, result);
    }

    @Test
    public void setWorkerBoxesToMove_SomeOccupiedBoxesAndUpperLevelsBoardCenter_AdjacentBoxesExceptOccupiedOrUpperLevels () {

        //initialize worker position
        x = 2;
        y = 2;
        currentWorker = game.getPlayers().get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        //set boxes levels
        board[1][2].setDome(true);
        board[2][1].setLevel(3);
        board[3][1].setLevel(3);
        board[3][1].setDome(true);
        board[3][3].setWorker(game.getPlayers().get(0).choseWorker(false));

        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[1][1]);
        expected.add(board[1][3]);
        expected.add(board[2][3]);
        expected.add(board[3][2]);

        ArrayList<Box> result = game.getPlayers().get(0).setWorkerBoxesToMove(currentWorker);

        assertEquals(expected, result);
    }

    @Test
    public void setWorkerBoxesToMove_FromSecondLevelBoardCenter_AllAdjacentBoxes () {

        //initialize worker position
        x = 2;
        y = 2;
        board[2][2].setLevel(2);
        currentWorker = game.getPlayers().get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

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

        assertEquals(expected, result);
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

    //setWorkerBoxesToBuild

    @Test
    public void setWorkerBoxesToBuild() {
    }
}