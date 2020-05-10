package it.polimi.ingsw.PSP54.utils.choices;

public class WorkerChoice extends PlayerChoice{

    private final boolean male;

    public WorkerChoice(boolean male) {
        this.male = male;
    }

    public boolean isMale() {
        return male;
    }
}
