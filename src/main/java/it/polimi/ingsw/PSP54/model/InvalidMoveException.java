package it.polimi.ingsw.PSP54.model;

public class InvalidMoveException extends Exception {
    public InvalidMoveException(){
        System.out.println("ERRORE: Mossa non valida");
    }
}
