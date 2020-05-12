package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.observer.Observable;
import it.polimi.ingsw.PSP54.utils.PlayerAction;
import it.polimi.ingsw.PSP54.utils.choices.CardChoice;
import it.polimi.ingsw.PSP54.utils.messages.BoardMessage;
import it.polimi.ingsw.PSP54.utils.messages.CardsMessage;
import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;


import java.io.Serializable;
import java.util.*;

public class Game extends Observable<GameMessage> implements Serializable, Cloneable {

    public static final int APOLLO = 0, ARTEMIS = 1, ATHENA = 2, ATLAS = 3, DEMETER = 4;
    public static final int CARD_NUMBER = 5;
    public static final int BOARD_SIZE = 5;
    public static final String[] colors = {"blue", "red", "yellow"};
    private final Box[][] board;
    private HashMap<Integer, String> cardMap = new HashMap<>();
    private HashMap<Integer, String> extractedCards = new HashMap<>();
    private Vector<Player> players;
    private Player currentPlayer;
    private boolean powersAssigned;

    public Game() {

        players = new Vector<>(1, 1);
        board = new Box[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new Box(i, j, 0, false);
            }
        }
        powersAssigned = false;
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

    /**
     * Show cards that can be chosen to current player.
     * If all cards are already taken moves on to the next game step.
     */
    public synchronized void displayCards () {
        if (!arePowersAssigned()) {
            GameMessage cards = new CardsMessage(currentPlayer.getVirtualViewID(), getExtractedCards());
            notify(cards);
        }
        else {
            GameMessage board = new BoardMessage(null, getBoard().clone());
            notify(board);
            //TODO: notify first placement to first player
        }
    }

    /**
     * Verifies if the assignment can be done and if so decorates the current player with the chosen power.
     * @param selectedCard the PlayerAction containing the chosen card.
     */
    public synchronized void performPowerAssignment(PlayerAction selectedCard) {
        if (getCurrentPlayer().getVirtualViewID() == selectedCard.getVirtualViewID()) {
            if (!arePowersAssigned()) {
                CardChoice cardChoice = (CardChoice) selectedCard.getChoice();
                GameMessage powerInfoMessage;
                switch (cardChoice.getChoiceKey()) {
                    case APOLLO:
                        currentPlayer.assignPower(APOLLO);
                        powerInfoMessage = new StringMessage(selectedCard.getVirtualViewID(), StringMessage.apolloMessage);
                        notify(powerInfoMessage);
                        break;
                    case ARTEMIS:
                        currentPlayer.assignPower(ARTEMIS);
                        powerInfoMessage = new StringMessage(selectedCard.getVirtualViewID(), StringMessage.artemisMessage);
                        notify(powerInfoMessage);
                        break;
                    case ATHENA:
                        currentPlayer.assignPower(ATHENA);
                        powerInfoMessage = new StringMessage(selectedCard.getVirtualViewID(), StringMessage.athenaMessage);
                        notify(powerInfoMessage);
                        break;
                    case ATLAS:
                        currentPlayer.assignPower(ATLAS);
                        powerInfoMessage = new StringMessage(selectedCard.getVirtualViewID(), StringMessage.atlasMessage);
                        notify(powerInfoMessage);
                        break;
                    case DEMETER:
                        currentPlayer.assignPower(DEMETER);
                        powerInfoMessage = new StringMessage(selectedCard.getVirtualViewID(), StringMessage.demeterMessage);
                        notify(powerInfoMessage);
                        break;
                }
                extractedCards.remove(((CardChoice) selectedCard.getChoice()).getChoiceKey());
                if (currentPlayer == players.lastElement())
                    setPowersAssigned(true);

                endTurn(getCurrentPlayer());
            }else {
                GameMessage cantSelect = new StringMessage(selectedCard.getVirtualViewID(), StringMessage.cantSelect);
                notify(cantSelect);
            }
        }else {
            GameMessage wrongTurn = new StringMessage(selectedCard.getVirtualViewID(), StringMessage.wrongTurnMessage);
            notify(wrongTurn);
        }
    }

    /**
     * Determines whether a player has placed no workers
     * in order to inform the Controller of the message to be sent.
     * @param player the current player.
     * @return true if no worker has been placed, false otherwise.
     */
    /*public boolean noWorkerPlaced(Player player){

        return (player.getWorkers()[0].getPos() == null
                && player.getWorkers()[1].getPos() == null);
    }*/

    /**
     * Sets the initial worker's position on the board.
     * @param moveChoice the message containing information about the Box where the worker is going to be placed.
     */
    /*public void setWorker(MoveChoice moveChoice) throws InvalidMoveException {
        players.get(moveChoice.getPlayer_ind()).setWorkerPos(players.get(moveChoice.getPlayer_ind()).getWorkers()[moveChoice.getWorker_ind()], moveChoice.getX(), moveChoice.getY());
        notify(board.clone());
    }*/

    /**
     * Metodo per chiamare lo spostamento di un worker e restituire alla view la board che ha subito il cambiamento
     * @param moveChoice oggetto che contiene le informazioni per eseguire lo spostamento
     */
    /*public void move(MoveChoice moveChoice) throws InvalidMoveException {
        players.get(moveChoice.getPlayer_ind()).turnInit(moveChoice.isMale());
        players.get(moveChoice.getPlayer_ind()).move(players.get(moveChoice.getPlayer_ind()).getWorkers()[moveChoice.getWorker_ind()],board[moveChoice.getX()][moveChoice.getY()]);
        notify(board.clone());
    }*/

    /**
     * Metodo per chiamare la costruzione e restituire alla view la board che ha subito il cambiamento
     * @param build oggetto che contiene le informazioni per costruire
     */
    /*public void build (Build build) throws InvalidBuildingException {
        players.get(build.getPlayer_ind()).build(players.get(build.getPlayer_ind()).getWorkers()[build.getPlayer_ind()],board[build.getX()][build.getY()]);
        notify(board.clone());
    }*/

    /**
     * Set the nex player in the players Vector to current.
     * @param currentPlayer player who has just finished his turn.
     */
    public void endTurn(Player currentPlayer) {

        int i = players.indexOf(currentPlayer);
        if (i == players.indexOf(players.lastElement())){
            setCurrentPlayer(players.get(0));
        } else
            setCurrentPlayer(players.get(i+1));
    }

    //setters & getters

    public Vector<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Vector<Player> players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the playing attribute of currentPlayer to 'true' and notifies the VirtualView.
     * @param currentPlayer the member of the players Vector which is going to play.
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        currentPlayer.setPlaying(true);
        GameMessage yourTurn = new StringMessage(currentPlayer.getVirtualViewID(), StringMessage.turnMessage);
        notify(yourTurn);
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

    public boolean arePowersAssigned() {
        return powersAssigned;
    }

    public void setPowersAssigned(boolean powersAssigned) {
        this.powersAssigned = powersAssigned;
    }
}
