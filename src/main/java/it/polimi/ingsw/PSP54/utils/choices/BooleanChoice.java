package it.polimi.ingsw.PSP54.utils.choices;

import java.io.Serializable;

/**
 * Represents the player's choice regarding a binary decision.
 */
public class BooleanChoice extends PlayerChoice implements Serializable, Cloneable {

    private final boolean choice;
    private boolean gameEnded;

    public BooleanChoice(boolean choice) {
        this.choice = choice;
    }

    public boolean isChoice() {
        return choice;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }
}
