package it.polimi.ingsw.PSP54.server.model;

public class InvalidBuildingException extends Exception {
    public InvalidBuildingException (){
        System.out.println("ERRORE: Costruzione non valida");
    }
}
