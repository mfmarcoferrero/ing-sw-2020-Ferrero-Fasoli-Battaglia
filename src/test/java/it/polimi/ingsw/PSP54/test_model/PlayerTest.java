package it.polimi.ingsw.PSP54.test_model;

import org.junit.After;
import org.junit.Before;
import it.polimi.ingsw.PSP54.model.*;
import org.junit.Test;

import java.nio.channels.ScatteringByteChannel;

import static org.junit.Assert.*;

public class PlayerTest {
    Player p = new Player(null, 0, null, null);
    Box box_dest = new Box(0, 0);

    @Before
    public void setUp() throws Exception {
        p.setBuildToken(1);
        p.setMoveToken(1);
        p.setGodID(Player.NORMAL_POWER);
        p.setPower();
    }

    @After
    public void tearDown() {
        p = null;
        box_dest = null;
    }

    @Test
    public void move_correctDest_workerSet() throws Exception {
        p.getWorkerList().get(0).pos = new Box(2,2);
        box_dest.setX(3);
        box_dest.setY(3);
        p.move(0, box_dest);
        assertEquals(p.getWorkerList().get(0).pos, box_dest);
    }

    @Test
    public void move_incorrectDest_throwsInvalidMoveException() throws Exception {
        p.getWorkerList().get(0).pos = new Box(2,2);
        box_dest.setX(2);
        box_dest.setY(4);
        try {
            p.move(0, box_dest);
        } catch (InvalidMoveException e) {
            assertEquals(p.getWorkerList().get(0).pos.getX(),2);
            assertEquals(p.getWorkerList().get(0).pos.getY(),2);
        }
    }

    @Test
    public void build_correctDest_buildingSet() throws Exception {
        p.getWorkerList().get(1).pos = new Box(2,2);
        box_dest.setLevel(0);
        box_dest.setX(2);
        box_dest.setY(3);
        p.build(1,box_dest,false);
        assertEquals(box_dest.getLevel(),1);
    }

    @Test
    public void build_inCorrectDest_throwsInvalidBuildException() throws Exception {
        p.getWorkerList().get(1).pos = new Box(2,2);
        box_dest.setLevel(0);
        box_dest.setX(2);
        box_dest.setY(4);
        try {
            p.build(1, box_dest, false);
        } catch (InvalidBuildingException e) {
            assertEquals(box_dest.getLevel(),0);
        }
    }

    @Test
    public void build_correctDest_domeSet() throws Exception {
        p.getWorkerList().get(1).pos = new Box(2,2);
        box_dest.setLevel(0);
        box_dest.setX(2);
        box_dest.setY(3);
        p.build(1,box_dest,true);
        assertEquals(box_dest.getLevel(),0);
        assertEquals(box_dest.isDome(),true);
    }

    @Test
    public void endTurn_lastPlayerInArray_setTurnFirstPlayer() throws Exception {

    }

}