package it.polimi.ingsw.PSP54.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Vector;

public class DemeterDecoratorTest {

    private Game game;
    Vector<Player> players;
    Box[][] board;
    Worker demeterWorker;
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

        //set Demeter power to player 1
        players.set(0, players.get(0).assignPower(4));
        players.set(1, players.get(1));
        players.set(2, players.get(2));

        //set game
        players.get(0).setGame(game);
        players.get(1).setGame(game);
        players.get(2).setGame(game);
    }

    @After
    public void tearDown() throws Exception {
        game = null;
    }

    @Test
    public void build_EmptyBoardSecondBuild_CorrectOutput() throws InvalidMoveException, InvalidBuildingException {

        x = 2;
        y = 2;

        demeterWorker = players.get(0).turnInit(true);
        demeterWorker.setPos(board[x][y]);
        board[x][y].setWorker(demeterWorker);

        //set boxes level
        board[4][4].setLevel(3);

        players.get(0).setWorkerBoxesToMove(demeterWorker);
        players.get(0).move(demeterWorker, board[3][3]);
        players.get(0).build(demeterWorker, board[2][2]);
        players.get(0).build(demeterWorker, board[4][4]);

        assertEquals(board[3][3], demeterWorker.getPos());
        assertEquals(1, board[2][2].getLevel());
        assertEquals(true, board[4][4].isDome());

    }

    @Test
    public void build_EmptyBoardSecondBuildInLastBuilding_ShouldThrowException() throws InvalidMoveException, InvalidBuildingException {

        x = 2;
        y = 2;

        demeterWorker = players.get(0).turnInit(true);
        demeterWorker.setPos(board[x][y]);
        board[x][y].setWorker(demeterWorker);

        players.get(0).setWorkerBoxesToMove(demeterWorker);

        players.get(0).move(demeterWorker, board[2][1]);

        Exception thrown = null;

        players.get(0).build(demeterWorker, board[3][1]);

        try {
            players.get(0).build(demeterWorker, board[3][1]);
        } catch (InvalidBuildingException e) {
            thrown = e;
        }

        assertNotNull(thrown);
        assertEquals(board[2][1], demeterWorker.getPos());
        assertEquals(1, board[3][1].getLevel());
    }

    @Test
    public void move() throws InvalidMoveException {

        x = 0;
        y = 0;

        demeterWorker = players.get(0).turnInit(false);
        demeterWorker.setPos(board[x][y]);
        board[0][0].setWorker(demeterWorker);

        players.get(0).setWorkerBoxesToMove(demeterWorker);
        players.get(0).move(demeterWorker, board[1][0]);

        assertEquals(board[1][0], demeterWorker.getPos());
        assertEquals(demeterWorker, board[1][0].getWorker());
        assertEquals(2, demeterWorker.getBuildToken());
        assertEquals(0, demeterWorker.getMoveToken());

    }
}