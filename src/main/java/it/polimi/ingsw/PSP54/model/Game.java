package it.polimi.ingsw.PSP54.model;

import java.util.*;

public class Game {

    public static final int APOLLO = 0, ARTEMIS = 1, ATHENA = 2, ATLAS = 3, DEMETER = 4;
    public static final String[] colors = {"blue", "red", "yellow"};
    public final int cardNumber = 5;
    public final int boardSize = 5;
    private Vector<Player> players;
    private final Box[][] board;

    public Game(){

        players = new Vector<>(2, 1);
        board = new Box[boardSize][boardSize];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new Box(i, j, 0, false);

            }
        }
    }

    /**
     * Adds a player to the players Vector
     * @param name the name of the player
     */
    public void newPlayer ( String name){

        Player player = new StandardPlayer(name);
        players.add(player);
    }

    /**
     *Sorts elements of players vector depending on players age
     * @param players the players vector
     */
    public void sortPlayers(Vector<Player> players){ //TODO: if player1.age == player2.age => alphabetic order?

        Comparator<Player> comp = new Comparator<Player>(){
            @Override
            public int compare(Player o1, Player o2) {
                int result = 0;
                if (o1.getAge() < o2.getAge())
                    result = -1;
                else if (o1.getAge() > o2.getAge())
                    result = 1;
                return result;
            }
        };

        Collections.sort(players, comp);
    }

    /**
     * Sets the color of the players' workers according to the order: first player is blue, second is red, third is yellow
     * @param players the players Vector
     */
    public void assignColors(Vector<Player> players){

        int numberOfPlayers = players.capacity();

        for (int i = 0; i < numberOfPlayers; i++) {
            players.get(i).setColor(colors[i]);
        }
    }

    /**
     *Extract an unique random god card for each player in the game
     * @return the array containing the extracted god cards
     */
    public int[] extractCards (){

        int numberOfPlayers = players.capacity();
        ArrayList<Integer> deck = new ArrayList<>();
        int [] selectedCards = new int[numberOfPlayers];

        for (int i = 0; i < cardNumber; i++) {
            deck.add(i);
        }

        Collections.shuffle(deck);

        for (int i = 0; i < selectedCards.length; i++) {
            selectedCards[i] = deck.get(i);
        }

        return selectedCards;
    }

    /**
     *Associates cards ID with the cards name
     * @param extractedCards the array containing extracted cards for the game
     * @return an array of String containing the names of extracted cards
     */
    public String [] nameExtractedCards (int[] extractedCards){
        String [] cardsNames = new String[extractedCards.length];

        for (int i = 0; i < extractedCards.length; i++) {
            if (extractedCards[i] == APOLLO) {
                cardsNames[i] = "Apollo";
            } else if (extractedCards[i] == ARTEMIS) {
                cardsNames[i] = "Artemis";
            } else if (extractedCards[i] == ATHENA) {
                cardsNames[i] = "Athena";
            } else if (extractedCards[i] == ATLAS) {
                cardsNames[i] = "Atlas";
            } else if (extractedCards[i] == DEMETER) {
                cardsNames[i] = "Demeter";
            }
        }

        return cardsNames;
    }

    //setters & getters

    public Vector<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Vector<Player> players) {
        this.players = players;
    }

    public Box[][] getBoard() {
        return board;
    }

    public Box getBox(int x, int y){
        return board[x][y];
    }

}
