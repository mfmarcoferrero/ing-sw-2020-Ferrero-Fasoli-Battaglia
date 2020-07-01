package it.polimi.ingsw.PSP54.server.model;

import junit.framework.TestCase;

public class BoxTest extends TestCase {
    Box box=new Box(0,0,0,false);


    public void testSetComplete() {
        box.setComplete(false);
        assertEquals(box.isComplete(),false);
    }
}