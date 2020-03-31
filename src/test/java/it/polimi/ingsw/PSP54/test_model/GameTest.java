package it.polimi.ingsw.PSP54.test_model;

import it.polimi.ingsw.PSP54.model.Game;
import it.polimi.ingsw.PSP54.model.IllegalNumberOfPlayersException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    Game gameTest = null;
    IllegalNumberOfPlayersException e;

    @Before
    public void setUp() throws IllegalNumberOfPlayersException {
        gameTest = new Game(2,"marco",22, "verde","matteo",20,"rosso",null,0,null);
    }

    @After
    public void tearDown() {
        gameTest = null;
    }

    @Test
    public void displayWinner() {

    }
    /**
     * Test per verificare che la funzione restituisca l'indice del giocatore più giovane
     */
    @Test
    public void youngestPlayerTest_correctInput_correctOutput() {
        assertEquals(gameTest.youngestPlayer(),1,0);
    }
}