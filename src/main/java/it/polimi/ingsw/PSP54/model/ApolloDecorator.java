package it.polimi.ingsw.PSP54.model;

import java.util.Vector;

public class ApolloDecorator extends GodDecorator {

    public ApolloDecorator(Player player) {
        super(player);
    }

    /*your worker may move into an opponent worker's space by forcing their worker to the space just vacated

    PSEUDOCODE:

    set:
        if (isOccupied)
            valid.add()

    move:
        if(isOccupied)
            perform move
            perform swap


    */
    /**
     * Sets Apollo's available boxes for the worker to move
     * @param worker current worker in use
     * @return the vector containing available boxes
     */
    @Override
    public Vector<Box> setWorkerBoxesToMove (Worker worker){

        Vector<Box> boxes = new Vector<>(1, 1);
        int deltaX, deltaY, deltaH;
        Box[][] board = getGame().getBoard();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                deltaX = Math.abs(worker.getPos().getX() - board[i][j].getX());
                deltaY = Math.abs(worker.getPos().getY() - board[i][j].getY());
                deltaH =  (board[i][j].getLevel() - worker.getPos().getLevel());
                if ((deltaX == 1 || deltaY ==1) && deltaH == 1 && !board[i][j].isDome())
                    boxes.add(board[i][j]);
            }
        }
        worker.setBoxesToMove(boxes);
        return boxes;
    }

    //TODO:JavaDoc
    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {

        Vector<Box> valid = worker.getBoxesToMove();
        int currentMoveToken = worker.getMoveToken();
        Box currentPos = worker.getPos();

        if (currentMoveToken >= 0 && valid.contains(dest)){
            if (dest.isOccupied()){
                //perform swap
                Worker opponent = dest.getWorker();
                dest.getWorker().setPos(currentPos);
                currentPos.setWorker(opponent);
            }
            //perform move
            worker.setPos(dest);
            dest.setWorker(worker);
            //decrement token
            worker.setMoveToken(currentMoveToken-1);
            //set buildable boxes
            worker.setBoxesToBuild(setWorkerBoxesToBuild(worker));
        }else throw new InvalidMoveException();
    }

    //only for debug purpose

    @Override
    public void printPower() {
        System.out.println("Apollo");

    }
}
