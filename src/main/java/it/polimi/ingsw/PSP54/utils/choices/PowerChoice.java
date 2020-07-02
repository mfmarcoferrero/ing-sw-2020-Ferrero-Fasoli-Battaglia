package it.polimi.ingsw.PSP54.utils.choices;

import java.io.Serializable;

/**
 * Represents the player's choice of the God card.
 */
public class PowerChoice extends PlayerChoice implements Serializable, Cloneable {

    private final int choiceKey;

    public PowerChoice(int choiceKey) {
        this.choiceKey = choiceKey;
    }

    public int getChoiceKey() {
        return choiceKey;
    }

}
