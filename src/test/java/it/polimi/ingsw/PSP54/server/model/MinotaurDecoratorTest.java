package it.polimi.ingsw.PSP54.server.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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


    @Test
    public void move_ForceOpponent_CorrectOutput() throws InvalidMoveException {

        //set workers initial position
        minotaurWorker = players.get(0).turnInit(true);
        minotaurWorker.setPos(board[2][2]);
        board[2][2].setWorker(minotaurWorker);

        victimWorker = players.get(1).turnInit(false);
        victimWorker.setPos(board[1][1]);
        board[1][1].setWorker(victimWorker);

        //set available boxes
        players.get(0).setWorkerBoxesToMove(minotaurWorker);

        //invoke move() method
        players.get(0).move(minotaurWorker, board[1][1]);


        //set result
        Box minotaurExpected = board [1][1];
        Box victimExpected = board[0][0];

        assertEquals(minotaurWorker.getPos(), minotaurExpected);
        assertEquals(minotaurExpected.getWorker(), minotaurWorker);

        assertEquals(victimWorker.getPos(), victimExpected);
        assertEquals(victimExpected.getWorker(), victimWorker);
    }

    @Test
    public void move_TryForceOpponentFromCorner_ShouldThrowException(){

        Exception thrown = null;

        //set workers initial position
        minotaurWorker = players.get(0).turnInit(true);
        minotaurWorker.setPos(board[1][1]);
        board[1][1].setWorker(minotaurWorker);

        victimWorker = players.get(1).turnInit(false);
        victimWorker.setPos(board[0][0]);
        board[0][0].setWorker(victimWorker);

        //set available boxes
        players.get(0).setWorkerBoxesToMove(minotaurWorker);

        //invoke move() method
        try {
            players.get(0).move(minotaurWorker, board[0][0]);
        } catch (InvalidMoveException e) {
            thrown = e;
        }

        //set expected results
        Box minotaurExpected = board [1][1];
        Box victimExpected = board[0][0];

        assertNotNull(thrown);

        assertEquals(minotaurWorker.getPos(), minotaurExpected);
        assertEquals(minotaurExpected.getWorker(), minotaurWorker);

        assertEquals(victimWorker.getPos(), victimExpected);
        assertEquals(victimExpected.getWorker(), victimWorker);
    }

    @Test
    public void move_TryForceOpponentToOccupied_ShouldThrowException(){

        Exception thrown = null;

        //set workers initial position
        minotaurWorker = players.get(0).turnInit(true);
        minotaurWorker.setPos(board[2][2]);
        board[2][2].setWorker(minotaurWorker);

        victimWorker = players.get(1).turnInit(false);
        victimWorker.setPos(board[1][1]);
        board[1][1].setWorker(victimWorker);

        Worker obstacleWorker = players.get(2).turnInit(false);
        obstacleWorker.setPos(board[0][0]);
        board[0][0].setWorker(obstacleWorker);

        //set available boxes
        players.get(0).setWorkerBoxesToMove(minotaurWorker);

        //invoke move() method
        try {
            players.get(0).move(minotaurWorker, board[1][1]);
        } catch (InvalidMoveException e) {
            thrown = e;
        }


        //set expected results
        Box minotaurExpected = board [2][2];
        Box victimExpected = board[1][1];
        Box obstacleExpected = board[0][0];

        assertNotNull(thrown);

        assertEquals(minotaurWorker.getPos(), minotaurExpected);
        assertEquals(minotaurExpected.getWorker(), minotaurWorker);

        assertEquals(victimWorker.getPos(), victimExpected);
        assertEquals(victimExpected.getWorker(), victimWorker);

        assertEquals(obstacleWorker.getPos(), obstacleExpected);
        assertEquals(obstacleExpected.getWorker(), obstacleWorker);
    }

}