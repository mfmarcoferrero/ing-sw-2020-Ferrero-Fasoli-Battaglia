package it.polimi.ingsw.PSP54.utils.choices;

import java.io.Serializable;

public class NumberOfPlayers implements Serializable, Cloneable {

    private int numberOfPlayers;

    public NumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
