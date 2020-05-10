package it.polimi.ingsw.PSP54.utils.choices;

/**
 * Represents the choice regarding a Build Action
 */
public class MoveChoice extends PlayerChoice{
    private final int  x, y;
    private final boolean firstPlacement;

    public MoveChoice(int x, int y, boolean firstPlacement) {
        this.x = x;
        this.y = y;
        this.firstPlacement = firstPlacement;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isFirstPlacement() {
        return firstPlacement;
    }

}
