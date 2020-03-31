package it.polimi.ingsw.PSP54.model;

public class IllegalNumberOfPlayersException extends Exception {
    public void IllegalNumberOfPlayersException() {
        System.out.println("ERRORE: numero di giocatori non valido");
    }
}
