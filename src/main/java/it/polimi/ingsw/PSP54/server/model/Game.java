package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.observer.Observable;
import it.polimi.ingsw.PSP54.utils.*;


import java.io.Serializable;
import java.util.*;

public class Game extends Observable<Object> implements Serializable, Cloneable {

    public static final int APOLLO = 0, ARTEMIS = 1, ATHENA = 2, ATLAS = 3, DEMETER = 4;
    public static final int CARD_NUMBER = 5;
    public static final int BOARD_SIZE = 5;
    public static final String[] colors = {"blue", "red", "yellow"};
    private final Box[][] board;
    private HashMap<Integer, String> cardMap = new HashMap<>();
    private HashMap<Integer, String> extractedCards = new HashMap<>();
    private Vector<Player> players;
    private Player currentPlayer;
    private boolean powersSet;

    public Game() {

        players = new Vector<>(1, 1);
        board = new Box[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new Box(i, j, 0, false);
            }
        }
        powersSet = false;
        cardMap.put(APOLLO,"Apollo");
        cardMap.put(ARTEMIS,"Artemis");
        cardMap.put(ATHENA,"Athena");
        cardMap.put(ATLAS,"Atlas");
        cardMap.put(DEMETER,"Demeter");
    }

    /**
     * Adds a player to the players Vector.
     * @param name the player's username.
     */
    public void newPlayer(String name){
        Player player = new StandardPlayer(name);
        players.add(player);
        notify(board.clone());
    }

    /**
     * Adds a player to the players Vector.
     * @param name the player's username.
     * @param age the player's age.
     * @param virtualViewId the unique identifier for the VirtualView to which the player interfaces.
     */
    public void newPlayer (String name, int age, int virtualViewId) {
        Player player = new StandardPlayer(name, age, virtualViewId);
        player.setGame(this);
        players.add(player);
        notify(board.clone());
    }

    /**
     * Sorts elements of players vector depending on players age.
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
     * Sets the color of the players' workers according to the order: first player is blue,
     * second is red, third is yellow
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

        int numberOfPlayers = players.size();
        Vector<Integer> deck = new Vector<>();

        for (int i = 0; i < CARD_NUMBER; i++) {
            deck.add(i);
        }

        Collections.shuffle(deck);

        for (int i = 0; i < numberOfPlayers; i++) {
            extractedCards.put(deck.get(i),cardMap.get(deck.get(i)));
        }
    }


    synchronized public void chosePower(Choice choice) {
        GameMessage message = new GameMessage(choice.getVirtualViewID(),GameMessage.cantSelect);
        switch (choice.getChoiceInt()) {
            case APOLLO:
                currentPlayer.assignPower(APOLLO);
                message.setMessage(GameMessage.apolloMessage);
                break;
            case ARTEMIS:
                currentPlayer.assignPower(ARTEMIS);
                message.setMessage(GameMessage.artemisMessage);
                break;
            case ATHENA:
                currentPlayer.assignPower(ATHENA);
                message.setMessage(GameMessage.athenaMessage);
                break;
            case ATLAS:
                currentPlayer.assignPower(ATLAS);
                message.setMessage(GameMessage.atlasMessage);
                break;
            case DEMETER:
                currentPlayer.assignPower(DEMETER);
                message.setMessage(GameMessage.demeterMessage);
                break;
        }
        extractedCards.remove(choice.getChoiceInt());
        int index = players.indexOf(getCurrentPlayer());
        if (currentPlayer != players.lastElement()) {
            setCurrentPlayer(players.get(index + 1));
        } else {
            setPowersSet(true);
            setCurrentPlayer(players.get(0));
        }
        notify(message);
    }

    /**
     * Metodo per chiamare lo spostamento di un worker e restituire alla view la board che ha subito il cambiamento
     * @param move oggetto che contiene le informazioni per eseguire lo spostamento
     */
    public void move(Move move) throws InvalidMoveException {
        players.get(move.getPlayer_ind()).turnInit(move.isMale());
        players.get(move.getPlayer_ind()).move(players.get(move.getPlayer_ind()).getWorkers()[move.getWorker_ind()],board[move.getX()][move.getY()]);
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
    public void setWorker(Move move) throws InvalidMoveException {
        players.get(move.getPlayer_ind()).setWorkerPos(players.get(move.getPlayer_ind()).getWorkers()[move.getWorker_ind()], move.getX(), move.getY());
        notify(board.clone());
    }

    public boolean noWorkerSettled(Player player){

        return (player.getWorkers()[0].getPos() == null
                && player.getWorkers()[1].getPos() == null);

    }

    //setters & getters

    /**
     * Search for the currently playing member of the player's Vector.
     * @return the currently playing player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the playing attribute of currentPlayer to 'true' and notifies the VirtualView
     * @param currentPlayer the member of the players Vector which is going to play
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;

        for (Player player : players) {
            player.setPlaying(currentPlayer == player);
        }
        /*GameMessage yourTurn = new GameMessage(currentPlayer.getVirtualViewID(), GameMessage.turnMessage);
        notify(yourTurn);
         */
    }

    /**
     *
     * @param currentPlayer
     */
    public void endTurn(Player currentPlayer) {

        for (Player player : players){
            if (player.equals(currentPlayer)){
                int i = players.indexOf(player);
                if (i == players.indexOf(players.lastElement())){
                    setCurrentPlayer(players.get(0));
                }else
                    setCurrentPlayer(players.get(i+1));
            }
        }
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

    public Box getBox(int x, int y) {
        return board[x][y];
    }

    public HashMap<Integer, String> getExtractedCards() {
        return extractedCards;
    }

    public HashMap<Integer, String> getCardMap() {
        return cardMap;
    }

    public boolean isPowersSet() {
        return powersSet;
    }

    public void setPowersSet(boolean powersSet) {
        this.powersSet = powersSet;
    }
}
