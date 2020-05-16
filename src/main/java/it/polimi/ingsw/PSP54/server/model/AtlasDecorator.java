package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;

import java.util.ArrayList;

/**
 * Your worker can build a dome on every level
 */
public class AtlasDecorator extends GodDecorator {

    private Box selectedBox;

    public AtlasDecorator(Player player) {
        super(player);
    }

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
        }
    }

    @Override
    public void chose(boolean choice){
        if (choice){
            selectedBox.setDome(true);
            getCurrentWorker().setBuildToken(0);
        }else
            selectedBox.setLevel(selectedBox.getLevel()+1);
    }
}
