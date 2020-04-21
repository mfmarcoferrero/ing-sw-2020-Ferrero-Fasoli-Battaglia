package it.polimi.ingsw.PSP54.server.model;

public class IllegalNumberOfPlayersException extends Exception {
    public IllegalNumberOfPlayersException() {
        System.out.println("ERRORE: numero di giocatori non valido");
    }
}
