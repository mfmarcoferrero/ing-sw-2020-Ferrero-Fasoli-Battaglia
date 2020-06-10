package it.polimi.ingsw.PSP54.utils.messages;

import it.polimi.ingsw.PSP54.server.model.Box;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class AvailableBoxesMessage extends GameMessage implements Serializable, Cloneable {

    private final List<Box> availableBoxes;

    public AvailableBoxesMessage(Integer virtualViewID, List<Box> availableBoxes) {
        super(virtualViewID);
        this.availableBoxes = availableBoxes;
    }

    public List<Box> getAvailableBoxes() {
        return availableBoxes;
    }

}
