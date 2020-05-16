package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;

/**
 * Your worker can build a dome on every level
 */
public class AtlasDecorator extends GodDecorator {

    private Box dest;

    public AtlasDecorator(Player player) {
        super(player);
    }

    @Override
    public void build(Worker worker, Box dest) throws InvalidBuildingException {

        if (!dest.isDome() && dest.getLevel() != 3) {
            this.dest = dest;
            GameMessage buildOrDome = new StringMessage(this.getVirtualViewID(), StringMessage.buildOrDome);
            getGame().notify(buildOrDome);
            worker.setMoveToken(-1);
            worker.setMoveToken(-1);
        }else
            super.build(worker, dest);

    }
}
