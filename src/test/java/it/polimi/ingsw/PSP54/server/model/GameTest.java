package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.utils.PlayerAction;
import it.polimi.ingsw.PSP54.utils.choices.CardChoice;
import it.polimi.ingsw.PSP54.utils.choices.MoveChoice;
import it.polimi.ingsw.PSP54.utils.choices.PlayerChoice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
}