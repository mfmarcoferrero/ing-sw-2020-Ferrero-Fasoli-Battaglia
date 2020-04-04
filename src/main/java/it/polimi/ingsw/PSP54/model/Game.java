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
    public Game (){

    }
    public void newPlayer(String name, int age){
       Player player= new Player(name, age);
       players.add(player);
       for (int i=0;i<2;i++){
           Worker worker=new Worker(player,null);
           player.worker[i]=worker;
       }
    }

    public void startGame(){
        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++){
                board[i][j]=new Box(i,j);
            }
        }
    }

    /**
     * Metodo per capire se un giocatore ha vinto
     * @return nome del giocatore vincente
     */
    public String displayWinner() {
        for (Player player : players) {
            if (player.isWinner()) {
                return player.getPlayerName();
            }
        }
        return null;
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

    public void ordinamentoPlayers(int val){
        players.add(0,players.get(val));
        players.remove(val+1);
    }

    public void setTurns() {
            for (int i = 0; i < players.size(); i++) {
                System.out.println("è il turno di:" + players.get(i).getPlayerName());
                players.get(i).myTurn();
                if (players.get(i).isWinner()) {
                    System.out.println(players.get(i).getPlayerName() + "è il vincitore");
                    break;
                }
                if (players.get(i).isLose()){
                    players.remove(i);
                    System.out.println("il giocatore:"+players.get(i).getPlayerName()+"è eliminato");
                }

            }
    }
}
