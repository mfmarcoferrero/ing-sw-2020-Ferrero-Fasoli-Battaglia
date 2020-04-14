package it.polimi.ingsw.PSP54.model;

import java.util.Random;
import java.util.Vector;

/**
 * Classe gestione del gioco
 */
public class Game {
    protected Vector<Player> players = new Vector<>();
    protected Box[][] board = new Box[5][5];

    public Game(){
    }

    /**
     * Istanzia un nuovo giocatore
     * @param name
     * @param age
     * @param workerColour
     */
    public void newPlayer(String name, int age , String workerColour){
       Player player = new Player(name, age, workerColour,this);
       players.add(player);
       player.setPlayer_index(players.indexOf(player));
    }


    /**
     * Costruisce la board
     */
    public void startGame(){
        for (int i = 0;i < Box.BOARD_SIZE;i++) {
            for (int j = 0; j < Box.BOARD_SIZE; j++) {
                board[i][j] = new Box(i, j);
            }
        }

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
            p.setGodID(num);
            p.setPower();
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
     * @param val
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
        System.out.println("turno di:" + players.get(index).getPlayerName());
        players.get(index).buildToken = 1;
        players.get(index).moveToken = 1;
        System.out.println("hai" + players.get(index).moveToken + "mosse");
        System.out.println("puoi costruire" + players.get(index).buildToken + "volte");
    }

    public Box[][] getBoard() {
        return board;
    }

    public Vector<Player> getPlayers() {
        return players;
    }

}
