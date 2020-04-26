package it.polimi.ingsw.PSP54.server.model;

public class Demeter extends God {
    private Worker firstWorker;

    public Demeter(Player player) {
        super(player);
    }

    /**
     * Non modifica la modalità di spostamento
     * @param source
     * @param dest
     * @return
     */
    @Override
    public boolean validMove(Box source, Box dest) {
        return normalValidMove(source,dest);
    }

    /**
     * Può costruire due volte con lo stesso worker
     * @param source
     * @param dest
     * @param setDome
     * @return
     */
    @Override
    public boolean validBuilding(Box source, Box dest, boolean setDome) {
        if(normalValidBuilding(source,dest)){
            firstWorker = source.worker;
            return true;
        }
        else return adjacentBoxes(source, dest) && (!dest.isOccupied()) && (!dest.isDome()) && player.buildToken == 0 && firstWorker == source.worker;
    }
}
