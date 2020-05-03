package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.observer.Observable;
import it.polimi.ingsw.PSP54.utils.*;


import java.util.*;

public class Game extends Observable {

    public static final int APOLLO = 0, ARTEMIS = 1, ATHENA = 2, ATLAS = 3, DEMETER = 4;
    private HashMap<Integer, String> cardMap = new HashMap<>();
    public static final String[] colors = {"blue", "red", "yellow"};
    public final int cardNumber = 5;
    public final int boardSize = 5;
    private Vector<Player> players;
    private Player currentPlayer;
    private final Box[][] board;
    private ArrayList<Integer> extractedCards;

    public Game() {

        players = new Vector<>(1, 1);
        board = new Box[boardSize][boardSize];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new Box(i, j, 0, false);
            }
        }
        cardMap.put(APOLLO, "Apollo");
        cardMap.put(ARTEMIS, "Artemis");
        cardMap.put(ATHENA, "Athena");
        cardMap.put(ATLAS, "Atlas");
        cardMap.put(DEMETER, "Demeter");
    }

    public void newPlayer(String name){
        Player player = new StandardPlayer(name);
        players.add(player);
    }

    /**
     * Adds a player to the players Vector
     * @param name the name of the player
     */
    public void newPlayer (String name, int age, int virtualViewId) {

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
        setCurrentPlayer(players.get(0));
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
        extractedCards = new ArrayList<>();

        for (int i = 0; i < cardNumber; i++) {
            deck.add(i);
        }

        Collections.shuffle(deck);

        for (int i = 0; i < numberOfPlayers; i++) {
            extractedCards.add(deck.get(i));
        }

    }

    /**
     * Creates a message for the player containing the extracted cards
     */
    public void displayCards(){

        String message = "Chose your card:\n";
        StringBuilder cardNames = new StringBuilder();

        for (int i = 0; i < extractedCards.size(); i++) {
            cardNames.append(i+1).append(". ").append(cardMap.get(getExtractedCards().get(i))).append("\n");
        }

        message = message + cardNames;

        CardDisplayed cardDisplayed = new CardDisplayed(currentPlayer.getVirtualViewID(), message);
        notify(cardDisplayed); //TODO: fix not displayed when currentPLayer is players.get(2)
    }

    /**
     * Invoke the player decoration and remove the card from extractedCards ArrayList.
     * @param cardIndex the index of the chosen card in the extractedCards ArrayList.
     */
    public void powerAssignment(int cardIndex){

        //assign the power and notify the player
        currentPlayer.assignPower(getExtractedCards().get(cardIndex));
        GameMessage message = new GameMessage(currentPlayer.getVirtualViewID(), null);

        switch (getExtractedCards().get(cardIndex)) {
            case APOLLO:
                message.setMessage(GameMessage.apolloMessage);
                notify(message);
                break;
            case ARTEMIS:
                message.setMessage(GameMessage.artemisMessage);
                notify(message);
                break;
            case ATHENA:
                message.setMessage(GameMessage.athenaMessage);
                notify(message);
                break;
            case ATLAS:
                message.setMessage(GameMessage.atlasMessage);
                notify(message);
                break;
            case DEMETER:
                message.setMessage(GameMessage.demeterMessage);
                notify(message);
                break;
        }

        //remove the already taken card
        getExtractedCards().remove(cardIndex);

        //end the current player's turn
        int i = players.indexOf(getCurrentPlayer());
        if (i<players.indexOf(players.lastElement())) {
            setCurrentPlayer(players.get(i + 1));
            displayCards();
        }else
            setCurrentPlayer(players.get(0));

    }

    //TODO: maybe handle InvalidMove/BuildingException here?

    /**
     * Metodo per chiamare lo spostamento di un worker e restituire alla view la board che ha subito il cambiamento
     * @param move oggetto che contiene le informazioni per eseguire lo spostamento
     */
    public void move(Move move) throws InvalidMoveException {
        players.get(move.getPlayer_ind()).move(players.get(move.getPlayer_ind()).getWorkers()[move.getPlayer_ind()],board[move.getX()][move.getY()]);
        notify(board.clone());
    }

    /**
     * Metodo per chiamare la costruzione e restituire alla view la board che ha subito il cambiamento
     * @param build oggetto che contiene le informazioni per costruire
     */
    public void build (Build build) throws InvalidBuildingException {
        players.get(build.getPlayer_ind()).build(players.get(build.getPlayer_ind()).getWorkers()[build.getPlayer_ind()],board[build.getX()][build.getY()]);
        notify(board.clone());
    }

    /**
     * Sets the initial worker's position on the board.
     * @param move the message containing information about the Box where the worker is going to be placed.
     */
    public void setWorker(Move move){
        players.get(move.getPlayer_ind()).setWorkerPos(players.get(move.getPlayer_ind()).getWorkers()[move.getPlayer_ind()], move.getX(), move.getY());
        notify(board.clone());
    }

    //setters & getters

    /**
     * Search for the currently playing member of the player's Vector.
     * @return the currently playing player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {

        for (Player player : players) {
            if (currentPlayer.equals(player))
                player.setPlaying(true);
        }

        this.currentPlayer = currentPlayer;
        GameMessage yourTurn = new GameMessage(currentPlayer.getVirtualViewID(), GameMessage.turnMessage);
        notify(yourTurn);
    }

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

    public ArrayList<Integer> getExtractedCards() {
        return extractedCards;
    }

    public void setExtractedCards(ArrayList<Integer> extractedCards) {
        this.extractedCards = extractedCards;
    }

    public HashMap<Integer, String> getCardMap() {
        return cardMap;
    }

    public void setCardMap(HashMap<Integer, String> cardMap) {
        this.cardMap = cardMap;
    }
}
