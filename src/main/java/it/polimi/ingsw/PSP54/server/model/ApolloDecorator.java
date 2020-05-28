package it.polimi.ingsw.PSP54.server.model;

import java.util.ArrayList;

/**
 * Class representing the Apollo God Card.
 * From Santorini's rules : "Your Move: Your Worker may move on an occupied cell, changing position with that worker."
 */
public class ApolloDecorator extends GodDecorator {

    public ApolloDecorator(Player player) {
        super(player);
    }

    /**
     * Checks if a box is occupied by a teammate worker.
     * @param box the box to check.
     * @return true if the box is occupied by a teammate worker, false otherwise.
     */
    private boolean isMyWorkerPos(Box box) {
        return (getWorker(true).getPos() == box
                || getWorker(false).getPos() == box);
    }

    /**
     * Method used to set available boxes for the worker to move.
     * It returns all adjacent boxes up to a level higher, including the occupied ones.
     * @param worker current worker in use.
     * @return the vector containing available boxes.
     */
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
                if ((deltaX <= 1 && deltaY <= 1) && !isMyWorkerPos(board[i][j]) && deltaH <= 1 && !board[i][j].isDome())
                    valid.add(board[i][j]);
            }
        }
        if (valid.isEmpty()){
            setLoser(true);
        }
        worker.setBoxesToMove(valid);
        return valid;
    }

    /**
     * Method used to perform a move action.
     * Performs a normal move action, but if the box is occupied it performs an exchange between the two workers.
     * @param worker selected worker which the player wants to move.
     * @param dest selected destination box.
     * @throws InvalidMoveException if the move can't be done.
     */
    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {
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
                //perform move
                worker.setPos(dest);
                dest.setWorker(worker);
                //update tokens
                worker.setMoveToken(0);
                worker.setBuildToken(1);
                //notify
                getGame().notifyBoard();
                super.checkWinner(worker);

            }else
                super.move(worker, dest);

        }else throw new InvalidMoveException();
    }
}
