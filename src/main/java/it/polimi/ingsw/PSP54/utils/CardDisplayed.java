package it.polimi.ingsw.PSP54.utils;

public class CardDisplayed {

    private int virtualViewID;
    private String toDisplay;

    public CardDisplayed(int virtualViewID, String toDisplay) {
        this.virtualViewID = virtualViewID;
        this.toDisplay = toDisplay;
    }

    public int getVirtualViewID() {
        return virtualViewID;
    }

    public void setVirtualViewID(int virtualViewID) {
        this.virtualViewID = virtualViewID;
    }

    public String getToDisplay() {
        return toDisplay;
    }

    public void setToDisplay(String toDisplay) {
        this.toDisplay = toDisplay;
    }
}
