package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;

import java.util.ArrayList;

/**
 * Your worker may move an additional time but not back to its initial space.
 */
public class ArtemisDecorator extends GodDecorator{

    private Box initialPos;

    public ArtemisDecorator(Player player) {
        super(player);
    }

    /**
     *Initialize current player's turn by setting worker's action tokens
     * @param male represent the sex of the worker which the player is going to use
     * @return the chosen worker with updated tokens
     */
    @Override
    public Worker turnInit(Boolean male){
        Worker currentWorker = getWorker(male);
        currentWorker.setMoveToken(2);
        currentWorker.setBuildToken(0);
        return currentWorker;
    }

    /**
     * Set available boxes for the worker to move and stores them in worker's attribute
     * @param worker current worker in use
     * @return the vector containing available boxes
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

    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {
        super.move(worker, dest);
        if (worker.getMoveToken() == 1){
            worker.setMoveToken(-1);
            GameMessage moveAgain = new StringMessage(getVirtualViewID(), StringMessage.moveAgain);
            getGame().notify(moveAgain);
        }
    }

    @Override
    public void chose(boolean choice){
        if (choice){ //move again
            getCurrentWorker().setMoveToken(1);
        }else {
            getCurrentWorker().setMoveToken(0);
            getCurrentWorker().setBuildToken(1);
        }
    }
}
