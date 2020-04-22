package it.polimi.ingsw.PSP54.model;

import java.util.ArrayList;

/**
 * Your worker MAY move an additional time but NOT BACK to its initial space
 */
public class ArtemisDecorator extends GodDecorator{

    private Box initialPos;

    public ArtemisDecorator(Player player) {
        super(player);
    }

    @Override
    public Worker turnInit(Boolean male){

        Worker currentWorker = choseWorker(male);
        currentWorker.setMoveToken(2);
        currentWorker.setBuildToken(0);
        return currentWorker;
    }

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
    public void build(Worker worker, Box dest) throws InvalidBuildingException {

        super.build(worker, dest);
        if (worker.getMoveToken()!=0)
            worker.setMoveToken(0);
    }

    //only for debug purpose

    @Override
    public void printPower() {
        System.out.println("Artemis");

    }
}
