package it.polimi.ingsw.PSP54.model;
import java.util.ArrayList;

/**
 * Classe giocatore
 */
public class Player {
    public static int APOLLO = 0, ARTEMIS = 1, ATHENA = 2, ATLAS = 3, DEMETER = 4;
    God power;
    private String playerName;
    protected int player_index;
    public int age;
    private String workerColour;
    private int godID;
    private ArrayList <Worker> workerList = new ArrayList<>(2);
    private boolean isWinner = false, lose = false;

    /**
     * Costruttore della classe
     *
     * @param playerName
     * @param age
     * @param workerColour
     */

    public Player (String playerName, int age, String workerColour) {
        this.playerName = playerName;
        this.age = age;
        this.workerColour = workerColour;
        this.workerList.add(new Worker(this, workerColour, 1));
        this.workerList.add(new Worker(this, workerColour, 2));
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

    public void setPlayer_index(int player_index) {
        this.player_index = player_index;
    }

    public int getPlayer_index() {
        return player_index;
    }

    /**
     * Metodo per la posizione del worker all'inizio del gioco
     * @param numWorker indice dell'operaio da spostare
     * @param dest casella di destinazione
     */
    public void setInitialPosition (int numWorker, Box dest) {
        workerList.get(numWorker).setPos(dest);
    }

    /**
     * Metodo per decidere quale strategy utilizzare
     * @param godID
     */
    public void setPower(int godID) {
        if (godID == APOLLO) {
            this.power = new Apollo();
        } else if (godID == ARTEMIS) {
            this.power = new Artemis();
        } else if (godID == ATHENA) {
            this.power = new Athena();
        } else if (godID == ATLAS) {
            this.power = new Atlas();
        } else if (godID == DEMETER) {
            this.power = new Demeter();
        }
    }


    /**
     * Metodo che verifica la validità di una mossa spostamento normale
     * @param source
     * @param dest
     * @return
     */
    public boolean normalValidMove(Box source, Box dest) {
        int deltaLevel = Math.abs(dest.level - source.level);
        if(power.adjacentBoxes(source,dest) && (deltaLevel <= 1) && (!dest.isOccupied())){
            if(dest.level == 3 && source.level == 2){
                this.isWinner = true;
            }
            return true;
        }
        return false;
    }

    /**
     * Metodo che verifica la validità di una mossa costruzione normale
     * @param source
     * @param dest
     * @return
     */
    public boolean normalValidBuilding(Box source, Box dest) {
        if(power.adjacentBoxes(source,dest) && (!dest.isOccupied()) && (!dest.completed)){
            return true;
        }
        return false;
    }

    /**
     * Metodo per muovere l'operaio
     * @param worker_ind indice del worker
     * @param dest casella di destinazione
     * @throws InvalidMoveException se la mossa non è valida
     */
    public void move(int worker_ind, Box dest) throws InvalidMoveException {
        Worker w = workerList.get(worker_ind);
        if (normalValidMove(w.pos, dest) || power.specialValidMove(w.pos,dest)) {
            w.setPos(dest);
        }
        else
            throw new InvalidMoveException();
    }

    /**
     * Metodo per far costruire all'operaio*
     * @param worker_ind indice del worker
     * @param dest casella dove costruire
     * @throws InvalidBuildingException se la mossa di costruzione non è valida
     */
    public void build(int worker_ind, Box dest) throws InvalidBuildingException {
        Worker w = workerList.get(worker_ind);
        if (normalValidBuilding(w.pos, dest) || power.specialValidBuilding(w.pos,dest)) {
            w.setBuilding(dest);
        } else
            throw new InvalidBuildingException();
    }

    public void myTurn(){
    }

    public void endTurn() throws InterruptedException {
        if (isWinner){
            System.out.println(playerName + "ha vinto");
            wait(10000000);
            System.exit(0);
        }
        if (lose){
            System.out.println(playerName + "non può piu giocare");
            players.remove(player_index);
        }
        setTurns(player_index + 1);
    }


}
