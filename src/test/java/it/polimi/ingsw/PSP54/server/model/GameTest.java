package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.utils.PlayerAction;
import it.polimi.ingsw.PSP54.utils.choices.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.*;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    private Vector<Player> players;
    private Box[][] board;

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
    }

    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void sortPlayers_SameAge_OrderOfInsertion() {

        players.get(0).setAge(23);
        players.get(1).setAge(23);
        players.get(2).setAge(23);

        game.sortPlayers();

        int i = 1;
        for (Player player : players) {
            String name = player.getPlayerName();
            assertEquals(Integer.toString(i), name);
            i++;
        }

    }

    @Test
    public void sortPlayers_DifferentAges_InverseOrder() {

        players.get(0).setAge(43);
        players.get(1).setAge(32);
        players.get(2).setAge(5);

        game.sortPlayers();

        int i = 3;
        for (Player player : players) {
            String name = player.getPlayerName();
            assertEquals(Integer.toString(i), name);
            i--;
        }

    }

    @Test
    public void assignColors_RandomOrder_OrderedColors() {

        Random selector = new Random();
        for (Player player : players){
            player.setAge(selector.nextInt());
        }

        game.sortPlayers();

        game.assignColors();

        assertEquals("blue", players.get(0).getColor());
        assertEquals("red", players.get(1).getColor());
        assertEquals("yellow", players.get(2).getColor());
    }

    @Test
    public void performPowerAssignment_ApolloPower_CorrectOutput() {

        game.setCurrentPlayer(players.get(0));

        game.getExtractedCards().put(0, "Apollo");

        PlayerAction cardSelection = new PlayerAction(0, new CardChoice(0));

        game.performPowerAssignment(cardSelection);

        assertTrue(players.get(0) instanceof ApolloDecorator);
        assertFalse(players.get(0).isPlaying());
        assertTrue(players.get(1).isPlaying());
    }

    @Test
    public void performWorkerChoice_FirstSelection_CorrectOutput() {

        game.setCurrentPlayer(players.get(0));
        game.getCurrentPlayer().setVirtualViewId(0);

        PlayerChoice workerChoice = new WorkerChoice(true);
        PlayerAction workerSelection = new PlayerAction(0, workerChoice);

        game.performWorkerChoice(workerSelection);

        assertEquals(0, game.getCurrentPlayer().getCurrentWorker().getMoveToken());
        assertEquals(0, game.getCurrentPlayer().getCurrentWorker().getBuildToken());
        assertEquals(players.get(0).getWorker(true), game.getCurrentPlayer().getCurrentWorker());
    }

    @Test
    public void performMove_InitialWorkersSetting_SecondIncorrectSetting() {

        game.setCurrentPlayer(players.get(0));
        game.getCurrentPlayer().setVirtualViewId(0);

        PlayerChoice workerChoice = new WorkerChoice(true);
        PlayerAction workerSelection = new PlayerAction(0, workerChoice);

        game.performWorkerChoice(workerSelection); //select first worker

        PlayerChoice moveChoice = new MoveChoice(0, 0);
        PlayerAction moveAction = new PlayerAction(0, moveChoice);

        game.performMove(moveAction); //set first worker

        moveChoice = new MoveChoice(0, 0);
        moveAction = new PlayerAction(0, moveChoice);

        game.performMove(moveAction); //set second worker

        assertEquals(board[0][0], players.get(0).getWorker(true).getPos());
        assertNull(players.get(0).getWorker(false).getPos());
        assertTrue(players.get(0).isPlaying());
    }

    @Test
    public void performMove_ActualMove_InvalidMove() {

        game.setCurrentPlayer(players.get(1));

        game.getCurrentPlayer().getWorker(true).setPos(board[2][2]);
        game.getCurrentPlayer().getWorker(false).setPos(board[2][3]);

        board[2][2].setWorker(game.getCurrentPlayer().getWorker(true));
        board[2][3].setWorker(game.getCurrentPlayer().getWorker(false));

        PlayerChoice workerChoice = new WorkerChoice(true);
        PlayerAction workerSelection = new PlayerAction(1, workerChoice);

        game.performWorkerChoice(workerSelection); //select first worker

        PlayerChoice moveChoice = new MoveChoice(2, 3);
        PlayerAction moveAction = new PlayerAction(1, moveChoice);

        game.performMove(moveAction);

        assertEquals(board[2][2], players.get(1).getWorker(true).getPos());
        assertTrue(players.get(1).isPlaying());
        assertEquals(players.get(1).getWorker(true), game.getCurrentPlayer().getCurrentWorker());
    }

    @Test
    public void performBuild_OccupiedBox_InvalidBuilding() {

        game.setCurrentPlayer(players.get(2));

        game.getCurrentPlayer().getWorker(true).setPos(board[2][2]);
        game.getCurrentPlayer().getWorker(false).setPos(board[3][2]);

        PlayerChoice workerChoice = new WorkerChoice(true);
        PlayerAction workerSelection = new PlayerAction(2, workerChoice);

        game.performWorkerChoice(workerSelection);

        PlayerChoice buildChoice = new BuildChoice(3, 2);
        PlayerAction buildAction = new PlayerAction(2, buildChoice);

        game.performBuild(buildAction);

        assertEquals(0, board[3][2].getLevel());
        assertTrue(players.get(2).isPlaying());
        assertEquals(players.get(2).getWorker(true), game.getCurrentPlayer().getCurrentWorker());
    }
    
}