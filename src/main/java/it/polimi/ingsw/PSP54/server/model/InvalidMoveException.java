package it.polimi.ingsw.PSP54.server.model;

public class InvalidMoveException extends Exception {
    public InvalidMoveException(){
        System.out.println("ERROR: Invalid Move");
    }
}
