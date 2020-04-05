package it.polimi.ingsw.PSP54.model;

public class InvalidBoxException extends Exception {
    public InvalidBoxException () {
        System.out.println("ERRORE: Indice della casella non valido");
    }
}
