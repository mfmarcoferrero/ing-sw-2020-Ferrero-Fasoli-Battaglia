package it.polimi.ingsw.PSP54.server.model;

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

}
