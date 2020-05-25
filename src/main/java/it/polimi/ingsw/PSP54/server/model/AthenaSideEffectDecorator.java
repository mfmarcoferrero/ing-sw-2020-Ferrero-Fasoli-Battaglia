package it.polimi.ingsw.PSP54.server.model;

import java.util.ArrayList;

/**
 * Class representing the effect of Athena God Card on other players.
 */
public class AthenaSideEffectDecorator extends GodDecorator {

    public AthenaSideEffectDecorator(Player player) {
        super(player);
    }


    /**
     * Method used to set available boxes for the worker to move.
     * Calls the super method and removes all boxes one level higher than current one.
     * @param worker current worker in use.
     * @return the vector containing available boxes.
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
