package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;

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

        if (!dest.isDome() && dest.getLevel() != 3) {
            GameMessage buildOrDome = new StringMessage(this.getVirtualViewID(), StringMessage.buildOrDome);
            //getGame().notify(buildOrDome);
        }else
            super.build(worker, dest);

        //TODO: endTurn?
        //Box destAtCall = new Box(dest.getX(), dest.getY(), dest.getLevel(), dest.isDome());
        //if dest.getLevel == destAtCall.getLevel+1 || dest.isDome
            //endTurn

    }
}
