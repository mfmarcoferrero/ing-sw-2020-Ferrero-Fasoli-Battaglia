package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.utils.GameMessage;

import java.util.ArrayList;

/**
 * Your worker can build a dome on every level
 */
public class AtlasDecorator extends GodDecorator {

    public AtlasDecorator(Player player) {
        super(player);
    }

    /*
    PSEUDOCODE

    build(Worker worker, Box dest):
        if !dest.isDome && dest.getLevel() != 3
            notifyController()
            acquireChoice()
            performBuild
     */

    @Override
    public void build(Worker worker, Box dest) throws InvalidBuildingException {
        ArrayList<Box> valid = worker.getBoxesToBuild();
        int currentBuildToken = worker.getBuildToken();

        if (valid.contains(dest)) {
            if (worker.isDomeBuilder()) {
                dest.setDome(true);
                worker.setDomeBuilder(false);
            } else
                super.build(worker, dest);
            worker.setBuildToken(currentBuildToken - 1);
        }
        else
            throw new InvalidBuildingException();

        //TODO: endTurn?
        //Box destAtCall = new Box(dest.getX(), dest.getY(), dest.getLevel(), dest.isDome());
        //if dest.getLevel == destAtCall.getLevel+1 || dest.isDome
            //endTurn

    }
}
