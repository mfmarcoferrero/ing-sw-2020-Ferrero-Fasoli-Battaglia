package it.polimi.ingsw.PSP54.model;

public class Atlas extends God {

    public Atlas(Player player) {
        super(player);
    }

    /**
     * Atlas può costruire una cupola su qualsiasi livello, anche sul terreno
     * @param source
     * @param dest
     * @param setDome
     * @return
     */
    @Override
    public boolean validBuilding(Box source,Box dest,boolean setDome) {
        if (setDome == false){
            return normalValidBuilding(source,dest);
        }
        else if (setDome && adjacentBoxes(source,dest) && !(dest.isOccupied()) && !(dest.isDome())){
            return true;
        }
        else
            return false;
    }

    /**
     * Atlas non modifica le modalità di spostamento
     * @param source
     * @param dest
     * @return
     */
    @Override
    public boolean validMove(Box source, Box dest) {
        return normalValidMove(source,dest);
    }
}
