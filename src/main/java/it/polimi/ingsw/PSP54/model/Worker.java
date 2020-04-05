package it.polimi.ingsw.PSP54.model;

/**
 * Classe operaio
 */
public class Worker {
    private int workerID;
    private Player owner;
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
    public void setPos (Box dest){
        if (dest.isOccupied() == false){
            this.pos = dest;
            dest.setWorker(this);
        }
    }

    public void setBuilding (Box dest){
        if (dest.isOccupied() == false){
            if (dest.level == 3){
                dest.dome = true;
                dest.completed = true;
            }
            else
                dest.level++;
        }
    }
}

