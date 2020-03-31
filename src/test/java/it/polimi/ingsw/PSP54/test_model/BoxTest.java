package it.polimi.ingsw.PSP54.test_model;

import it.polimi.ingsw.PSP54.model.Box;
import it.polimi.ingsw.PSP54.model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoxTest {
    Box boxTest = null;

    @Before
    public void setUp() {
        boxTest = new Box();
    }

    @After
    public void tearDown() {
        boxTest = null;
    }

    @Test
    public void isOccupied_correctInput_true() {
        Worker w1;
        w1 = new Worker(null,"blue");
        boxTest.setBox(1,false, w1);
        assertTrue(boxTest.isOccupied());
    }

}