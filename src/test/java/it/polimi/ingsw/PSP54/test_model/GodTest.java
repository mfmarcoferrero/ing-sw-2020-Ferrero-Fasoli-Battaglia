package it.polimi.ingsw.PSP54.test_model;

import it.polimi.ingsw.PSP54.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GodTest {
    Player p = new Player(null,0,null,null);
    Box box_source,box_dest;

    @Before
    public void setUp() throws Exception {
        box_source = new Box(2,2);
        box_dest = new Box(2,1);
        p.setGodID(Player.NORMAL_POWER);
        p.setPower();
        p.setMoveToken(1);
        p.setBuildToken(1);
    }

    @After
    public void tearDown() throws Exception {
        box_source = null;
        box_dest = null;
        p = null;
    }

    @Test
    public void adjacentBoxes_correctInput_outputIsTrue() {

        assertTrue(p.power.adjacentBoxes(box_source,box_dest));
    }

    @Test
    public void adjacentBoxes_correctInput_outputIsFalse() throws Exception {
        box_dest.setX(2);
        box_dest.setY(4);
        assertFalse(p.power.adjacentBoxes(box_source,box_dest));
    }

    @Test
    public void normalValidMove_correctInput_outputIsTrue() throws Exception {
        box_dest.setX(3);
        box_dest.setY(2);
        box_dest.setLevel(1);
        assertTrue(p.power.normalValidMove(box_source,box_dest));
    }

    @Test
    public void normalValidMove_correctInput_outputIsFalse() throws Exception {
        box_dest.setX(3);
        box_dest.setY(2);
        box_dest.setLevel(3);
        assertFalse(p.power.normalValidMove(box_source,box_dest));
    }

    @Test
    public void normalValidMove_correctInput_destIsOccupied() throws Exception {
        box_dest.setX(3);
        box_dest.setY(2);
        box_dest.setWorker(new Worker(null,null,0));
        box_dest.setLevel(1);
        assertFalse(p.power.normalValidMove(box_source,box_dest));
    }
}