package it.polimi.ingsw.PSP54.server.model;

public class PanDecorator extends GodDecorator {

    public PanDecorator(Player player) {
        super(player);
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

        Box previousPos = worker.getPos();
        super.move(worker, dest);

        if (previousPos.getLevel() >= dest.getLevel() + 2)
            getGame().notifyWinner(this);
    }
}
