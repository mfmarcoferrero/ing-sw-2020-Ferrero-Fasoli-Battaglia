package it.polimi.ingsw.PSP54.utils.choices;

import java.io.Serializable;

/**
 * Represents the Challenger's choice regarding the Start Player.
 */
public class StartPlayerChoice extends PlayerChoice implements Serializable, Cloneable {

    private final int startPlayerIndex;

    public StartPlayerChoice(int startPlayerIndex) {
        this.startPlayerIndex = startPlayerIndex;
    }

    public int getStartPlayerIndex() {
        return startPlayerIndex;
    }
}
