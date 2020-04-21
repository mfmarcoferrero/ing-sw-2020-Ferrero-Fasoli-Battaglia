package it.polimi.ingsw.PSP54.server.model;

public class NormalPower extends God {

    public NormalPower(Player player) {
        super(player);
    }

    @Override
    public boolean validBuilding(Box source, Box dest, boolean setBuilding) { return normalValidBuilding(source,dest); }

    @Override
    public boolean validMove(Box source, Box dest) {
        return normalValidMove(source,dest);
    }
}