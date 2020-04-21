package it.polimi.ingsw.PSP54.server.model;
import java.util.ArrayList;

/**
 * Classe giocatore
 */
public class Player {
    public static int NORMAL_POWER = 0, APOLLO = 1, ARTEMIS = 2, ATHENA = 3, ATLAS = 4, DEMETER = 5;
    public God power = null;
    public Game game;
    private String playerName, workerColour;
    protected int player_index, moveToken, buildToken, godID, age;
    private ArrayList <Worker> workerList = new ArrayList<>(2);
    protected boolean isWinner = false, lose = false;


    /**
     * Costruttore della classe
     * @param playerName
     * @param age
     * @param workerColour
     */

    public Player (String playerName, int age, String workerColour, Game game){
        this.playerName = playerName;
        this.age = age;
        this.workerColour = workerColour;
        this.workerList.add(new Worker(this, workerColour, 0));
        this.workerList.add(new Worker(this, workerColour, 1));
        this.moveToken = 0;
        this.buildToken = 0;
        this.game = game;
    }

    public ArrayList<Worker> getWorkerList() {
        return workerList;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setMoveToken(int moveToken) {
        this.moveToken = moveToken;
    }

    public void setBuildToken(int buildToken) {
        this.buildToken = buildToken;
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
     * @param ind_worker indice dell'operaio da spostare
     * @param dest casella di destinazione
     */
    public void setInitialPosition (int ind_worker, Box dest) throws InvalidMoveException {
        if (!dest.isOccupied()) {
            workerList.get(ind_worker).setPos(dest);
            dest.setWorker(workerList.get(ind_worker));
        } else throw new InvalidMoveException();
    }

    /**
     * Metodo per decidere quale strategy utilizzare
     * @param godID
     */
    public void setPower(int godID) {
        this.godID = godID;

        if (this.godID == NORMAL_POWER){
            this.power = new NormalPower(this);
        } else if (this.godID == APOLLO) {
            this.power = new Apollo(this);
        } else if (this.godID == ARTEMIS) {
            this.power = new Artemis(this);
        } else if (this.godID == ATHENA) {
            this.power = new Athena(this);
        } else if (this.godID == ATLAS) {
            this.power = new Atlas(this);
        } else if (this.godID == DEMETER) {
            this.power = new Demeter(this);
        }
    }


    /**
     * Metodo per muovere l'operaio
     * @param worker_ind indice del worker
     * @param dest casella di destinazione
     * @throws InvalidMoveException se la mossa non è valida
     */
    public void move(int worker_ind, Box dest) throws InvalidMoveException {
        Worker w = getWorkerList().get(worker_ind);
        if (power.validMove(w.pos, dest)) {
            power.moveWorker(w,dest);
            moveToken--;
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
    public void build(int worker_ind, Box dest, boolean buildDome) throws InvalidBuildingException {
        if (power.validBuilding(workerList.get(worker_ind).pos, dest, buildDome)) {
            if (buildDome == false) {
                dest.setBuilding();
            } else if (buildDome){
                dest.setDome(true);
            }
            buildToken--;
        } else
            throw new InvalidBuildingException();
    }

    /**
     * Metodo per la gestione della fine di un turno
     * Controlla le condizioni di vittoria
     * @throws InterruptedException
     */
    public void endTurn() throws InterruptedException {
        if (moveToken + buildToken > 0) {
            lose = true;
        }
        if (isWinner){
            System.out.println(playerName + "ha vinto");
            wait(10000000);
            System.exit(0);
        }
        if (lose){
            System.out.println(playerName + "non può piu giocare");
            game.players.remove(player_index);
        }
        if (player_index == game.players.size() - 1) {
            game.setTurns(0);
        }
        else
            game.setTurns(player_index + 1);
    }

    @Override
    public String toString (){
        return "NOME: " + playerName + " GOD ID: " + godID + " INDICE NELL'ARRAY: " + player_index;
    }
}
