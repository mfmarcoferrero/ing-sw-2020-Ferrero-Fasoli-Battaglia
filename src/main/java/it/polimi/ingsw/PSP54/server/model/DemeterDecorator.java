package it.polimi.ingsw.PSP54.server.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Your worker may build an additional time but not on the same space
 */
public class DemeterDecorator extends GodDecorator{

    private Box lastBuilding;

    public DemeterDecorator(Player player) {
        super(player);
    }

    @Override
    public ArrayList<Box> setWorkerBoxesToBuild(Worker worker) {

        if (worker.getBuildToken() == 2)
            return super.setWorkerBoxesToBuild(worker);
        else {
            ArrayList<Box> valid = super.setWorkerBoxesToBuild(worker);
            valid.removeIf(check -> check == getLastBuilding());

            return valid;
        }
    }

    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {
        super.move(worker, dest);
        worker.setBuildToken(2);
    }

    @Override
    public void build(Worker worker, Box dest) throws InvalidBuildingException {

        super.build(worker, dest);
        if (worker.getBuildToken() == 1)
            setLastBuilding(dest);
    }

    //getters & setters

    public Box getLastBuilding() {
        return lastBuilding;
    }

    public void setLastBuilding(Box lastBuilding) {
        this.lastBuilding = lastBuilding;
    }
}
