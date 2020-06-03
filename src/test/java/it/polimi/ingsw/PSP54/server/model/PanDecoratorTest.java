package it.polimi.ingsw.PSP54.server.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

public class PanDecoratorTest {

    private Game game;
    Vector<Player> players;
    Box[][] board;
    Worker panWorker;

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

        //set Pan power to player_1
        players.set(0, players.get(0).assignPower(Game.PAN));
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
    public void move_DownTwoLevel_Win() throws InvalidMoveException {
        panWorker = players.get(0).turnInit(true);

        board[2][2].setLevel(2);

        panWorker.setPos(board[2][2]);
        board[2][2].setWorker(panWorker);

        players.get(0).setWorkerBoxesToMove(panWorker);
        players.get(0).move(panWorker, board[1][1]);

        assertEquals(game.getWinner(), players.get(0));
    }

    @Test
    public void move_FromThirdToThird_NotWinner() throws InvalidMoveException {
        panWorker = players.get(0).turnInit(true);

        board[1][1].setLevel(3);
        board[2][2].setLevel(3);

        panWorker.setPos(board[2][2]);
        board[2][2].setWorker(panWorker);

        players.get(0).setWorkerBoxesToMove(panWorker);
        players.get(0).move(panWorker, board[1][1]);

        assertNull(game.getWinner());
    }

}