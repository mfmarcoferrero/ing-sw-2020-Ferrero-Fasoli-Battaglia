package it.polimi.ingsw.PSP54.server.model;

import java.util.ArrayList;

/**
 * Class representing the Minotaur God Card.
 * From Santorini's rules: "Your Move: Your worker may move into an opponent worker's space,
 * if their worker can be forced one space straight backwards to an occupied space at any level."
 */
public class MinotaurDecorator extends GodDecorator {

    public MinotaurDecorator(Player player) {
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
     * Checks if minotaur's power can be applied and return the box where the opponent's worker will be forced to.
     * @param redSheetBox the box where the opponent's worker is placed.
     * @param bullBox the box where minotaur's worker is placed.
     * @return the box where the opponent's worker is going to be forced. The returned box can be null if that box don't exist or is unavailable.
     */
    private Box diametricallyOppositeBox(Box redSheetBox, Box bullBox) {

        Box[][] board = getGame().getBoard();
        super.setWorkerBoxesToMove(redSheetBox.getWorker());
        Box forceTo = null;

        if (bullBox.getX() == redSheetBox.getX()) { // north || south
            if (bullBox.getY() < redSheetBox.getY() && redSheetBox.getY() < 4)
                forceTo = board[redSheetBox.getX()][redSheetBox.getY() + 1];
            else if (redSheetBox.getY() > 0)
                forceTo = board[redSheetBox.getX()][redSheetBox.getY() - 1];

        } else if (bullBox.getY() == redSheetBox.getY()) { // east || west
            if (bullBox.getX() < redSheetBox.getX() && redSheetBox.getX() < 4)
                forceTo = board[redSheetBox.getX() + 1][redSheetBox.getY()];
            else if (redSheetBox.getX() > 0)
                forceTo = board[redSheetBox.getX() - 1][redSheetBox.getY()];

        } else if (bullBox.getX() > redSheetBox.getX()) { // north/south-east
            if (bullBox.getY() > redSheetBox.getY() && redSheetBox.getX() > 0 && redSheetBox.getY() > 0)
                forceTo = board[redSheetBox.getX() - 1][redSheetBox.getY() - 1];
            else if (redSheetBox.getX() > 0 && redSheetBox.getY() < 4)
                forceTo = board[redSheetBox.getX() - 1][redSheetBox.getY() + 1];

        } else if (bullBox.getY() > redSheetBox.getY()) {
            if (redSheetBox.getX() < 4 && redSheetBox.getY() > 0)
                forceTo = board[redSheetBox.getX() + 1][redSheetBox.getY() - 1];
            else if (redSheetBox.getX() < 4 && redSheetBox.getY() < 4)
                forceTo = board[redSheetBox.getX() + 1][redSheetBox.getY() + 1];
        }

        if (forceTo != null)
            if (forceTo.isOccupied() || forceTo.isDome())
                forceTo = null;

        return forceTo;
    }

    /**
     * Method used to set available boxes for the worker to move.
     *
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
                if ((deltaX <= 1 && deltaY <= 1) && deltaH <= 1 && !board[i][j].isDome()) {

                    if (board[i][j].isOccupied()) {
                        if (!isMyWorkerPos(board[i][j])) {
                            Box canBeForced = diametricallyOppositeBox(board[i][j], worker.getPos());
                            if (canBeForced != null)
                                valid.add(board[i][j]);
                        }
                    }else
                        valid.add(board[i][j]);
                }
            }
        }
        worker.setBoxesToMove(valid);
        return valid;
    }

    /**
     * Method used to perform a move action.
     *
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
                //perform force
                Box forceTo = diametricallyOppositeBox(dest, worker.getPos());
                Worker victim = dest.getWorker();
                victim.setPos(forceTo);
                forceTo.setWorker(victim);
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
