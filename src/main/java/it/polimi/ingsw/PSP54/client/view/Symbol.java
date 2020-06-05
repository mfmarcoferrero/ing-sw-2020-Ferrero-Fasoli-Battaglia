package it.polimi.ingsw.PSP54.client.view;

public enum Symbol {

    UNICODE_BORDER("｜"),
    UNICODE_MALE_WORKER("♂ "),
    UNICODE_FEMALE_WORKER("♀ "),
    UNICODE_DOME("██"),
    UNICODE_SQUARE("██");

    private final String codePoint;

    Symbol(String codePoint){
        this.codePoint = codePoint;
    }

    @Override
    public String toString(){
        return codePoint;
    }
}
