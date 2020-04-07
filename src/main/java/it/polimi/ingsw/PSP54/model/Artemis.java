package it.polimi.ingsw.PSP54.model;

public class Artemis extends God {

    /**
     * Artemis non modifica le modalità di costruzione
     * @param source
     * @param dest
     * @return
     */
    @Override
    public boolean specialValidBuilding(Box source,Box dest) {
        return false;
    }

    /**
     * Può muoversi due volte
     * @param source
     * @param dest
     * @return
     */
    @Override
    public boolean specialValidMove(Box source, Box dest) {
        int deltaLevel = Math.abs(dest.level - source.level);
        if(doubleAdjacentBoxes(source,dest) && (deltaLevel <= 2) && (!dest.isOccupied())){
            return true;
        }
        return false;
    }

    /**
     * Metodo per verificare la doppia adiacenza di due caselle
     * @param box1
     * @param box2
     * @return
     */
    private boolean doubleAdjacentBoxes (Box box1,Box box2){
        int deltaX, deltaY;
        deltaX = Math.abs(box1.x - box2.x);
        deltaY = Math.abs(box1.y - box2.y);
        if (deltaX <= 2 && deltaY <= 2){
            return true;
        }
        else
            return false;
    }
}
