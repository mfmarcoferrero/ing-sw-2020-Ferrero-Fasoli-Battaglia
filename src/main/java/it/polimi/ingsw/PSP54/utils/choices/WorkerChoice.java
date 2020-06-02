package it.polimi.ingsw.PSP54.utils.choices;

import java.io.Serializable;

/**
 * Represents the player's choice regarding the worker selection.
 */
public class WorkerChoice extends PlayerChoice implements Serializable, Cloneable {

    private final boolean male;

    public WorkerChoice(boolean male) {
        this.male = male;
    }

    public boolean isMale() {
        return male;
    }

}
