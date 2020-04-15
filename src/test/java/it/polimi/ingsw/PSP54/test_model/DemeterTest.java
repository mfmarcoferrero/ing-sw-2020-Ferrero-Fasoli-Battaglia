package it.polimi.ingsw.PSP54.test_model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import it.polimi.ingsw.PSP54.model.*;

import static org.junit.Assert.*;

public class DemeterTest {
    Player p = new Player("Giovanni",0,null,null);
    Box box_source,box_dest;

    @Before
    public void setUp() throws Exception {
        p.setGodID(Player.DEMETER);
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
        box_source = null;
        box_dest = null;
    }

    @Test
    public void validBuilding_doubleBuildingSameBox_buildingSet() throws Exception {
        p.build(0,box_dest,false);
        p.build(0,box_dest,false);
        assertEquals(box_dest.getLevel(),2);
    }

    @Test
    public void validBuilding_doubleBuildingDifferentBox_buildingSet() throws Exception {
        Box box_dest_2 = new Box(3,3);
        p.build(0,box_dest,false);
        p.build(0,box_dest_2,false);
        assertEquals(box_dest.getLevel(),1);
        assertEquals(box_dest_2.getLevel(),1);
    }

    @Test
    public void validBuilding_differentWorkerChoose_throwsInvalidBuildingException() throws Exception {
        Box box_source_worker1 = new Box(0,0);
        box_source_worker1.setWorker(p.getWorkerList().get(1));
        try {
            p.build(0,box_dest,false);
            p.build(1,new Box(1,1),false);
        }
        catch (InvalidBuildingException e ){
            assertTrue(true); ;
        }
    }

    @Test
    public void validBuilding_boxCompleted_throwsInvalidBuildingException() throws Exception {
        try {
            box_dest.setLevel(3);
            p.build(0,box_dest,false);
            p.build(0,box_dest,false);
        }
        catch (InvalidBuildingException e ){
            assertTrue(true); ;
        }
    }
}