package it.polimi.ingsw.PSP54.model;

/**
 * Classe gestione del gioco
 */
public class Game {
    private int numberOfPlayers;
    private Board board;
    private Player[] player;

    /**
     * Costruttore della classe
     * istanzia i giocatori e la tabella
     * @param numberOfPlayers,name1,age1,color1,name2,age2,color2,name3,age3,color3
     * @throws IllegalNumberOfPlayersException
     */
    public Game (int numberOfPlayers,String name1, int age1, String color1, String name2, int age2, String color2, String name3, int age3, String color3)
            throws IllegalNumberOfPlayersException {
        if (numberOfPlayers != 2 && numberOfPlayers != 3){
            throw new IllegalNumberOfPlayersException();
        }
        this.numberOfPlayers = numberOfPlayers;
        this.player = new Player[numberOfPlayers];
        this.board = new Board();
        this.player[0] = new Player(name1,age1,color1);
        this.player[1] = new Player(name2,age2,color2);
        if (numberOfPlayers == 3) {
            this.player[2] = new Player(name3,age3,color3);
        }
    }

    /**
     * Metodo per capire se un giocatore ha vinto
     * @return nome del giocatore vincente
     */
    public String displayWinner() {
        for (int i = 0; i < numberOfPlayers; i++) {
            if (player[i].isWinner()) {
                return player[i].getPlayerName();
            }
        }
        return null;
    }

    /**
     * Metodo per capire il giocatore più giovane
     * @return indice del giocatore più giovane
     */
    public int youngestPlayer(){
        int min = player[0].age, ind = 0;
        for (int i = 0; i < numberOfPlayers; i++){
            if (player[i].age <= min){
                ind = i;
            }
        }
        return ind;
    }
}
