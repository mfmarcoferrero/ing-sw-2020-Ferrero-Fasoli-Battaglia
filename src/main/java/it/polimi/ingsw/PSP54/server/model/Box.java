package it.polimi.ingsw.PSP54.server.model;

import java.io.Serializable;

/**
 * Classe casella della tabella
 */

public class Box implements Cloneable, Serializable {
    public static final int BOARD_SIZE = 5;
    protected int x, y, level;
    protected boolean dome;
    protected Worker worker;

    /**
     * Costruttore della casella
     * Istanzia la casella senza livelli cupole o operai
     */
    public Box(int x, int y) {
        this.level = 0;
        this.dome = false;
        this.worker = null;
        this.x = x;
        this.y = y;
    }

    /**
     * Setting della posizione del worker
     * @param worker
     */
    public void setWorker(Worker worker) {
            this.worker = worker;
    }

    /**
     * Metodo per posizionare la costruzione
     */
    public void setBuilding (){
        if (!this.isOccupied()){
            if (level == 3){
                this.dome = true;
            }
            else
                this.level++;
        }
    }

    /**
     * Metodo che verifica se la casella é occupata
     * @return boolean in base al risultato
     */
    public boolean isOccupied (){
        return worker != null;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level){
        this.level=level;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    protected final Box clone() {
        final Box result = new Box(this.x,this.y);
        result.dome = this.dome;
        result.worker = this.worker;
        result.level = this.level;
        return result;
    }

    @Override
    public String toString (){
        return "BOX con coordinate: X = " + this.x + " Y = " + this.y;
    }
}
