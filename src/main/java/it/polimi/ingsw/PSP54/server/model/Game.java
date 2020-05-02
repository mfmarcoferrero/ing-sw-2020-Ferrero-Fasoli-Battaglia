package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.observer.Observable;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.utils.Build;
import it.polimi.ingsw.PSP54.utils.Move;

import java.util.*;

public class Game extends Observable {

    public static final int APOLLO = 0, ARTEMIS = 1, ATHENA = 2, ATLAS = 3, DEMETER = 4;
    public static final String[] colors = {"blue", "red", "yellow"};
    public final int cardNumber = 5;
    public final int boardSize = 5;
    private Vector<Player> players;
    private final Box[][] board;
    private int[] extractedCards;

    public Game() {

        players = new Vector<>(1, 1);
        board = new Box[boardSize][boardSize];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new Box(i, j, 0, false);
            }
        }

    }

    public void newPlayer(String name){
        Player player = new StandardPlayer(name);
        players.add(player);
    }

    /**
     * Adds a player to the players Vector
     * @param name the name of the player
     */
    public void newPlayer (String name, int age, int virtualViewId) throws Exception {

        Player player = new StandardPlayer(name, age, virtualViewId);
        players.add(player);
        notify(board.clone());

    }

    /**
     * Sorts elements of players vector depending on players age
     */
    public void sortPlayers(){

        Comparator<Player> comp = (o1, o2) -> {
            int result = 0;
            if (o1.getAge() < o2.getAge())
                result = -1;
            else if (o1.getAge() > o2.getAge())
                result = 1;
            return result;
        };

        players.sort(comp);
    }

    /**
     * Sets the color of the players' workers according to the order: first player is blue, second is red, third is yellow
     */
    public void assignColors(){

        int numberOfPlayers = players.capacity();

        for (int i = 0; i < numberOfPlayers; i++) {
            players.get(i).setColor(colors[i]);
        }
    }

    /**
     *Extract an unique random god card for each player in the game
     */
    public void extractCards() {

        int numberOfPlayers = players.capacity();
        ArrayList<Integer> deck = new ArrayList<>();
        extractedCards = new int[numberOfPlayers];

        for (int i = 0; i < cardNumber; i++) {
            deck.add(i);
        }

        Collections.shuffle(deck);

        for (int i = 0; i < extractedCards.length; i++) {
            extractedCards[i] = deck.get(i);
        }

    }

    /**
     *Associates cards ID with the cards name
     * @return an array of String containing the names of extracted cards
     */
    public String [] nameExtractedCards() {
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

    /**
     * Creates a message for the player containing the extracted cards
     * @throws Exception ??
     */
    public void cardsSelection() throws Exception { //TODO notify only the correct virtual view with the updated message

        String[] cardsToDisplay = nameExtractedCards();
        String message = "Chose your card: ";
        StringBuilder cardNames = new StringBuilder();
        for (int i = 0; i < extractedCards.length; i++) {
            cardNames.append(i).append(". ").append(extractedCards[i]).append("\n");
        }
        message = message +cardNames;
        //send message with extracted cards to virtual view
        notify(message);
    }

    /**
     * Metodo per chiamare lo spostamento di un worker e restituire alla view la board che ha subito il cambiamento
     * @param move oggetto che contiene le informazioni per eseguire lo spostamento
     * @throws Exception
     */
    public void move(Move move) throws Exception {
        players.get(move.getPlayer_ind()).move(players.get(move.getPlayer_ind()).getWorkers()[move.getPlayer_ind()],board[move.getX()][move.getY()]);
        notify(board.clone());
    }

    /**
     * Metodo per chiamare la costruzione e restituire alla view la board che ha subito il cambiamento
     * @param build oggetto che contiene le informazioni per costruire
     * @throws Exception
     */
    public void build (Build build) throws Exception {
        players.get(build.getPlayer_ind()).build(players.get(build.getPlayer_ind()).getWorkers()[build.getPlayer_ind()],board[build.getX()][build.getY()]);
        notify(board.clone());
    }

    /**
     * Metodo per settare la posizione iniziale di un worker
     * @param move
     * @throws Exception
     */
    public void setWorker(Move move) throws Exception {
        players.get(move.getPlayer_ind()).setWorkerPos(players.get(move.getPlayer_ind()).getWorkers()[move.getPlayer_ind()], move.getX(), move.getY());
        notify(board.clone());
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

    public int[] getExtractedCards() {
        return extractedCards;
    }

    public void setExtractedCards(int[] extractedCards) {
        this.extractedCards = extractedCards;
    }
}
