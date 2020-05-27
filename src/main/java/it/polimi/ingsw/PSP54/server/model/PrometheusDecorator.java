package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;
import java.util.ArrayList;

/**
 * Class representing the Prometheus God Card.
 * From Santorini's rules: "Your Turn: If your worker does not move up, it may build both before and after moving."
 */
public class PrometheusDecorator extends GodDecorator{

    public PrometheusDecorator(Player player) {
        super(player);
    }

    private boolean usedPower;

    /**
     * Method used to initialize current player's turn.
     * Sets the selected worker's tokens to -1 and sends a message to teh player.
     * @param male represent the sex of the worker which the player is going to use.
     * @return the chosen worker with updated tokens.
     */
    @Override
    public Worker turnInit(Boolean male) {
        Worker currentWorker = getWorker(male);
        currentWorker.setMoveToken(-1);
        currentWorker.setBuildToken(-1);

        GameMessage buildFirst = new StringMessage(getVirtualViewID(), StringMessage.buildFirst);
        getGame().notify(buildFirst);
        return currentWorker;
    }

    /**
     * Method used to set available boxes for the worker to move.
     * Calls the super method and removes all the upper boxes from the ArrayList if the player has chosen to use the power.
     * @param worker current worker in use.
     * @return the vector containing available boxes.
     */
    @Override
    public ArrayList<Box> setWorkerBoxesToMove(Worker worker) {

        ArrayList<Box> valid = super.setWorkerBoxesToMove(worker);

        if (usedPower){
            valid.removeIf(check -> check.getLevel() == worker.getPos().getLevel() + 1);
            setUsedPower(false);
        }
        return valid;
    }

    /**
     * Method used to perform a build action.
     * Calls the super method and if the player has chosen to use the power sets worker's moveToken to 1.
     * @param worker selected worker which the player wants to move.
     * @param dest selected box where to build.
     * @throws InvalidBuildingException if the build can't be done.
     */
    @Override
    public void build(Worker worker, Box dest) throws InvalidBuildingException {
        super.build(worker, dest);

        if (usedPower)
            getCurrentWorker().setMoveToken(1);
    }

    /**
     * Method used to perform a binary choice.
     * If choice is true sets usedPower to true, buildToken to 1 and moveToken to 0, the opposite otherwise.
     * @param choice the player's choice.
     */
    @Override
    public void chose(boolean choice) {
        if (choice) {
            setUsedPower(true);
            getCurrentWorker().setBuildToken(1);
            getCurrentWorker().setMoveToken(0);
        }
        else {
            setUsedPower(false);
            getCurrentWorker().setMoveToken(1);
            getCurrentWorker().setBuildToken(0);
        }
    }

    public void setUsedPower(boolean usedPower) {
        this.usedPower = usedPower;
    }
}
