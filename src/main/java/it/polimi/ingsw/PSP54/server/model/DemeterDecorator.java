package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;

import java.util.ArrayList;

/**
 * Your worker may build an additional time but not on the same space
 */
public class DemeterDecorator extends GodDecorator{

    private Box lastBuilding;

    public DemeterDecorator(Player player) {
        super(player);
    }

    /**
     *
     * @param worker
     * @return
     */
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

    /**
     *
     * @param worker
     * @param dest
     * @throws InvalidMoveException
     */
    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {
        super.move(worker, dest);
        worker.setBuildToken(2);
    }

    /**
     *
     * @param worker
     * @param dest
     * @throws InvalidBuildingException
     */
    @Override
    public void build(Worker worker, Box dest) throws InvalidBuildingException {

        if (worker.getBuildToken() == 2) {
            super.build(worker, dest);
            setLastBuilding(dest);
            worker.setBuildToken(-1);
            GameMessage buildAgain = new StringMessage(getVirtualViewID(), StringMessage.buildAgain);
            getGame().notify(buildAgain);
        }else
            super.build(worker, dest);
    }

    /**
     *
     * @param choice
     */
    @Override
    public void chose(boolean choice) {
        if (choice){
            getCurrentWorker().setBuildToken(1);
        }else
            getCurrentWorker().setBuildToken(0);
    }

    //getters & setters

    public Box getLastBuilding() {
        return lastBuilding;
    }

    public void setLastBuilding(Box lastBuilding) {
        this.lastBuilding = lastBuilding;
    }
}
