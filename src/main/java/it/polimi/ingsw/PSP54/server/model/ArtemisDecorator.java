package it.polimi.ingsw.PSP54.server.model;

import java.util.ArrayList;

/**
 * Your worker MAY move an additional time but NOT BACK to its initial space
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

        Worker currentWorker = choseWorker(male);
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

    /**
     *If valid performs build and modify action tokens
     * @param worker selected worker which the player wants to move
     * @param dest selected box where to build
     */
    @Override
    public void build(Worker worker, Box dest) throws InvalidBuildingException {

        super.build(worker, dest);
        if (worker.getMoveToken()!=0)
            worker.setMoveToken(0);
    }
}
