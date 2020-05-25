package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;

import java.util.ArrayList;

/**
 * Class representing the Artemis God Card.
 * From Santorini's rules: "Your Move: Your Worker may move one additional time, but not back to its initial space."
 */
public class ArtemisDecorator extends GodDecorator{

    private Box initialPos;

    public ArtemisDecorator(Player player) {
        super(player);
    }

    /**
     * Method used to initialize current player's turn.
     * Sets current worker's moveToken to 2 and buildToken to 0.
     * @param male represent the sex of the worker which the player is going to use.
     * @return the chosen worker with updated tokens.
     */
    @Override
    public Worker turnInit(Boolean male){
        Worker currentWorker = getWorker(male);
        currentWorker.setMoveToken(2);
        currentWorker.setBuildToken(0);
        return currentWorker;
    }

    /**
     * Method used to set available boxes for the worker to move.
     * Calls the super method and if the current worker's moveToken is 1 removes the initial position from the valid boxes.
     * @param worker current worker in use.
     * @return the vector containing available boxes.
     */
    @Override
    public ArrayList<Box> setWorkerBoxesToMove (Worker worker) {

        ArrayList<Box> valid;

        if (worker.getMoveToken() == 2){
            initialPos = worker.getPos();
            valid = super.setWorkerBoxesToMove(worker);
        }else {
            valid = super.setWorkerBoxesToMove(worker);
            valid.remove(initialPos);
        }
        worker.setBoxesToMove(valid);
        return valid;
    }

    /**
     * Method used to perform a move action.
     * Calls the super method and eventually notifies a message to the player in accordance to the tokens.
     * @param worker selected worker which the player wants to move.
     * @param dest selected destination box.
     * @throws InvalidMoveException if the move can't be done.
     */
    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {
        super.move(worker, dest);
        if (worker.getMoveToken() == 1){
            worker.setMoveToken(-1);
            GameMessage moveAgain = new StringMessage(getVirtualViewID(), StringMessage.moveAgain);
            getGame().notify(moveAgain);
        }
    }

    /**
     * Method used to perform a binary choice.
     * If choice is true restores a moveToken, otherwise set them to zero and sets the buildToken to 1.
     * @param choice the player's choice.
     */
    @Override
    public void chose(boolean choice){
        if (choice){ //move again
            getCurrentWorker().setMoveToken(1);
            getCurrentWorker().setBuildToken(0);
        }else {
            getCurrentWorker().setMoveToken(0);
            getCurrentWorker().setBuildToken(1);
        }
    }
}
