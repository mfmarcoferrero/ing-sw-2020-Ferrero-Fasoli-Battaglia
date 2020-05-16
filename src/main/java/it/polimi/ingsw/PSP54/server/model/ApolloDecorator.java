package it.polimi.ingsw.PSP54.server.model;

import java.util.ArrayList;

/**
 * Your worker may move into an opponent worker's space by forcing their worker to the space just vacated
 */
public class ApolloDecorator extends GodDecorator {

    public ApolloDecorator(Player player) {
        super(player);
    }

    @Override
    public ArrayList<Box> setWorkerBoxesToMove (Worker worker){

        ArrayList<Box> valid = new ArrayList<>();
        int deltaX, deltaY, deltaH;
        Box[][] board = getGame().getBoard();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                deltaX = Math.abs(worker.getPos().getX() - board[i][j].getX());
                deltaY = Math.abs(worker.getPos().getY() - board[i][j].getY());
                deltaH =  (board[i][j].getLevel() - worker.getPos().getLevel());
                if ((deltaX <= 1 && deltaY <= 1) && board[i][j] != worker.getPos() && deltaH <= 1 && !board[i][j].isDome())
                    valid.add(board[i][j]);
            }
        }
        worker.setBoxesToMove(valid);
        return valid;
    }

    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {
        //worker.setBoxesToMove(setWorkerBoxesToMove(worker));
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
            worker.setBuildToken(1);
            //set buildable boxes
            worker.setBoxesToBuild(setWorkerBoxesToBuild(worker));
        } else throw new InvalidMoveException();
    }
}
