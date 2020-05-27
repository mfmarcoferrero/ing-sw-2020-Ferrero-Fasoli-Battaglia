package it.polimi.ingsw.PSP54.server.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

public class PrometheusDecoratorTest {

    private Game game;
    Vector<Player> players;
    Box[][] board;
    Worker prometheusWorker;
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

        //set Atlas power to player_1
        players.set(0, players.get(0).assignPower(8));
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
    public void chose_NoPower_StandardTurn() throws InvalidMoveException, InvalidBuildingException {

        x = 2;
        y = 2;
        prometheusWorker = players.get(0).turnInit(true);
        players.get(0).setCurrentWorker(prometheusWorker);
        prometheusWorker.setPos(board[x][y]);
        board[x][y].setWorker(prometheusWorker);

        players.get(0).chose(false);

        board[4][4].setLevel(1);

        players.get(0).setWorkerBoxesToMove(prometheusWorker);
        players.get(0).move(prometheusWorker, board[3][3]);

        assertEquals(0, prometheusWorker.getMoveToken());
        assertEquals(1, prometheusWorker.getBuildToken());

        players.get(0).setWorkerBoxesToBuild(prometheusWorker);
        players.get(0).build(prometheusWorker, board[4][4]);

        assertEquals(0, prometheusWorker.getMoveToken());
        assertEquals(0, prometheusWorker.getBuildToken());
        assertFalse(players.get(0).isPlaying());
        assertEquals(2, board[4][4].getLevel());
    }

    @Test
    public void chose_PowerApplied_SpecialTurn() throws InvalidMoveException, InvalidBuildingException {

        x = 2;
        y = 2;
        prometheusWorker = players.get(0).turnInit(true);
        players.get(0).setCurrentWorker(prometheusWorker);
        prometheusWorker.setPos(board[x][y]);
        board[x][y].setWorker(prometheusWorker);

        board[4][4].setLevel(1);

        players.get(0).chose(true);

        players.get(0).setWorkerBoxesToBuild(prometheusWorker);
        players.get(0).build(prometheusWorker, board[1][1]);

        assertEquals(1, board[1][1].getLevel());
        assertEquals(0, prometheusWorker.getBuildToken());
        assertEquals(1, prometheusWorker.getMoveToken());

        players.get(0).setWorkerBoxesToMove(prometheusWorker);
        players.get(0).move(prometheusWorker, board[3][3]);

        assertEquals(1, prometheusWorker.getBuildToken());
        assertEquals(0, prometheusWorker.getMoveToken());

        players.get(0).setWorkerBoxesToBuild(prometheusWorker);
        players.get(0).build(prometheusWorker, board[4][4]);

        assertEquals(0, prometheusWorker.getMoveToken());
        assertEquals(0, prometheusWorker.getBuildToken());
        assertFalse(players.get(0).isPlaying());
        assertEquals(2, board[4][4].getLevel());
    }

    @Test
    public void move_PowerAppliedTriesToMoveUp_ThrowsException() throws InvalidBuildingException {

        Exception thrown = null;

        x = 2;
        y = 2;
        prometheusWorker = players.get(0).turnInit(true);
        players.get(0).setCurrentWorker(prometheusWorker);
        prometheusWorker.setPos(board[x][y]);
        board[x][y].setWorker(prometheusWorker);

        board[4][4].setLevel(1);

        players.get(0).chose(true);

        players.get(0).setWorkerBoxesToBuild(prometheusWorker);
        players.get(0).build(prometheusWorker, board[3][3]);

        assertEquals(1, board[3][3].getLevel());
        assertEquals(0, prometheusWorker.getBuildToken());
        assertEquals(1, prometheusWorker.getMoveToken());

        players.get(0).setWorkerBoxesToMove(prometheusWorker);
        try {
            players.get(0).move(prometheusWorker, board[3][3]);
        } catch (InvalidMoveException e) {
            thrown = e;
        }

        assertNotNull(thrown);
        assertNull(board[3][3].getWorker());
        assertEquals(0, prometheusWorker.getBuildToken());
        assertEquals(1, prometheusWorker.getMoveToken());
    }
}