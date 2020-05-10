package it.polimi.ingsw.PSP54.utils.choices;

/**
 * Represents the player's choice of the God card.
 */
public class CardChoice extends PlayerChoice{

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
