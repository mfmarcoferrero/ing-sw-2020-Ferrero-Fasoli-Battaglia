package it.polimi.ingsw.PSP54.model;

/**
 * Classe operaio
 */
public class Worker {
    private Player owner;
    private String color;
    public Box pos;

    public Worker(Player player,String color){
        this.owner = player;
        this.color = color;
    }

    /**
     * Metodo per posizionare l'operaio
     * @param dest casella di destinazione
     */
    public void setPos (Box dest){
        if (dest.isOccupied() == false){
            this.pos = dest;
            dest.setWorker(this);
        }
    }
}
