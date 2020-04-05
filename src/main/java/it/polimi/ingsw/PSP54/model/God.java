package it.polimi.ingsw.PSP54.model;

public abstract class God {
    private boolean hasMoved;
    private boolean wentUp;

    public God() {
        this.hasMoved = false;
        this.wentUp = false;
    }
    public abstract boolean specialValidBuilding (Box source, Box pos);
    public abstract boolean specialValidMove (Box source, Box dest);

}

