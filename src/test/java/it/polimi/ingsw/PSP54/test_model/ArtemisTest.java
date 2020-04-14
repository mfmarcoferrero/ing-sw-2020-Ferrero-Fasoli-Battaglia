package it.polimi.ingsw.PSP54.test_model;

import it.polimi.ingsw.PSP54.model.Box;
import it.polimi.ingsw.PSP54.model.InvalidMoveException;
import it.polimi.ingsw.PSP54.model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArtemisTest {
    Player p = new Player("Giovanni",0,null,null);
    Box box_source,box_dest;

    @Before
    public void setUp() {
        p.setGodID(Player.ARTEMIS);
        p.setPower();
        p.setMoveToken(1);
        p.setBuildToken(1);
        box_source = new Box(2,2);
        box_source.setWorker(p.getWorkerList().get(0));
        box_dest = new Box(2,1);
    }

    @After
    public void tearDown() {
        p = null;
        box_dest = null;
    }

    @Test
    public void specialValidMove_doubleMove_correctMove() throws InvalidMoveException {
        p.move(0,box_dest);
        Box box_dest_2 = new Box(2,0);
        p.move(0,box_dest_2);
        assertEquals(p.getWorkerList().get(0).pos, box_dest_2);
    }

    @Test
    public void specialValidMove_inCorrectInput_inCorrectOutput() {
        box_source.setX(0);
        box_source.setY(0);
        box_dest.setX(0);
        box_dest.setY(4);
        assertFalse(p.power.validMove(box_source,box_dest));
    }
}