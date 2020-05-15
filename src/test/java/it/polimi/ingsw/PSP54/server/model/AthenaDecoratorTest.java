package it.polimi.ingsw.PSP54.server.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Vector;

import static org.junit.Assert.*;

public class AthenaDecoratorTest {
    private Game game;
    Vector<Player> players;
    Box[][] board;
    Worker athenaWorker;
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

        //set Athena power to player_1
        players.set(0, players.get(0).assignPower(2));
        players.set(1, players.get(1).assignPower(1));
        players.set(2, players.get(2).assignPower(0));

        //set game
        players.get(0).setGame(game);
        players.get(1).setGame(game);
        players.get(2).setGame(game);
    }

    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void move_AthenaMovesUp_OthersCant() throws InvalidMoveException {

        //initialize athenaWorker and set position
        x = 2;
        y = 2;
        athenaWorker = players.get(0).turnInit(true);
        athenaWorker.setPos(board[x][y]);
        board[x][y].setWorker(athenaWorker);

        //set boxes levels
        board[1][1].setLevel(1);
        board[4][2].setLevel(1);
        board[0][4].setLevel(2);

        //initialize other workers and set positions
        Worker victim1 = players.get(1).turnInit(false);
        Worker victim2 = players.get(1).turnInit(true);

        victim1.setPos(board[3][2]);
        board[3][2].setWorker(victim1);
        victim2.setPos(board[0][4]);
        board[0][4].setWorker(victim2);

        //perform athena move
        players.get(0).setWorkerBoxesToMove(athenaWorker);
        players.get(0).move(athenaWorker, board[1][1]);

        //set victim1 expectations
        ArrayList<Box> exp1 = new ArrayList<>();
        exp1.add(board[2][1]);
        exp1.add(board[2][2]);
        exp1.add(board[2][3]);
        exp1.add(board[3][1]);
        exp1.add(board[3][3]);
        exp1.add(board[4][1]);
        exp1.add(board[4][3]);

        ArrayList<Box> res1 = players.get(1).setWorkerBoxesToMove(victim1);

        assertEquals(exp1, res1);

        //set victim2 expectations
        ArrayList<Box> exp2 = new ArrayList<>();
        exp2.add(board[0][3]);
        exp2.add(board[1][3]);
        exp2.add(board[1][4]);

        ArrayList<Box> res2 = players.get(1).setWorkerBoxesToMove(victim2);

        assertEquals(exp2, res2);

    }

    @Test
    public void move_AthenaMovesUpAndDown_ReassignPreviousPowers() throws InvalidMoveException {

        //initialize athenaWorker and set position
        x = 2;
        y = 2;
        athenaWorker = players.get(0).turnInit(true);
        athenaWorker.setPos(board[x][y]);
        board[x][y].setWorker(athenaWorker);

        //set boxes levels
        board[1][1].setLevel(1);
        board[4][2].setLevel(1);
        board[0][4].setLevel(2);

        //initialize other workers and set positions
        Worker victim1 = players.get(1).turnInit(false);
        Worker victim2 = players.get(1).turnInit(true);

        victim1.setPos(board[3][2]);
        board[3][2].setWorker(victim1);
        victim2.setPos(board[0][4]);
        board[0][4].setWorker(victim2);

        //perform athena move
        players.get(0).setWorkerBoxesToMove(athenaWorker);
        players.get(0).move(athenaWorker, board[1][1]);

        //go back
        athenaWorker.setMoveToken(1);
        players.get(0).setWorkerBoxesToMove(athenaWorker);
        players.get(0).move(athenaWorker, board[x][y]);

        assertEquals(athenaWorker.getPos(), board[x][y]);

        //set victim1 expectations
        ArrayList<Box> exp1 = new ArrayList<>();
        exp1.add(board[2][1]);
        exp1.add(board[2][3]);
        exp1.add(board[3][1]);
        exp1.add(board[3][3]);
        exp1.add(board[4][1]);
        exp1.add(board[4][2]);
        exp1.add(board[4][3]);

        ArrayList<Box> res1 = players.get(1).setWorkerBoxesToMove(victim1);

        assertEquals(exp1, res1);

        //set victim2 expectations
        ArrayList<Box> exp2 = new ArrayList<>();
        exp2.add(board[0][3]);
        exp2.add(board[1][3]);
        exp2.add(board[1][4]);

        ArrayList<Box> res2 = players.get(1).setWorkerBoxesToMove(victim2);

        assertEquals(exp2, res2);

    }

    @Test
    public void move_AthenaMovesUpTwice_OthersCant() throws InvalidMoveException {

        //initialize athenaWorker and set position
        x = 2;
        y = 2;
        athenaWorker = players.get(0).turnInit(true);
        athenaWorker.setPos(board[x][y]);
        board[x][y].setWorker(athenaWorker);

        //set boxes levels
        board[0][0].setLevel(2);
        board[1][1].setLevel(1);
        board[4][2].setLevel(1);
        board[0][4].setLevel(2);
        board[1][4].setLevel(3);

        //initialize other workers and set positions
        Worker victim1 = players.get(1).turnInit(false);
        Worker victim2 = players.get(1).turnInit(true);

        victim1.setPos(board[3][2]);
        board[3][2].setWorker(victim1);
        victim2.setPos(board[0][4]);
        board[0][4].setWorker(victim2);

        //perform athena move
        players.get(0).setWorkerBoxesToMove(athenaWorker);
        players.get(0).move(athenaWorker, board[1][1]);

        //go back
        athenaWorker.setMoveToken(1);
        players.get(0).setWorkerBoxesToMove(athenaWorker);
        players.get(0).move(athenaWorker, board[0][0]);

        //set victim1 expectations
        ArrayList<Box> exp1 = new ArrayList<>();
        exp1.add(board[2][1]);
        exp1.add(board[2][2]);
        exp1.add(board[2][3]);
        exp1.add(board[3][1]);
        exp1.add(board[3][3]);
        exp1.add(board[4][1]);
        exp1.add(board[4][3]);

        ArrayList<Box> res1 = players.get(1).setWorkerBoxesToMove(victim1);

        assertEquals(exp1, res1);

        //set victim2 expectations
        ArrayList<Box> exp2 = new ArrayList<>();
        exp2.add(board[0][3]);
        exp2.add(board[1][3]);

        ArrayList<Box> res2 = players.get(1).setWorkerBoxesToMove(victim2);

        assertEquals(exp2, res2);

    }

}