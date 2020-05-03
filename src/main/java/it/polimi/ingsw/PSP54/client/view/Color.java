package it.polimi.ingsw.PSP54.client.view;

public enum Color {
    ANSI_BORDER("\u001B[38;5;58m"),
    ANSI_GROUND("\u001B[38;5;34m"),
    ANSI_FIRST("\u001B[38;5;244m"),
    ANSI_SECOND("\u001B[38;5;248m"),
    ANSI_THIRD("\u001B[38;5;252m"),
    ANSI_RESET("\u001B[38;5;251"),
    ANSI_GROUND_BACKGROUND("\u001B[48;5;34m"),
    ANSI_FIRST_BACKGROUND("\u001B[48;5;244m"),
    ANSI_SECOND_BACKGROUND("\u001B[48;5;248m"),
    ANSI_THIRD_BACKGROUND("\u001B[48;5;252m"),
    ANSI_BACKGROUND_RESET("\u001B[48;5;235m"),
    ANSI_DOME("\u001B[38;5;20m"),
    ANSI_P1("\u001B[38;5;14m"),
    ANSI_P2("\u001B[38;5;9m"),
    ANSI_P3("\u001B[38;5;11m");

    private final String escape;

    Color(String escape) {
        this.escape = escape;
    }

    @Override
    public String toString(){
        return escape;
    }
}
