package it.polimi.ingsw.PSP54.model;

import java.util.ArrayList;

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
    public ArrayList<Box> setWorkerBoxesToMove(Worker worker) {

        //get standard vector
        ArrayList<Box> def = super.setWorkerBoxesToMove(worker);

        //affects the vector
        for (int i = 0; i < def.size(); i++) { //TODO: use Iterator
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
