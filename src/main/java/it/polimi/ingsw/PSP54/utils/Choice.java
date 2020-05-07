package it.polimi.ingsw.PSP54.utils;

import java.io.Serializable;

public class Choice implements Serializable,Cloneable {

    private String choiceString;
    private int choiceInt;
    private int virtualViewID;

    public Choice(int choiceInt) {
        this.choiceInt = choiceInt;
    }

    public int getVirtualViewID() {
        return virtualViewID;
    }

    public void setVirtualViewID(int virtualViewID) {
        this.virtualViewID = virtualViewID;
    }

    public String getChoiceString() {
        return choiceString;
    }

    public void setChoiceString(String choiceString) {
        this.choiceString = choiceString;
    }

    public int getChoiceInt() {
        return choiceInt;
    }

    public void setChoiceInt(int choiceInt) {
        this.choiceInt = choiceInt;
    }
}
