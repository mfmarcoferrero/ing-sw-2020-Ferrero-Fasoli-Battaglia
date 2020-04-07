package it.polimi.ingsw.PSP54.model;

import java.util.Vector;

/**
 * Classe gestione del gioco
 */
public class Game {
    Vector<Player> players = new Vector<>();
    Box[][] board = new Box[5][5];

    /**
     * Costruttore della classe
     */
    public Game () {
    }

    /**
     * Istanzia un nuovo giocatore
     * @param name
     * @param age
     * @param workerColour
     */
    public void newPlayer(String name, int age , String workerColour){
       Player player = new Player(name, age, workerColour);
       players.add(player);
       player.setPlayer_index(players.indexOf(player));
    }

    /**
     * Costruisce la board
     * @throws InvalidBoxException se le coordinate della box non sono valide
     */
    public void startGame() throws InvalidBoxException {
        for (int i = 0;i < Box.BOARD_SIZE;i++) {
            for (int j = 0; j < Box.BOARD_SIZE; j++) {
                board[i][j] = new Box(i, j);
            }
        }
    }

    /**
     * Metodo per capire il giocatore più giovane
     * @return indice del giocatore più giovane
     */
    public int youngestPlayer(){
        int min = players.get(0).age, ind=0;
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).age <= min){
                ind = i;
            }
        }
        return ind;
    }

    public void ordinamentoPlayers (int val){
        players.add(0,players.get(val));
        players.get(0).setPlayer_index(0);
        players.remove(val+1);
        for (int i=0;i<players.size();i++)
            players.get(i).setPlayer_index(i);
    }

    public void setTurns(int index) {
        System.out.println("turno di:" + players.get(index).getPlayerName());
        players.get(index).buildtoken=1;
        players.get(index).movetoken=1;
        System.out.println("hai"+players.get(index).movetoken+"mosse");
        System.out.println("puoi costruire"+players.get(index).buildtoken+"volte");
    }

    public Box[][] getBoard() {
        return board;
    }
}
