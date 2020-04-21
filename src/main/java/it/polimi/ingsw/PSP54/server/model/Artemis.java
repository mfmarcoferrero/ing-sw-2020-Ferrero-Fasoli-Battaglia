package it.polimi.ingsw.PSP54.server.model;

public class Artemis extends God {
    private Box firstSource;
    private Worker firstWorkerMoved;

    public Artemis(Player player) {
        super(player);
    }

    /**
     * Artemis non modifica le modalità di costruzione
     * @param source
     * @param dest
     * @param setDome
     * @return
     */
    @Override
    public boolean validBuilding(Box source,Box dest, boolean setDome) {
        return normalValidBuilding(source,dest);
    }

    /**
     * Può muoversi due volte con lo stesso worker senza tornare nella casella di partenza
     * @param source
     * @param dest
     * @return
     */
    @Override
    public boolean validMove(Box source, Box dest) {
        int deltaLevel = Math.abs(dest.level - source.level);
        int deltaLevelUp = dest.level - source.level;
        if (normalValidMove(source,dest)){
            firstSource = source;
            firstWorkerMoved = source.getWorker();
            return true;
        }
        else if (adjacentBoxes(source,dest) && (deltaLevel <= 1) && (!dest.isOccupied()) && (!dest.isDome()) && player.moveToken == 0 && firstSource != dest && firstWorkerMoved == source.worker){
            if (this.isCanMoveUp() == false && deltaLevelUp > 0){
                return false;
            }
            return true;
        }
        else
            return false;
    }
}