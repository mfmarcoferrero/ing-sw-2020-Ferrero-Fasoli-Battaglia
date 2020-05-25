package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;

import java.util.ArrayList;

/**
 * Class representing the Demeter God Card.
 * From Santorini's rules: "Your Build: Your Worker may build one additional time, but not on the same space."
 */
public class DemeterDecorator extends GodDecorator{

    private Box lastBuilding;

    public DemeterDecorator(Player player) {
        super(player);
    }

    /**
     * Method used to set available boxes for the worker to build.
     * Calls teh super method and eventually removes the previously built box.
     * @param worker current worker in use.
     * @return the vector containing buildable boxes.
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
     * Method used to perform a move action.
     * Calls the super method and sets the buildToken to 2.
     * @param worker selected worker which the player wants to move.
     * @param dest selected destination box.
     * @throws InvalidMoveException if the move can't be done.
     */
    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {
        super.move(worker, dest);
        worker.setBuildToken(2);
    }

    /**
     * Method used to perform a build action.
     * Calls the super method and eventually notifies a message to the player in accordance to the tokens.
     * @param worker selected worker which the player wants to move.
     * @param dest selected box where to build.
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
