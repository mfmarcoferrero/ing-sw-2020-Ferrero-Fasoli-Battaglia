package it.polimi.ingsw.PSP54.model;

/**
 * Classe casella della tabella
 */
public class Box {
    public static final int BOARD_SIZE = 5;
    protected int x, y, level;
    public boolean dome, completed;
    private Worker worker;

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
}
