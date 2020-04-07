package it.polimi.ingsw.PSP54.model;

/**
 * Classe casella della tabella
 */

public class Box {
    protected static final int BOARD_SIZE = 5;
    protected int x, y, level;
    protected boolean dome, completed;
    protected Worker worker;

    /**
     * Costruttore della casella
     * Istanzia la casella senza livelli cupole o operai
     */
    public Box(int x, int y) throws InvalidBoxException {
        this.level = 0;
        this.dome = false;
        this.worker = null;
        this.completed = false;
        this.x = x;
        this.y = y;
        if (x >= BOARD_SIZE || x < 0 || y >= BOARD_SIZE || y < 0){
            throw new InvalidBoxException();
        }
    }

    public void setBox (int level,boolean dome,Worker worker){
        this.level = level;
        this.dome = dome;
        if(this.dome == false) {
            this.worker = worker;
        }
    }

    public void setWorker(Worker worker) {
        if(this.dome == false) {
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

    public Worker getWorker() {
        return worker;
    }

    public boolean isDome() {
        return dome;
    }

    public void setDome(boolean dome) {
        this.dome = dome;
    }

    public void setX(int x) throws InvalidBoxException {
        if(x < 0 || x >= BOARD_SIZE){
            throw new InvalidBoxException();
        }
        this.x = x;
    }

    public void setY(int y) throws InvalidBoxException {
        if(y < 0 || y >= BOARD_SIZE){
            throw new InvalidBoxException();
        }
        this.y = y;
    }

    public int getLevel() {
        return level;
    }
}
