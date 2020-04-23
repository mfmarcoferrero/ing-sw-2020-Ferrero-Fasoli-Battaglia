package it.polimi.ingsw.PSP54.model;

import java.util.ArrayList;

/**
 * Your worker may move into an opponent worker's space by forcing their worker to the space just vacated
 */
public class ApolloDecorator extends GodDecorator {

    public ApolloDecorator(Player player) {
        super(player);
    }

    /**
     * Sets Apollo's available boxes for the worker to move
     * @param worker current worker in use
     * @return the vector containing available boxes
     */
    @Override
    public ArrayList<Box> setWorkerBoxesToMove (Worker worker){

        ArrayList<Box> boxes = new ArrayList<>();
        int deltaX, deltaY, deltaH;
        Box[][] board = getGame().getBoard();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                deltaX = Math.abs(worker.getPos().getX() - board[i][j].getX());
                deltaY = Math.abs(worker.getPos().getY() - board[i][j].getY());
                deltaH =  (board[i][j].getLevel() - worker.getPos().getLevel());
                if ((deltaX <= 1 && deltaY <= 1) && board[i][j] != worker.getPos() && deltaH <= 1 && !board[i][j].isDome())
                    boxes.add(board[i][j]);
            }
        }
        worker.setBoxesToMove(boxes);
        return boxes;
    }

    /**
     * Performs Apollo special move: if destination box is occupied  swaps
     * @param worker selected worker which the player wants to move
     * @param dest selected destination box
     * @throws InvalidMoveException if the move can't be done
     */
    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {
        worker.setBoxesToMove(setWorkerBoxesToMove(worker));
        //sets validity indicators
        ArrayList<Box> valid = worker.getBoxesToMove();
        int currentMoveToken = worker.getMoveToken();

        if (currentMoveToken > 0 && valid.contains(dest)){
            if (dest.isOccupied()){
                //perform swap
                Box current = worker.getPos();
                Worker opponent = dest.getWorker();
                opponent.setPos(current);
                current.setWorker(dest.getWorker());

            }else {
                //free current box
                worker.getPos().setWorker(null);
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
