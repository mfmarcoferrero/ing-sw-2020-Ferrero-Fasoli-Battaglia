package it.polimi.ingsw.PSP54.model;

public abstract class God {
    private boolean hasMoved;
    private boolean wentUp;

    public God() {
        this.hasMoved = false;
        this.wentUp = false;
    }
    public abstract boolean specialValidBuilding (Box pos);
    public abstract boolean specialValidMove (Box source, Box dest);

    public boolean validBuilding(Box pos){
        if (pos.isOccupied()){
            return false;
        }
        if (pos.dome){
            return false;
        }
        return true;
    }

    public boolean validMove(Box source, Box dest) {
        return false;
    }
}
