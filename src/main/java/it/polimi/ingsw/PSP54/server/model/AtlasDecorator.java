package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;

import java.util.ArrayList;

/**
 * Class representing the Atlas God Card.
 * From Santorini's rules: "Your Build: Your Worker may build a dome at any level."
 */
public class AtlasDecorator extends GodDecorator {

    private Box selectedBox;

    public AtlasDecorator(Player player) {
        super(player);
    }

    /**
     * Method used to perform a build action.
     * If the destination Box is a t level 3 it builds a dome, otherwise a message is notified to the player.
     * @param worker selected worker which the player wants to move.
     * @param dest selected box where to build.
     */
    @Override
    public void build(Worker worker, Box dest) throws InvalidBuildingException {

        ArrayList<Box> valid = worker.getBoxesToBuild();
        if (valid.contains(dest)) {
            if (!dest.isDome() && dest.getLevel() != 3) {
                this.selectedBox = dest;
                GameMessage buildOrDome = new StringMessage(this.getVirtualViewID(), StringMessage.buildOrDome);
                getGame().notify(buildOrDome);
                worker.setBuildToken(-1);
            } else
                super.build(worker, dest);
        }else
            throw new InvalidBuildingException();
    }

    /**
     * Method used to perform a binary choice.
     * If the choice is true it builds a dome in the previously selected box, otherwise adds a level to the box.
     * @param choice the player's choice.
     */
    @Override
    public void chose(boolean choice){
        if (choice){
            selectedBox.setDome(true);
        }else {
            selectedBox.setLevel(selectedBox.getLevel() + 1);
        }
        getGame().notifyBoard();
        getCurrentWorker().setBuildToken(0);
    }
}
