package it.polimi.ingsw.PSP54.server.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Vector;

import static org.junit.Assert.*;

public class ApolloDecoratorTest {

    private Game game;
    Vector<Player> players;
    Box[][] board;
    Worker apolloWorker;
    Worker victimWorker;
    int x;
    int y;

    @Before
    public void setUp() {
        //initialize game
        game = new Game();
        board = game.getBoard();
        //initialize players
        game.newPlayer("1", 20, 0);
        game.newPlayer("2", 21, 1);
        game.newPlayer("3", 22, 2);
        players = game.getPlayers();

        //set Apollo power to player_1
        players.set(0, players.get(0).assignPower(0));
        players.get(0).setGame(game);
        //sets other players
        players.get(1).setGame(game);
        players.get(2).setGame(game);
    }

    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void setWorkerBoxesToMove_EmptyBoardsCenter_AllAdjacentBoxes() {

        //initialize worker and sets position
        x = 2;
        y = 2;
        apolloWorker = players.get(0).getWorker(true);
        apolloWorker.setPos(game.getBoard()[x][y]);
        board[x][y].setWorker(apolloWorker);

        //generate expected result
        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[1][1]);
        expected.add(board[1][2]);
        expected.add(board[1][3]);
        expected.add(board[2][1]);
        expected.add(board[2][3]);
        expected.add(board[3][1]);
        expected.add(board[3][2]);
        expected.add(board[3][3]);

        ArrayList<Box> result = players.get(0).setWorkerBoxesToMove(apolloWorker);

        assertEquals(expected, result);
    }

    @Test
    public void setWorkerBoxesToMove_SomeOccupiedBoxesAndUpperLevelsBoardCenter_AdjacentBoxesExceptUpperLevels () {

        //initialize worker and sets position
        x = 2;
        y = 2;
        apolloWorker = players.get(0).getWorker(true);
        apolloWorker.setPos(game.getBoard()[x][y]);
        board[x][y].setWorker(apolloWorker);

        //set boxes levels
        board[1][2].setDome(true);
        board[2][1].setLevel(3);
        board[3][1].setLevel(3);
        board[3][1].setDome(true);
        board[3][3].setWorker(players.get(1).getWorker(false));

        //generate expected result
        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[1][1]);
        expected.add(board[1][3]);
        expected.add(board[2][3]);
        expected.add(board[3][2]);
        expected.add(board[3][3]);

        ArrayList<Box> result = players.get(0).setWorkerBoxesToMove(apolloWorker);

        assertEquals(expected, result);
    }

    @Test
    public void setWorkerBoxesToMove_SomeUpperLevelsOccupiedBoardCenter_AdjacentBoxesExceptUpperLevels () {

        //initialize worker and sets position
        x = 2;
        y = 2;
        apolloWorker = players.get(0).getWorker(true);
        apolloWorker.setPos(game.getBoard()[x][y]);
        board[x][y].setWorker(apolloWorker);

        //set boxes levels
        board[1][1].setLevel(1);
        board[1][1].setWorker(players.get(1).getWorker(true));
        board[1][2].setDome(true);
        board[2][1].setLevel(3);
        board[3][1].setLevel(3);
        board[3][1].setDome(true);
        board[3][1].setWorker(players.get(1).getWorker(false));

        //generate expected result
        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[1][1]);
        expected.add(board[1][3]);
        expected.add(board[2][3]);
        expected.add(board[3][2]);
        expected.add(board[3][3]);

        ArrayList<Box> result = players.get(0).setWorkerBoxesToMove(apolloWorker);

        assertEquals(expected, result);
    }

    @Test
    public void move_GroundLevelCenter_NorthWestSwap() throws InvalidMoveException {

        //set workers initial position
        apolloWorker = players.get(0).turnInit(true);
        apolloWorker.setPos(board[2][2]);
        board[2][2].setWorker(apolloWorker);

        victimWorker = players.get(1).turnInit(false);
        victimWorker.setPos(board[1][1]);
        board[1][1].setWorker(victimWorker);

        //set available boxes
        //apolloWorker.setBoxesToMove(player_1.setWorkerBoxesToMove(apolloWorker));

        //invoke move() method
        players.get(0).move(apolloWorker, board[1][1]);


        //set result
        Box apolloExpected = board [1][1];
        Box victimExpected = board[2][2];

        assertEquals(apolloWorker.getPos(), apolloExpected);
        assertEquals(apolloExpected.getWorker(), apolloWorker);

        assertEquals(victimWorker.getPos(), victimExpected);
        assertEquals(victimExpected.getWorker(), victimWorker);

    }

    @Test
    public void move_GroundLevelCenter_OccupiedThrowException() {

        //set workers initial position
        apolloWorker = players.get(0).turnInit(true);
        apolloWorker.setPos(board[2][2]);
        board[2][2].setWorker(apolloWorker);

        victimWorker = players.get(1).turnInit(false);
        board[1][1].setLevel(2);
        victimWorker.setPos(board[1][1]);
        board[1][1].setWorker(victimWorker);

        Exception thrown = null;

        //invoke move() method
        try {
            players.get(0).move(apolloWorker, board[1][1]);
        }catch (InvalidMoveException e){
            thrown = e;
        }

        //set result
        Box apolloExpected = board [2][2];
        Box victimExpected = board[1][1];

        assertEquals(apolloWorker.getPos(), apolloExpected);
        assertEquals(apolloExpected.getWorker(), apolloWorker);

        assertEquals(victimWorker.getPos(), victimExpected);
        assertEquals(victimExpected.getWorker(), victimWorker);

        assertNotNull(thrown);

    }
}