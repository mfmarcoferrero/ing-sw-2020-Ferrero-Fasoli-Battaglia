package it.polimi.ingsw.PSP54.utils.messages;

import it.polimi.ingsw.PSP54.server.model.Box;

import java.util.List;

public class AvailableBoxesMessage extends GameMessage{

    private final int virtualViewID;
    private final List<Box> availableBoxes;

    public AvailableBoxesMessage(int virtualViewID, List<Box> availableBoxes) {
        this.virtualViewID = virtualViewID;
        this.availableBoxes = availableBoxes;
    }

    public int getVirtualViewID() {
        return virtualViewID;
    }

    public List<Box> getAvailableBoxes() {
        return availableBoxes;
    }

}
