package it.polimi.ingsw.PSP54.utils.choices;

import java.io.Serializable;

/**
 * Represents the player's choice regarding a build action.
 */
public class BuildChoice extends PlayerChoice implements Serializable, Cloneable {

    private final int x;
    private final int y;
    private boolean setDome;



    public BuildChoice(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isSetDome() {
        return setDome;
    }
}
