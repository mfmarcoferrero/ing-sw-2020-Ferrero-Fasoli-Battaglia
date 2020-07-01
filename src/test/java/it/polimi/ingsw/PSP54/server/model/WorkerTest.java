package it.polimi.ingsw.PSP54.server.model;

import junit.framework.TestCase;

public class WorkerTest extends TestCase {
    StandardPlayer p = new StandardPlayer("matteo",22,2);
    Box box = new Box(0,0,0,false);
    Worker worker = new Worker(true,p, box);

    public void testGetMale() {
        boolean check;
        check=worker.getMale();
        assertEquals(check,true);
    }

    public void testGetOwner() {
        assertEquals(worker.getOwner(),p);
    }
}