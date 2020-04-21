package it.polimi.ingsw.PSP54.server.model;

public abstract class God {
    private boolean canMoveUp;
    protected Player player;

    public God(Player player) {
        this.player = player;
        this.canMoveUp = true;
    }

    public abstract boolean validBuilding (Box source, Box dest, boolean setDome);
    public abstract boolean validMove (Box source, Box dest);


    public void moveWorker(Worker worker, Box dest){
        Box source = worker.pos;
        dest.setWorker(worker);
        worker.setPos(dest);
        source.setWorker(null);
    }

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
        if (deltaX <= 1 && deltaY <= 1){
            return true;
        }
        return false;
    }

    /**
     * Metodo che verifica la validità di una mossa spostamento normale
     * @param source
     * @param dest
     * @return
     */
    public boolean normalValidMove(Box source, Box dest) {
        int deltaLevel = Math.abs(dest.level - source.level);
        int deltaLevelUp = dest.level - source.level;
        if(adjacentBoxes(source,dest) && (deltaLevel <= 1) && (!dest.isDome()) && (!dest.isOccupied()) && player.moveToken == 1){
            if(dest.level == 3 && source.level == 2){
                player.isWinner = true;
            }
            if (canMoveUp == false && deltaLevelUp > 0){
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Metodo che verifica la validità di una mossa costruzione normale
     * @param source
     * @param dest
     * @return
     */
    public boolean normalValidBuilding(Box source, Box dest) {
        if(adjacentBoxes(source,dest) && (!dest.isOccupied()) && (!dest.isDome()) && player.buildToken == 1){
            return true;
        }
        return false;
    }

    public void setCanMoveUp(boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }

    public boolean isCanMoveUp() {
        return canMoveUp;
    }
}

