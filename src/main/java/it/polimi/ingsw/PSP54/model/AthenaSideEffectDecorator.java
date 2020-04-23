package it.polimi.ingsw.PSP54.model;

import java.util.ArrayList;

public class AthenaSideEffectDecorator extends GodDecorator {

    public AthenaSideEffectDecorator(Player player) {
        super(player);
    }

    //TODO: JavaDoc
    @Override
    public ArrayList<Box> setWorkerBoxesToMove(Worker worker) {

        //get standard vector
        ArrayList<Box> valid = super.setWorkerBoxesToMove(worker);

        //affects the vector
        for (int i = 0; i < valid.size(); i++) { //TODO: use Iterator(?)
            if (valid.get(i).getLevel() == worker.getPos().getLevel()+1)
                valid.remove(i);
        }

        return valid;
    }

    //only for debug purpose

    @Override
    public void printPower() {
        System.out.println("Athena Side Effect");
    }
}
