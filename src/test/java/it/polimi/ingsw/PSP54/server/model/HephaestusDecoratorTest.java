package it.polimi.ingsw.PSP54.server.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

public class HephaestusDecoratorTest {

    private Game game;
    Vector<Player> players;
    Box[][] board;
    Worker hephaestusWorker;
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
        players.set(0, players.get(0).assignPower(5));
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
    public void chose_DoubleBuilding_CorrectOutput() throws InvalidMoveException, InvalidBuildingException {

        x = 2;
        y = 2;
        hephaestusWorker = players.get(0).turnInit(true);
        players.get(0).setCurrentWorker(hephaestusWorker);
        hephaestusWorker.setPos(board[x][y]);
        board[x][y].setWorker(hephaestusWorker);

        board[4][4].setLevel(1);

        players.get(0).setWorkerBoxesToMove(hephaestusWorker);
        players.get(0).move(hephaestusWorker, board[3][3]);

        assertEquals(0, hephaestusWorker.getMoveToken());
        assertEquals(2, hephaestusWorker.getBuildToken());

        players.get(0).setWorkerBoxesToBuild(hephaestusWorker);
        players.get(0).build(hephaestusWorker, board[4][4]);

        players.get(0).chose(true);

        assertEquals(0, hephaestusWorker.getMoveToken());
        assertEquals(0, hephaestusWorker.getBuildToken());
        assertFalse(players.get(0).isPlaying());
        assertEquals(3, board[4][4].getLevel());
    }

    @Test
    public void chose_NoPower_CorrectOutput() throws InvalidBuildingException, InvalidMoveException {

        x = 2;
        y = 2;
        hephaestusWorker = players.get(0).turnInit(true);
        players.get(0).setCurrentWorker(hephaestusWorker);
        hephaestusWorker.setPos(board[x][y]);
        board[x][y].setWorker(hephaestusWorker);

        players.get(0).setWorkerBoxesToMove(hephaestusWorker);
        players.get(0).move(hephaestusWorker, board[3][3]);

        assertEquals(0, hephaestusWorker.getMoveToken());
        assertEquals(2, hephaestusWorker.getBuildToken());

        players.get(0).setWorkerBoxesToBuild(hephaestusWorker);
        players.get(0).build(hephaestusWorker, board[4][4]);

        players.get(0).chose(false);

        assertEquals(0, hephaestusWorker.getMoveToken());
        assertEquals(0, hephaestusWorker.getBuildToken());
        assertFalse(players.get(0).isPlaying());
        assertEquals(1, board[4][4].getLevel());
    }

    @Test
    public void chose_SecondBuildingShouldBeDome_NoSecondBuildingPerformed() throws InvalidMoveException, InvalidBuildingException {

        x = 2;
        y = 2;
        hephaestusWorker = players.get(0).turnInit(true);
        players.get(0).setCurrentWorker(hephaestusWorker);
        hephaestusWorker.setPos(board[x][y]);
        board[x][y].setWorker(hephaestusWorker);

        board[4][4].setLevel(2);

        players.get(0).setWorkerBoxesToMove(hephaestusWorker);
        players.get(0).move(hephaestusWorker, board[3][3]);

        assertEquals(0, hephaestusWorker.getMoveToken());
        assertEquals(2, hephaestusWorker.getBuildToken());

        players.get(0).setWorkerBoxesToBuild(hephaestusWorker);
        players.get(0).build(hephaestusWorker, board[4][4]);

        players.get(0).chose(true);

        assertEquals(0, hephaestusWorker.getMoveToken());
        assertEquals(0, hephaestusWorker.getBuildToken());
        assertFalse(players.get(0).isPlaying());
        assertEquals(3, board[4][4].getLevel());
    }
}