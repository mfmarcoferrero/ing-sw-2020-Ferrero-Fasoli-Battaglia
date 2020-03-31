package it.polimi.ingsw.PSP54.model;

/**
 * Classe casella della tabella
 */
public class Box {
    public int level;
    public boolean dome;
    private Worker worker;

    /**
     * Costruttore della casella
     * Istanzia la casella senza livelli cupole o operai
     */
    public Box() {
        this.level = 0;
        this.dome = false;
        this.worker = null;
    }
    /**
     *
     */
    public void setBox (int level,boolean dome,Worker worker){
        this.level = level;
        this.dome = dome;
        if(this.dome == false) {
            this.worker = worker;
        }
    }

    public void setWorker(Worker worker) {
        if(this.dome == false){
            this.worker = worker;
        }
    }

    /**
     * Metodo che verifica se la casella é occupata
     * @return boolean in base al risultato
     */
    public boolean isOccupied (){
        if (worker != null){
            return true;
        }
        else return false;
    }
}
