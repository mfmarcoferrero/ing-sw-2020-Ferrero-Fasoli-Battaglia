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
       player.setPlayer_index(players.indexOf(player));
       for (int i=0;i<2;i++){
           Worker worker=new Worker(player,null);
           if(i==0)
               worker.setMale(true);
           else
               worker.setMale(false);
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
        players.get(0).setPlayer_index(0);
        players.remove(val+1);
        for (int i=0;i<players.size();i++)
            players.get(i).setPlayer_index(i);
    }

    public void eliminaPerdente(int index){
        players.remove(index);
    }

    public void setTurns(int index) {
        System.out.println("turno di:"+players.get(index).getPlayerName());
        players.get(index).myTurn();
    }
}
