package it.polimi.ingsw.PSP54.utils;

public class CardChoice {

    private String name;
    private int virtualViewID;

    public CardChoice(int virtualViewID, String name) {
        this.name = name;
        this.virtualViewID = virtualViewID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVirtualViewID() {
        return virtualViewID;
    }

    public void setVirtualViewID(int virtualViewID) {
        this.virtualViewID = virtualViewID;
    }
}
