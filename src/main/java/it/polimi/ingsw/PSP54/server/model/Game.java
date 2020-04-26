package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.utils.Move;
import it.polimi.ingsw.PSP54.utils.Build;

import java.util.Observable;
import java.util.Random;
import java.util.Vector;

/**
 * Classe gestione del gioco
 */
public class Game extends Observable {
    protected Vector<Player> players = new Vector<>();
    protected Box[][] board = new Box[5][5];

    public Game() {
        for (int i = 0;i < Box.BOARD_SIZE;i++) {
            for (int j = 0; j < Box.BOARD_SIZE; j++) {
                board[i][j] = new Box(i, j);
            }
        }
    }

    public void startGame (){
        return;
    }

    /**
     * Istanzia un nuovo giocatore
     * Notifica le virtualView che lo osservano con una board
     * @param name
     * @param age
     * @param workerColour
     */
    public void newPlayer(String name, int age , String workerColour) throws IllegalNumberOfPlayersException {
       if (players.size() < 3) {
           players.add(new Player(name,age,workerColour,this));
           setChanged();
           notifyObservers(board.clone());
       }
       else
           throw new IllegalNumberOfPlayersException();
    }

    /**
     * Assegna ad ogni giocatore una carta God in modo casuale
     */
    public void godAssignment () {
        Random randomGenerator = new Random();
        int num = randomGenerator.nextInt(5) + 1;
        for (Player p : players){
            while (godIdCheck(num)){
                num = randomGenerator.nextInt(5) + 1;
            }
            p.setPower(num);
        }
    }

    /**
     * Controlla se il godId è già stato assegnato a un altro giocatore
     */
    public boolean godIdCheck(int num){
        for (Player p : players){
            if (num == p.godID){
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo per capire il giocatore più giovane
     * @return indice del giocatore più giovane
     */
    public int youngestPlayer(){
        int min = players.get(0).age, ind = 0;
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).age <= min){
                ind = i;
            }
        }
        return ind;
    }

    /**
     * Metodo per ordinare l'ArrayList di giocatori in ordine d'età
     */
    public void bubbleSortPlayers (){
        for (int i = 0; i < players.size() ;i++){
            boolean flag = false;
            for (int j = 0; j < players.size() - 1; j++){
                if(players.get(j).age > players.get(j + 1).age) {
                    Player provElem = players.get(j);
                    players.set(j,players.get(j+1));
                    players.set(j+1,provElem);
                    flag = true;
                }
            }
            if(!flag) break;
        }
        for (int i = 0; i < players.size(); i++){
            players.get(i).player_index = i;
        }
    }

    /**
     * Metodo per impostare il turno del player
     * @param index indice del player
     */
    public void setTurns(int index) {
        System.out.println("Turno di: " + players.get(index).getPlayerName());
        players.get(index).buildToken = 1;
        players.get(index).moveToken = 1;
        System.out.println("Hai " + players.get(index).moveToken + " mosse");
        System.out.println("Puoi costruire " + players.get(index).buildToken + " volte");
        System.out.println("---------------------------------------");
    }

    /**
     * Metodo per chiamare lo spostamento di un worker e restituire alla view la board che ha subito il cambiamento
     * @param move oggetto che contiene le informazioni per eseguire lo spostamento
     * @throws InvalidMoveException
     */
    public void move (Move move) throws InvalidMoveException {
        players.get(move.getPlayer_ind()).move(move.getWorker_ind(),board[move.getX()][move.getY()]);
        setChanged();
        notifyObservers(board.clone());
    }

    /**
     * Metodo per chiamare la costruzione e restituire alla view la board che ha subito il cambiamento
     * @param build oggetto che contiene le informazioni per costruire
     * @throws InvalidBuildingException
     */
    public void build (Build build) throws InvalidBuildingException {
        players.get(build.getPlayer_ind()).build(build.getWorker_ind(),board[build.getX()][build.getY()],build.isSetDome());
        setChanged();
        notifyObservers(board.clone());
    }

    /**
     * Metodo per settare la posizione iniziale di un worker
     * @param move
     * @throws InvalidMoveException
     */
    public void setWorker (Move move) throws InvalidMoveException {
        players.get(move.getPlayer_ind()).setInitialPosition(move.getWorker_ind(),board[move.getX()][move.getY()]);
        setChanged();
        notifyObservers(board.clone());
    }

    public Box[][] getBoard() {
        return board;
    }

    public Vector<Player> getPlayers() {
        return players;
    }

}
