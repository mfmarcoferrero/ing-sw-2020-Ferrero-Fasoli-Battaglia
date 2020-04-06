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

    /**
     * Metodo per la verifica di caselle adiacenti
     * @param box1
     * @param box2
     * @return
     */
    public boolean adjacentBoxes (Box box1, Box box2){
        int deltaX,deltaY;
        deltaX = Math.abs(box1.x - box2.x);
        deltaY = Math.abs(box1.y - box2.y);
        if (deltaX == 1 || deltaY == 1){
            return true;
        }
        return false;
    }
}

