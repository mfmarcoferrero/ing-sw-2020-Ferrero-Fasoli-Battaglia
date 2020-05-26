package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;


public class HephaestusDecorator extends GodDecorator {

    private Box lastBuilding;

    public HephaestusDecorator(Player player) {
        super(player);
    }

    /**
     * Method used to perform a move action.
     * Calls the super method and sets the buildToken to 2.
     * @param worker selected worker which the player wants to move.
     * @param dest selected destination box.
     * @throws InvalidMoveException if the move can't be done.
     */
    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {
        super.move(worker, dest);
        worker.setBuildToken(2);
    }

    /**
     * Method used to perform a build action.
     * Calls the super method and eventually notifies a message to the player in accordance to the building possibilities.
     * @param worker selected worker which the player wants to move.
     * @param dest selected box where to build.
     */
    @Override
    public void build(Worker worker, Box dest) throws InvalidBuildingException {
        super.build(worker, dest);
        if (!dest.isDome()) {
            setLastBuilding(dest);
            worker.setBuildToken(-1);
            GameMessage buildAgain = new StringMessage(getVirtualViewID(), StringMessage.buildAgain);
            getGame().notify(buildAgain);
        }
    }

    /**
     * Method used to perform a binary choice.
     * If choice is true builds another level on the previously built box, otherwise set them to zero.
     * @param choice the player's choice.
     */
    @Override
    public void chose(boolean choice) {
        if (choice){
            getCurrentWorker().setBuildToken(1);
            try {
                super.build(getCurrentWorker(), getLastBuilding());
            } catch (InvalidBuildingException e) {
                GameMessage invalidBuild = new StringMessage(getVirtualViewID(), "You can't build again!");
                getGame().notify(invalidBuild);
                getGame().endTurn(this);
            }
        }else
            getCurrentWorker().setBuildToken(0);
    }

    public Box getLastBuilding() {
        return lastBuilding;
    }

    public void setLastBuilding(Box lastBuilding) {
        this.lastBuilding = lastBuilding;
    }
}
