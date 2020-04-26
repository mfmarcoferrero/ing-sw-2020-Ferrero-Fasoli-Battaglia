package it.polimi.ingsw.PSP54.server.model;

public class Apollo extends God {

    public Apollo(Player player) {
        super(player);
    }

    /**
     * Può muoversi anche invertendo la propria posizione con quella di un worker su una casella adiacente
     * @param source
     * @param dest
     * @return
     */
    @Override
    public boolean validMove(Box source, Box dest) {
        int deltaLevel = Math.abs(dest.level - source.level);
        int deltaLevelUp = dest.level - source.level;

        if (!this.isCanMoveUp() && deltaLevelUp > 0){
            return false;
        }
        if (normalValidBuilding(source,dest)){
            source.setWorker(null);
            return true;
        }
        else if(adjacentBoxes(source,dest) && (deltaLevel <= 1) && (dest.isOccupied()) && (!dest.isDome()) && player.moveToken == 1){
            source.setWorker(dest.worker);
            return true;
        }
        else
            return false;
    }
    /**
     * Apollo non modifica le modalità di costruzione
     * @param source
     * @param dest
     * @return
     */
    @Override
    public boolean validBuilding(Box source, Box dest, boolean setDome) {
        return normalValidBuilding(source,dest);
    }

    @Override
    public void moveWorker (Worker worker, Box dest) {
        dest.setWorker(worker);
        worker.setPos(dest);
    }

}
