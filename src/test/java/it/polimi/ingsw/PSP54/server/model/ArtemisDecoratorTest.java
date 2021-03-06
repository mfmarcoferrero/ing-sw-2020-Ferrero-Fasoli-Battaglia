package it.polimi.ingsw.PSP54.server.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Vector;

import static org.junit.Assert.*;

public class ArtemisDecoratorTest {

    private Game game;
    Vector<Player> players;
    Box[][] board;
    Worker artemisWorker;
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

        //set Artemis power to player_1
        players.set(0, players.get(0).assignPower(1));
        players.get(0).setGame(game);
        players.get(1).setGame(game);
        players.get(2).setGame(game);
    }

    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void setWorkerBoxesToMove_EmptyBoardsCenter_AllAdjacentBoxes() {

        //initialize artemisWorker and set position
        x = 2;
        y = 2;
        artemisWorker = players.get(0).turnInit(true);
        artemisWorker.setPos(game.getBoard()[x][y]);
        board[x][y].setWorker(artemisWorker);

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

        ArrayList<Box> result = players.get(0).setWorkerBoxesToMove(artemisWorker);

        assertEquals(expected, result);
    }

    @Test
    public void setWorkerBoxesToMove_EmptyBoardsCenterWithMove_AdjacentBoxesExceptPreviousPosition() throws InvalidMoveException {

        //initialize worker and set position
        x = 2;
        y = 2;
        artemisWorker = players.get(0).turnInit(true);
        artemisWorker.setPos(board[x][y]);
        board[x][y].setWorker(artemisWorker);

        players.get(0).setWorkerBoxesToMove(artemisWorker);

        players.get(0).move(artemisWorker, board[1][1]);

        //generate expected result
        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[0][0]);
        expected.add(board[0][1]);
        expected.add(board[0][2]);
        expected.add(board[1][0]);
        expected.add(board[1][2]);
        expected.add(board[2][0]);
        expected.add(board[2][1]);

        ArrayList<Box> result = players.get(0).setWorkerBoxesToMove(artemisWorker);

        assertEquals(artemisWorker.getPos(), board[1][1]);
        assertEquals(board[1][1].getWorker(), artemisWorker);
        assertEquals(expected, result);
        assertEquals(-1, artemisWorker.getMoveToken());
    }

    @Test
    public void setWorkerBoxesToMove_DoubleMove_TryToGetBackThrowsException() {
        x = 2;
        y = 2;
        artemisWorker = players.get(0).turnInit(true);
        players.get(0).setCurrentWorker(artemisWorker);
        artemisWorker.setPos(board[x][y]);
        board[x][y].setWorker(artemisWorker);

        Exception thrown = null;
        try {
            doubleMove(artemisWorker, board[1][1], board[2][2]);
        } catch (InvalidMoveException e) {
            thrown = e;
        }

        assertEquals(1, artemisWorker.getMoveToken());

        assertNotNull(thrown);

        assertEquals(artemisWorker.getPos(), board[1][1]);
    }

    @Test
    public void build_DoubleMove_CorrectBuildingAndTokensToZero() throws InvalidMoveException, InvalidBuildingException {
        x = 2;
        y = 2;
        artemisWorker = players.get(0).turnInit(true);
        players.get(0).setCurrentWorker(artemisWorker);
        artemisWorker.setPos(board[x][y]);
        board[x][y].setWorker(artemisWorker);

        doubleMove(artemisWorker, board[1][1], board[0][0]);

        players.get(0).setWorkerBoxesToBuild(artemisWorker);
        players.get(0).build(artemisWorker, board[1][1]);

        assertEquals(artemisWorker.getPos(), board[0][0]);
        assertEquals(board[0][0].getWorker(), artemisWorker);

        assertEquals(1, board[1][1].getLevel());
        assertEquals(0, artemisWorker.getMoveToken() + artemisWorker.getBuildToken());
    }

    private void doubleMove(Worker worker, Box dest1, Box dest2) throws InvalidMoveException {
        players.get(0).setWorkerBoxesToMove(worker);
        players.get(0).move(worker, dest1);
        players.get(0).chose(true);
        players.get(0).setWorkerBoxesToMove(worker);
        players.get(0).move(worker, dest2);

    }
}