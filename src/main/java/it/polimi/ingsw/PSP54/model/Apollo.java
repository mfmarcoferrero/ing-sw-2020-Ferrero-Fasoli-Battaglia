package it.polimi.ingsw.PSP54.model;

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

        if (this.isCanMoveUp() == false && deltaLevelUp > 0){
            return false;
        }
        if (normalValidBuilding(source,dest)){
            return true;
        }
        else if(adjacentBoxes(source,dest) && (deltaLevel <= 1) && (dest.isOccupied()) && (!dest.isDome())){
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
}
