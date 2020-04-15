package it.polimi.ingsw.PSP54.model;

/**
 * Classe operaio
 */
public class Worker {
    private int workerID;
    private Player owner;
    private boolean male;
    private String color;
    public Box pos;

    public Worker(Player player,String color, int wID){
        this.owner = player;
        this.color = color;
        this.workerID = wID;
    }

    /**
     * Metodo per posizionare l'operaio
     * @param dest casella di destinazione
     */
    /*public void setPos (Box dest){
        if (!dest.isOccupied()){
            this.pos = dest;
            dest.setWorker(this);
        }
    }*/


    public String getColor() {
        return color;
    }

    public boolean isMale() {
        return male;
    }

    @Override
    public String toString(){
        return "Sono il worker con ID " + workerID;
    }
}

