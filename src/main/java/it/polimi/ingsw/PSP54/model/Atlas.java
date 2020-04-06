package it.polimi.ingsw.PSP54.model;

public class Atlas extends God {
    @Override
    public boolean specialValidBuilding(Box source,Box dest) {
        return false;
    }

    @Override
    public boolean specialValidMove(Box source, Box dest) {
        return false;
    }
}
