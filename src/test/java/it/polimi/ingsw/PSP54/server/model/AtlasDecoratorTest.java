package it.polimi.ingsw.PSP54.server.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

public class AtlasDecoratorTest {

    private Game game;
    Vector<Player> players;
    Box[][] board;
    Worker atlasWorker;
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
        players.set(0, players.get(0).assignPower(3));
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
    public void build_LevelThreeBuilding_NoQuestionAsked() throws InvalidMoveException, InvalidBuildingException {
        x = 2;
        y = 2;
        atlasWorker = players.get(0).turnInit(true);
        players.get(0).setCurrentWorker(atlasWorker);
        atlasWorker.setPos(board[x][y]);
        board[x][y].setWorker(atlasWorker);

        board[4][4].setLevel(3);

        players.get(0).setWorkerBoxesToMove(atlasWorker);
        players.get(0).move(atlasWorker, board[3][3]);

        players.get(0).setWorkerBoxesToBuild(atlasWorker);
        players.get(0).build(atlasWorker, board[4][4]);


        assertEquals(0, atlasWorker.getMoveToken());
        assertEquals(0, atlasWorker.getBuildToken());
        assertFalse(players.get(0).isPlaying());
        assertTrue(board[4][4].isDome());
    }

    @Test
    public void build_LevelZeroDome_CorrectOutput() throws InvalidMoveException, InvalidBuildingException {

        x = 2;
        y = 2;
        atlasWorker = players.get(0).turnInit(true);
        players.get(0).setCurrentWorker(atlasWorker);
        atlasWorker.setPos(board[x][y]);
        board[x][y].setWorker(atlasWorker);

        players.get(0).setWorkerBoxesToMove(atlasWorker);
        players.get(0).move(atlasWorker, board[3][3]);

        players.get(0).setWorkerBoxesToBuild(atlasWorker);
        players.get(0).build(atlasWorker, board[4][4]);

        players.get(0).chose(true);


        assertEquals(0, atlasWorker.getMoveToken());
        assertEquals(0, atlasWorker.getBuildToken());
        assertFalse(players.get(0).isPlaying());
        assertEquals(0, board[4][4].getLevel());
        assertTrue(board[4][4].isDome());
    }

    @Test
    public void build_LevelZeroStandard_CorrectOutput() throws InvalidMoveException, InvalidBuildingException {

        x = 2;
        y = 2;
        atlasWorker = players.get(0).turnInit(true);
        players.get(0).setCurrentWorker(atlasWorker);
        atlasWorker.setPos(board[x][y]);
        board[x][y].setWorker(atlasWorker);

        players.get(0).setWorkerBoxesToMove(atlasWorker);
        players.get(0).move(atlasWorker, board[3][3]);

        players.get(0).setWorkerBoxesToBuild(atlasWorker);
        players.get(0).build(atlasWorker, board[4][4]);

        players.get(0).chose(false);


        assertEquals(0, atlasWorker.getMoveToken());
        assertEquals(0, atlasWorker.getBuildToken());
        assertFalse(players.get(0).isPlaying());
        assertEquals(1, board[4][4].getLevel());
        assertFalse(board[4][4].isDome());
    }

}
