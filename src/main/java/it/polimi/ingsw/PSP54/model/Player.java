package it.polimi.ingsw.PSP54.model;

/**
 * Classe giocatore
 */
public class Player {
    public static int APOLLO = 0, ARTEMIS = 1, ATHENA = 2, ATLAS = 3, DEMETER = 4;
    God power;
    private String playerName;
    public int age;
    private String workerColour;
    private int godID;
    private Worker worker[] = new Worker[2];
    private boolean isWinner = false;
    public class InvalidMoveException extends Exception{};


    /**
     * Costruttore della classe
     * @param playerName
     * @param age
     * @param workerColour
     */
    public Player(String playerName, int age, String workerColour) {
        this.playerName = playerName;
        this.age = age;
        this.workerColour = workerColour;
        this.worker[0] = new Worker(this,workerColour);
        this.worker[1] = new Worker(this,workerColour);
    }
    public void setGodID(int godID) {
        this.godID = godID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getAge() {
        return age;
    }

    public String getWorkerColour() {
        return workerColour;
    }

    public int getGodID() {
        return godID;
    }

    public boolean isWinner() {
        return isWinner;
    }

    /**
     * Metodo per la posizione del worker all'inizio del gioco
     * @param numWorker indice dell'operaio da spostare
     * @param dest casella di destinazione
     */
    public void setWorkerPosition (int numWorker, Box dest){
        worker[numWorker].setPos(dest);
    }

    /**
     * Metodo per decidere quale strategy utilizzare
     * @param godID
     */
    public void setPower (int godID){
        if (godID == APOLLO){
            this.power = new Apollo();
        }
        else if (godID == ARTEMIS){
            this.power = new Artemis();
        }
        else if (godID == ATHENA){
            this.power = new Athena();
        }
        else if (godID == ATLAS){
            this.power = new Atlas();
        }
        else if (godID == DEMETER){
            this.power = new Demeter();
        }
    }

    /**
     * Metodo per muovere l'operaio
     * chiama la funzione validMove della classe astratta
     * @param ind_worker indice del worker
     * @param dest casella di destinazione
     * @throws InvalidMoveException se la mossa non è valida
     */
    public void move (int ind_worker,Box dest) throws InvalidMoveException {
        if (power.validMove(worker[ind_worker].pos,dest)){
            worker[ind_worker].setPos(dest);
        }
        else throw new InvalidMoveException();
    }
}
