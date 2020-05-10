package it.polimi.ingsw.PSP54.utils.choices;

/**
 * Represents the player's choice regarding a build action.
 */
public class BuildChoice extends PlayerChoice {

    private final int x;
    private final int y;
    private final boolean setDome;



    public BuildChoice(int x, int y, boolean setDome) {
        this.x = x;
        this.y = y;
        this.setDome = setDome;
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
