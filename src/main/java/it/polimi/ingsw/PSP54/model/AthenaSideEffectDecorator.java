package it.polimi.ingsw.PSP54.model;

import java.util.Vector;

//TODO: Test
public class AthenaSideEffectDecorator extends GodDecorator {

    public AthenaSideEffectDecorator(Player player) {
        super(player);
    }

    /*PSEUDOCODE:

    setWorkerBoxesToMove()
        default = super.setWorkerBoxesToMove
        addSideEffect(default)
     */

    //TODO: JavaDoc
    @Override
    public Vector setWorkerBoxesToMove(Worker worker) {

        //get standard vector
        Vector<Box> def = new Vector<>();
                def = super.setWorkerBoxesToMove(worker);

        for (int i = 0; i < def.capacity(); i++) { //affects the vector
            if (def.get(i).getLevel() == worker.getPos().getLevel()+1)
                def.remove(i);
        }

        return def; //TODO: how Vector.remove(index) works
    }

    //only for debug purpose

    @Override
    public void printPower() {
        System.out.println("Athena Side Effect");
    }
}
