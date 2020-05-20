package it.polimi.ingsw.PSP54.server.model;

import java.util.ArrayList;

public class AthenaSideEffectDecorator extends GodDecorator {

    public AthenaSideEffectDecorator(Player player) {
        super(player);
    }


    /**
     * Sets available boxes for the worker to move and stores them in worker's attribute
     * @param worker current worker in use
     * @return the vector containing available boxes
     */
    @Override
    public ArrayList<Box> setWorkerBoxesToMove(Worker worker) {

        //get standard vector
        ArrayList<Box> valid = super.setWorkerBoxesToMove(worker);

        //affects the vector
        valid.removeIf(check -> check.getLevel() == worker.getPos().getLevel() + 1);
        return valid;
    }
}
