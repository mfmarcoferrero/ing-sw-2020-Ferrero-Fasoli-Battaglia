package it.polimi.ingsw.PSP54.test_model;

import it.polimi.ingsw.PSP54.model.Box;
import it.polimi.ingsw.PSP54.model.InvalidBoxException;
import it.polimi.ingsw.PSP54.model.Apollo;
import it.polimi.ingsw.PSP54.model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApolloTest {
    Apollo apollo = new Apollo();
    Worker w1, w2;
    Box box_source,box_dest;

    /**
     * Istanzia due worker in due caselle diverse
     * @throws InvalidBoxException se la casella istanziata non ha coordinate corrette
     */
    @Before
    public void setUp() throws InvalidBoxException {
        w1 = new Worker(null,null,0);
        w2 = new Worker(null,null,0);
        box_source = new Box(2,2);
        box_dest = new Box(2,1);
        box_source.setWorker(w1);
        box_dest.setWorker(w2);
    }

    @After
    public void tearDown(){
        w1 = null;
        w2 = null;
        box_source = null;
    }

    /**
     * Verifica che il metodo specialValidMove restituisca true se le caselle in cui si sposta sono adiacenti
     */
    @Test
    public void specialValidMove_correctInput_correctOutput() {
        assertTrue(apollo.specialValidMove(box_source,box_dest));
    }

    /**
     * Verifica che nella casella del worker che si sta muovendo venga inserito il worker presente nella casella di destinazione
     */
    @Test
    public void specialValidMove_correctInput_workerChangeSource() {
        apollo.specialValidMove(box_source,box_dest);
        assertEquals(box_source.getWorker(),w2);
    }

    /**
     * Verifica che la funzione restituisca false se la casella in cui si muove non è adiacente
     */
    @Test
    public void specialValidMove_incorrectInput_incorrectOutput() throws InvalidBoxException{
        box_source.setX(0);
        box_source.setY(0);
        box_dest.setX(2);
        box_dest.setY(0);
        assertFalse(apollo.specialValidMove(box_source,box_dest));
    }
}