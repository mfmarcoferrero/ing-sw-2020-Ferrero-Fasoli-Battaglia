package it.polimi.ingsw.PSP54.model;

/**
 * Classe casella della tabella
 */
public class Box {
    protected int column;
    protected int row;
    protected int level;
    public boolean dome;
    private Worker worker;

    /**
     * Costruttore della casella
     * Istanzia la casella senza livelli cupole o operai
     */
    public Box(int x, int y) {
        column=y;
        row=x;
        this.level = 0;
        this.dome = false;
        this.worker = null;
    }
    /**
     *
     */
    public void setWorker(Worker worker) {
        if(this.dome == false){
            this.worker = worker;
        }
    }

    /**
     * Metodo che verifica se la casella é occupata
     * @return boolean in base al risultato
     */
    public boolean isOccupied(){
        if(this.dome==true || this.worker!=null)
            return true;
        else
            return false;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Worker getWorker() {
        return worker;
    }

    public boolean isDome() {
        return dome;
    }

    public void setDome(boolean dome) {
        this.dome = dome;
    }
}
