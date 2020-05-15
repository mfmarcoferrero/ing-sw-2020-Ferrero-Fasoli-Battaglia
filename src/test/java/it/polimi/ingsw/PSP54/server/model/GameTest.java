package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.utils.choices.MoveChoice;
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
        players.get(0).setGame(game);
        players.get(1).setGame(game);
        players.get(2).setGame(game);
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
    public void extractCards_2PlayersExtraction_AllDifferent() {

        game.extractCards();

        HashMap<Integer, String> cards = game.getExtractedCards();

        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < players.size(); j++){
                if (i!=j){
                    assertNotEquals(cards.get(i), cards.get(j));
                }
            }
        }

    }
}