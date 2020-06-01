package it.polimi.ingsw.PSP54.utils.choices;

import java.util.HashMap;

public class ExtractedCardsChoice extends PlayerChoice {

    private final HashMap<Integer, String> extractedCards;

    public ExtractedCardsChoice(HashMap<Integer, String> extractedCards) {
        this.extractedCards = extractedCards;
    }

    public HashMap<Integer, String> getExtractedCards() {
        return extractedCards;
    }
}
