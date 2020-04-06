package it.polimi.ingsw.PSP54.model;

public class Apollo extends God {

    /**
     * Può muoversi anche invertendo la propria posizione con quella di un worker su una casella adiacente
     * @param source
     * @param dest
     * @return
     */
    @Override
    public boolean specialValidMove(Box source, Box dest) {
        int deltaLevel = Math.abs(dest.level - source.level);
        if(adjacentBoxes(source,dest) && (deltaLevel <= 1) && (dest.isOccupied())){
            source.setWorker(dest.worker);
            return true;
        }
        else
            return false;
    }

    /**
     * Apollo non modifica le modalità di costruzione
     * @param source
     * @param pos
     * @return
     */
    @Override
    public boolean specialValidBuilding(Box source, Box pos) {
        return false;
    }

}
