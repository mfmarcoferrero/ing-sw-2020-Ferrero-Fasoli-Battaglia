package it.polimi.ingsw.PSP54.test_model;

import it.polimi.ingsw.PSP54.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApolloTest {
    Player p = new Player(null,0,null,null);
    Worker w2;
    Box box_source,box_dest;


    @Before
    public void setUp() {
        p.setBuildToken(1);
        p.setMoveToken(1);
        p.setGodID(Player.APOLLO);
        p.setPower();
        w2 = new Worker(null,null,0);
        box_source = new Box(0,0);
        box_dest = new Box(0,0);
        box_source.setWorker(p.getWorkerList().get(0));
        box_dest.setWorker(w2);
    }

    @After
    public void tearDown(){
        w2 = null;
        box_source = null;
        p = null;
    }

    /**
     * Verifica che il metodo specialValidMove restituisca true se le caselle in cui si sposta sono adiacenti
     */
    @Test
    public void specialValidMove_correctInput_correctOutput() {
        box_source.setX(2);
        box_source.setY(2);
        box_dest.setX(2);
        box_dest.setY(3);
        assertTrue(p.power.validMove(box_source,box_dest));
    }

    /**
     * Verifica che nella casella del worker che si sta muovendo venga inserito il worker presente nella casella di destinazione
     */
    @Test
    public void specialValidMove_correctInput_workerChangeSource() throws Exception{
        box_source.setX(2);
        box_source.setY(2);
        box_dest.setX(2);
        box_dest.setY(3);
        p.move(0,box_dest);
        assertEquals(box_source.getWorker(),w2);
        assertEquals(box_dest.getWorker(),p.getWorkerList().get(0));
    }

    /**
     * Verifica che la funzione restituisca false se la casella in cui si muove non è adiacente
     */
    @Test
    public void specialValidMove_incorrectInput_incorrectOutput(){
        box_source.setX(0);
        box_source.setY(0);
        box_dest.setX(2);
        box_dest.setY(0);
        assertFalse(p.power.validMove(box_source,box_dest));
    }
}