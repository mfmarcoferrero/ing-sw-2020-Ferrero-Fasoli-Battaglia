package it.polimi.ingsw.PSP54.server.model;

import java.util.ArrayList;

public class MinotaurDecorator extends GodDecorator {


    public MinotaurDecorator(Player player) {
        super(player);
    }


    private boolean canBeForced(Box box) {

        Box minotaurBox = getCurrentWorker().getPos();

        if (box.isOccupied()) {
            if (box.getWorker() != getWorker(true) && box.getWorker() != getWorker(false)) {

                if (minotaurBox.getX() == box.getX()) {
                    if (minotaurBox.getY() < box.getY())
                        return box.getY() < 4;
                    else
                        return box.getY() > 0;
                } else if (minotaurBox.getY() == box.getY()) {
                    if (minotaurBox.getX() < box.getX())
                        return box.getX() < 4;
                    else
                        return box.getX() > 0;
                } else if (minotaurBox.getX() > box.getX()) {
                    if (minotaurBox.getY() > box.getY())
                        return (box.getX() > 0 && box.getY() > 0);
                    else
                        return (box.getX() > 0 && box.getY() < 4);
                } else {
                    if (minotaurBox.getY() > box.getY())
                        return (box.getX() < 4 && box.getY() > 0);
                    else
                        return (box.getX() < 4 && box.getY() < 4);
                }
            } else
                return true;
        } else
            return true;
    }

    private Box diametricallyOppositeBox(Box center, Box radius) {

        Box[][] board = getGame().getBoard();

        if (radius.getX() == center.getX()) {
            if (radius.getY() < center.getY())
                return board[center.getX()][center.getY() + 1];
            else
                return board[center.getX()][center.getY() - 1];
        } else if (radius.getY() == center.getY()) {
            if (radius.getX() < center.getX())
                return board[center.getX() + 1][center.getY()];
            else
                return board[center.getX() - 1][center.getY()];
        } else if (radius.getX() > center.getX()) {
            if (radius.getY() > center.getY())
                return board[center.getX() - 1][center.getY() - 1];
            else
                return board[center.getX() - 1][center.getY() + 1];
        } else {
            if (radius.getY() > center.getY())
                return board[center.getX() + 1][center.getY() - 1];
            else
                return board[center.getX() + 1][center.getY() + 1];
        }

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
                if ((deltaX <= 1 && deltaY <= 1) && canBeForced(board[i][j]) && deltaH <= 1 && !board[i][j].isDome())

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
                Box forcedTo = diametricallyOppositeBox(dest, worker.getPos());
                Worker victim = dest.getWorker();
                victim.setPos(forcedTo);
                forcedTo.setWorker(victim);
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
