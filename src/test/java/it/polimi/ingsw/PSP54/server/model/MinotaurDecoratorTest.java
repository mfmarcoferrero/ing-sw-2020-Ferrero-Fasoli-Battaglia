package it.polimi.ingsw.PSP54.server.model;

import org.junit.After;
import org.junit.Before;

import java.util.Vector;

import static org.junit.Assert.*;

public class MinotaurDecoratorTest {

    private Game game;
    Vector<Player> players;
    Box[][] board;
    Worker minotaurWorker;
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
        players.set(0, players.get(0).assignPower(6));
        players.get(0).setGame(game);
        //sets other players
        players.get(1).setGame(game);
        players.get(2).setGame(game);
    }

    @After
    public void tearDown() {
        game = null;
    }



}