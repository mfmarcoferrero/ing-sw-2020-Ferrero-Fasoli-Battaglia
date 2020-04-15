package it.polimi.ingsw.PSP54.test_model;

import it.polimi.ingsw.PSP54.model.Game;
import it.polimi.ingsw.PSP54.model.IllegalNumberOfPlayersException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    Game gameDemo = new Game();

    @Before
    public void setUp()  {
        gameDemo.newPlayer("Marco",21,null);
        gameDemo.newPlayer("Alessandro",16,null);
        gameDemo.newPlayer("Matteo",27,null);
        gameDemo.startGame();
    }

    @After
    public void tearDown(){
        gameDemo = null;
    }

    @Test
    public void godAssignment_threePlayers_differentGods() {
        gameDemo.godAssignment();
        assertTrue(gameDemo.getPlayers().get(0).getGodID() != gameDemo.getPlayers().get(1).getGodID());
        assertTrue(gameDemo.getPlayers().get(0).getGodID() != gameDemo.getPlayers().get(2).getGodID());
        assertTrue(gameDemo.getPlayers().get(1).getGodID() != gameDemo.getPlayers().get(2).getGodID());
    }

    @Test
    public void bubbleSortPlayers_threePlayers_sortedVector() {
        gameDemo.bubbleSortPlayers();
        assertTrue(gameDemo.getPlayers().get(0).getPlayer_index() == 0);
        assertTrue(gameDemo.getPlayers().get(1).getPlayer_index() == 1);
        assertTrue(gameDemo.getPlayers().get(2).getPlayer_index() == 2);
        assertTrue(gameDemo.getPlayers().get(0).getPlayerName() == "Alessandro");
        assertTrue(gameDemo.getPlayers().get(1).getPlayerName() == "Marco");
        assertTrue(gameDemo.getPlayers().get(2).getPlayerName() == "Matteo");
    }


}