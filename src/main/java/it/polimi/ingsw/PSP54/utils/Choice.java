package it.polimi.ingsw.PSP54.utils;

public class Choice {

    private String choice;
    private int virtualViewID;

    public Choice(int virtualViewID, String choice) {
        this.choice = choice;
        this.virtualViewID = virtualViewID;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public int getVirtualViewID() {
        return virtualViewID;
    }

    public void setVirtualViewID(int virtualViewID) {
        this.virtualViewID = virtualViewID;
    }
}
