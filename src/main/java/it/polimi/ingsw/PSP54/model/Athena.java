package it.polimi.ingsw.PSP54.model;

public  class Athena extends God {
    @Override
    public boolean specialValidBuilding(Box pos) {
        return false;
    }

    @Override
    public boolean specialValidMove(Box source, Box dest) {
        return false;
    }
}
