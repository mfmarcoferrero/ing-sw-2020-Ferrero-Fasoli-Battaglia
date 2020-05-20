package it.polimi.ingsw.PSP54.utils.choices;

import java.io.Serializable;

/**
 * Represents the player's choice of the God card.
 */
public class CardChoice extends PlayerChoice implements Serializable, Cloneable {

    private String choiceName;
    private final int choiceKey;

    public CardChoice(int choiceKey) {
        this.choiceKey = choiceKey;
    }

    public String getChoiceName() {
        return choiceName;
    }

    public void setChoiceName(String choiceName) {
        this.choiceName = choiceName;
    }

    public int getChoiceKey() {
        return choiceKey;
    }

}
