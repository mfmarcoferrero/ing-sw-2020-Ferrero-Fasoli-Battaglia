package it.polimi.ingsw.PSP54.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Vector;

import static org.junit.Assert.*;

public class ArtemisDecoratorTest {

    private Game game;
    Vector<Player> players;
    Player player_1, player_2, player_3;
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
        game.newPlayer("1");
        game.newPlayer("2");
        game.newPlayer("3");
        players = game.getPlayers();

        //set Artemis power to player_1
        player_1 = players.get(0);
        player_1 = player_1.assignPower(1);

        player_2 = players.get(1);
        player_3 = players.get(2);
        player_1.setGame(game);
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
        artemisWorker = player_1.turnInit(true);
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

        ArrayList<Box> result = player_1.setWorkerBoxesToMove(artemisWorker);

        assertEquals(expected, result);
    }

    @Test
    public void setWorkerBoxesToMove_EmptyBoardsCenterWithMove_AdjacentBoxesExceptPreviousPosition() throws InvalidMoveException {

        //initialize worker and sets position
        x = 2;
        y = 2;
        artemisWorker = player_1.turnInit(true);
        artemisWorker.setPos(board[x][y]);
        board[x][y].setWorker(artemisWorker);

        player_1.setWorkerBoxesToMove(artemisWorker);

        player_1.move(artemisWorker, board[1][1]);

        //generate expected result
        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[0][0]);
        expected.add(board[0][1]);
        expected.add(board[0][2]);
        expected.add(board[1][0]);
        expected.add(board[1][2]);
        expected.add(board[2][0]);
        expected.add(board[2][1]);

        ArrayList<Box> result = player_1.setWorkerBoxesToMove(artemisWorker);

        assertEquals(artemisWorker.getPos(), board[1][1]);
        assertEquals(board[1][1].getWorker(), artemisWorker);
        assertEquals(expected, result);
        assertEquals(artemisWorker.getMoveToken(), 1);
    }

    @Test
    public void setWorkerBoxesToMove_DoubleMove_TryToGetBackThrowsException() {
        x = 2;
        y = 2;
        artemisWorker = player_1.turnInit(true);
        artemisWorker.setPos(board[x][y]);
        board[x][y].setWorker(artemisWorker);

        Exception thrown = null;
        try {
            doubleMove(artemisWorker, board[1][1], board[2][2]);
        } catch (InvalidMoveException e) {
            thrown = e;
        }

        assertEquals(artemisWorker.getMoveToken(), 1);

        assertNotNull(thrown);

        assertEquals(artemisWorker.getPos(), board[1][1]);
    }

    @Test
    public void build_DoubleMove_CorrectBuildingAndTokensToZero() throws InvalidMoveException, InvalidBuildingException {
        x = 2;
        y = 2;
        artemisWorker = player_1.turnInit(true);
        artemisWorker.setPos(board[x][y]);
        board[x][y].setWorker(artemisWorker);

        doubleMove(artemisWorker, board[1][1], board[0][0]);

        player_1.build(artemisWorker, board[1][1]);

        assertEquals(artemisWorker.getPos(), board[0][0]);
        assertEquals(board[0][0].getWorker(), artemisWorker);

        assertEquals(board[1][1].getLevel(), 1);
        assertEquals(artemisWorker.getMoveToken() + artemisWorker.getBuildToken(), 0);
    }

    private void doubleMove(Worker worker, Box dest1, Box dest2) throws InvalidMoveException {
        player_1.setWorkerBoxesToMove(worker);
        player_1.move(worker, dest1);
        player_1.setWorkerBoxesToMove(worker);
        player_1.move(worker, dest2);

    }
}