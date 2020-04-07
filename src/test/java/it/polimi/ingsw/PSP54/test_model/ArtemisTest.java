package it.polimi.ingsw.PSP54.test_model;

import it.polimi.ingsw.PSP54.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArtemisTest {
    Artemis artemis = new Artemis();
    Box box_source,box_dest;

    @Before
    public void setUp() throws InvalidBoxException {
        box_source = new Box(2,2);
        box_source.setWorker(null);
        box_dest = new Box(4,4);
        box_dest.setWorker(null);
    }

    @After
    public void tearDown() {
        box_source = null;
        box_dest = null;
    }

    @Test
    public void specialValidMove_correctInput_correctOutput() {
        assertTrue(artemis.specialValidMove(box_source,box_dest));
    }

    @Test
    public void specialValidMove_inCorrectInput_inCorrectOutput() throws InvalidBoxException {
        box_source.setX(0);
        box_source.setY(0);
        box_dest.setX(0);
        box_dest.setY(4);
        assertFalse(artemis.specialValidMove(box_source,box_dest));
    }

}