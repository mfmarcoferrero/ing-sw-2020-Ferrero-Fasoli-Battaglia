package it.polimi.ingsw.PSP54.model;

public class Apollo extends God {

    @Override
    public boolean specialValidMove(Box source, Box dest) {
        return false;
    }

    @Override
    public boolean specialValidBuilding(Box pos) {
        return false;
    }
}
