package it.polimi.ingsw.PSP54.test_model;

import it.polimi.ingsw.PSP54.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtlasTest {
    Player p = new Player("Giovanni",0,null,null);
    Box box_source,box_dest;

    @Before
    public void setUp() throws Exception {
        p.setGodID(Player.ATLAS);
        p.setPower();
        p.setMoveToken(1);
        p.setBuildToken(1);
        box_source = new Box(2,2);
        box_source.setWorker(p.getWorkerList().get(0));
        box_dest = new Box(2,1);
    }

    @After
    public void tearDown() throws Exception {
        p = null;
        box_dest = null;
        box_source = null;
    }

    @Test
    public void validBuilding_normalBuilding_setBuilding() throws Exception{
        p.build(0,box_dest,false);
        assertEquals(box_dest.getLevel(),1);
    }

    @Test
    public void validBuilding_domeBuilding_setBuilding() throws Exception{
        p.build(0,box_dest,true);
        assertTrue(box_dest.isDome());
    }

    @Test
    public void validBuilding_boxIsOccupied_throwsInvalidBuildingException() throws Exception{
        box_dest.setWorker(new Worker(null,null,0));
        try{
            p.build(0,box_dest,true);
        } catch (InvalidBuildingException e){
            assertTrue(true);
        }
    }

    @Test
    public void validBuilding_boxIsCompleted_throwsInvalidBuildingException() throws Exception{
        box_dest.setDome(true);
        try{
            p.build(0,box_dest,true);
        } catch (InvalidBuildingException e){
            assertTrue(true);
        }
    }
}