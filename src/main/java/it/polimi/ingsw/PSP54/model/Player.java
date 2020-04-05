package it.polimi.ingsw.PSP54.model;

/**
 * Classe giocatore
 */
public class Player extends Game {
    protected int player_index;
    protected static int APOLLO = 0, ARTEMIS = 1, ATHENA = 2, ATLAS = 3, DEMETER = 4;
    God power;
    protected String playerName;
    protected int age;
    protected int godID;
    protected Worker[] worker = new Worker[2];
    protected boolean isWinner ;
    protected boolean lose;


    /**
     * Costruttore della classe
     * @param playerName
     * @param age
     */
    public Player(String playerName, int age) {
        this.age=age;
        this.isWinner=false;
        this.lose=false;
        this.playerName=playerName;
    }

    public void setPlayer_index(int player_index) {
        this.player_index = player_index;
    }

    public int getPlayer_index() {
        return player_index;
    }

    public void setWorker(Worker[] worker) {
        this.worker = worker;
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

    public int getGodID() {
        return godID;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public boolean isLose() {
        return lose;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    /**
     * Metodo per la posizione del worker all'inizio del gioco
     * @param numWorker indice dell'operaio da spostare
     * @param dest casella di destinazione
     */
    public void setInitialPosition (int numWorker, Box dest) throws invalidMoveException {
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
     * @throws invalidMoveException se la mossa non è valida
     */
    public void move (int ind_worker,Box dest) {

    }
    public void build(int index, Box dest){

    }

    public void myTurn(){
    }

    public void endTurn() throws InterruptedException {
        if (isWinner){
            System.out.println(playerName+"ha vinto");
            wait(10000000);
            System.exit(0);
        }
        if (lose){
            System.out.println(playerName+"non può piu giocare");
            players.remove(player_index);
        }
        setTurns(player_index+1);
    }


}
