package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.observer.Observable;
import it.polimi.ingsw.PSP54.utils.PlayerAction;
import it.polimi.ingsw.PSP54.utils.choices.*;
import it.polimi.ingsw.PSP54.utils.messages.BoardMessage;
import it.polimi.ingsw.PSP54.utils.messages.CardsMessage;
import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;


import java.io.Serializable;
import java.util.*;

/**
 * Class representing the state of a game.
 */
public class Game extends Observable<GameMessage> implements Serializable, Cloneable {

    public static final int APOLLO = 0, ARTEMIS = 1, ATHENA = 2, ATLAS = 3, DEMETER = 4, HEPHAESTUS = 5, MINOTAUR = 6, PROMETHEUS = 8;
    public static final int CARD_NUMBER = 5;
    public static final int BOARD_SIZE = 5;
    public static final String[] colors = {"blue", "red", "yellow"};
    private final Box[][] board;
    private final HashMap<Integer, String> cardMap = new HashMap<>();
    private final HashMap<Integer, String> extractedCards = new HashMap<>();
    private Vector<Player> players;
    private Player currentPlayer;

    public Game() {
        players = new Vector<>(1, 1);
        board = new Box[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new Box(i, j, 0, false);
            }
        }
        cardMap.put(APOLLO,"Apollo");
        cardMap.put(ARTEMIS,"Artemis");
        cardMap.put(ATHENA,"Athena");
        cardMap.put(ATLAS,"Atlas");
        cardMap.put(DEMETER,"Demeter");
        cardMap.put(HEPHAESTUS, "Hephaestus");
        cardMap.put(MINOTAUR, "Minotaur");
        cardMap.put(PROMETHEUS, "Prometheus");
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
     * Sets the color of the players' workers according to the order: first player is blue, second is red, third is yellow.
     */
    public void assignColors(){

        int numberOfPlayers = players.capacity();

        for (int i = 0; i < numberOfPlayers; i++) {
            players.get(i).setColor(colors[i]);
        }

    }

    /**
     *Extract an unique random god card for each player in the game.
     */
    public void extractCards() {

        int numberOfPlayers = players.size();
        ArrayList<Integer> deck = new ArrayList<>();

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
        if (!getExtractedCards().isEmpty()) {
            GameMessage cards = new CardsMessage(currentPlayer.getVirtualViewID(), getExtractedCards());
            notify(cards);
        }else {//all cards are assigned => worker placement
            notifyBoard();
            GameMessage setFirstWorker = new StringMessage(currentPlayer.getVirtualViewID(), StringMessage.setFirstWorkerMessage);
            notify(setFirstWorker);
        }
    }

    /**
     * Verifies if the assignment can be done and if so decorates the current player with the chosen power.
     * @param cardSelection the PlayerAction containing the chosen card.
     */
    public synchronized void performPowerAssignment(PlayerAction cardSelection) {
        if (getCurrentPlayer().getVirtualViewID() == cardSelection.getVirtualViewID()) {
            CardChoice cardChoice = (CardChoice) cardSelection.getChoice();
            GameMessage powerInfoMessage;
            int currentIndex = players.indexOf(currentPlayer);
            switch (cardChoice.getChoiceKey()) {
                case APOLLO:
                    players.set(currentIndex, currentPlayer.assignPower(APOLLO));
                    currentPlayer = players.get(currentIndex);
                    powerInfoMessage = new StringMessage(cardSelection.getVirtualViewID(), StringMessage.apolloMessage);
                    notify(powerInfoMessage);
                    break;
                case ARTEMIS:
                    players.set(currentIndex, currentPlayer.assignPower(ARTEMIS));
                    currentPlayer = players.get(currentIndex);
                    powerInfoMessage = new StringMessage(cardSelection.getVirtualViewID(), StringMessage.artemisMessage);
                    notify(powerInfoMessage);
                    break;
                case ATHENA:
                    players.set(currentIndex, currentPlayer.assignPower(ATHENA));
                    currentPlayer = players.get(currentIndex);
                    powerInfoMessage = new StringMessage(cardSelection.getVirtualViewID(), StringMessage.athenaMessage);
                    notify(powerInfoMessage);
                    break;
                case ATLAS:
                    players.set(currentIndex, currentPlayer.assignPower(ATLAS));
                    currentPlayer = players.get(currentIndex);
                    powerInfoMessage = new StringMessage(cardSelection.getVirtualViewID(), StringMessage.atlasMessage);
                    notify(powerInfoMessage);
                    break;
                case DEMETER:
                    players.set(currentIndex, currentPlayer.assignPower(DEMETER));
                    currentPlayer = players.get(currentIndex);
                    powerInfoMessage = new StringMessage(cardSelection.getVirtualViewID(), StringMessage.demeterMessage);
                    notify(powerInfoMessage);
                    break;
                case HEPHAESTUS:
                    players.set(currentIndex, currentPlayer.assignPower(HEPHAESTUS));
                    currentPlayer = players.get(currentIndex);
                    powerInfoMessage = new StringMessage(cardSelection.getVirtualViewID(), StringMessage.hephaestusMessage);
                    notify(powerInfoMessage);
                    break;
                case MINOTAUR:
                    players.set(currentIndex, currentPlayer.assignPower(MINOTAUR));
                    currentPlayer = players.get(currentIndex);
                    powerInfoMessage = new StringMessage(cardSelection.getVirtualViewID(), StringMessage.minotaurMessage);
                    notify(powerInfoMessage);
                    break;
                case PROMETHEUS:
                    players.set(currentIndex, currentPlayer.assignPower(PROMETHEUS));
                    currentPlayer = players.get(currentIndex);
                    powerInfoMessage = new StringMessage(cardSelection.getVirtualViewID(), StringMessage.prometheusMessage);
                    notify(powerInfoMessage);
                    break;
            }
            extractedCards.remove(((CardChoice) cardSelection.getChoice()).getChoiceKey());
            endTurn(currentPlayer);
        }else {
            GameMessage wrongTurn = new StringMessage(cardSelection.getVirtualViewID(), StringMessage.wrongTurnMessage);
            notify(wrongTurn);
        }
    }

    /**
     * Notifies the observers with a message accordingly with current worker's action tokens.
     * @param currentWorker the worker chosen by the player at the beginning of his turn.
     */
    public void checkTokens(Worker currentWorker) {
        if (currentWorker.getMoveToken() >= 1 && currentWorker.getBuildToken() == 0){
            GameMessage move = new StringMessage(currentPlayer.getVirtualViewID(), StringMessage.moveMessage);
            notify(move);
        }else if (currentWorker.getMoveToken() == 0 && currentWorker.getBuildToken() >= 1){
            GameMessage build = new StringMessage(currentPlayer.getVirtualViewID(), StringMessage.buildMessage);
            notify(build);
        }else if (currentWorker.getMoveToken() == 0 && currentWorker.getBuildToken() == 0)
            endTurn(currentPlayer);
    }

    /**
     * Verifies if the choice can be done and if so initializes worker's token and notifies with a corresponding message.
     * @param workerSelection the object representing the player's selection.
     */
    public void performWorkerChoice(PlayerAction workerSelection) {
        if (workerSelection.getVirtualViewID() == currentPlayer.getVirtualViewID()){
            WorkerChoice workerChoice = (WorkerChoice) workerSelection.getChoice();
            Worker currentWorker = currentPlayer.getWorker(workerChoice.isMale());
            currentPlayer.setCurrentWorker(currentWorker);
            if (currentWorker.getPos() != null) { //set tokens if is already settled
                currentPlayer.turnInit(workerChoice.isMale());
                checkTokens(currentWorker);
            }else {
                GameMessage move = new StringMessage(currentPlayer.getVirtualViewID(), StringMessage.moveMessage);
                notify(move);
            }
        }else{
            GameMessage wrongTurn = new StringMessage(workerSelection.getVirtualViewID(), StringMessage.wrongTurnMessage);
            notify(wrongTurn);
        }
    }

    /**
     * Verifies if the move can be done, if so performs it and notifies the observers with a corresponding message.
     * @param moveSelection the message representing the move action.
     */
    public void performMove(PlayerAction moveSelection) {
        if (moveSelection.getVirtualViewID() == currentPlayer.getVirtualViewID()) {
            MoveChoice moveChoice = (MoveChoice) moveSelection.getChoice();
            if (currentPlayer.getCurrentWorker().getPos() != null) { //actual move
                try {
                    currentPlayer.setWorkerBoxesToMove(currentPlayer.getCurrentWorker());
                    currentPlayer.move(currentPlayer.getCurrentWorker(), getBox(moveChoice.getX(), moveChoice.getY()));
                    checkTokens(currentPlayer.getCurrentWorker());
                }
                catch (InvalidMoveException e) { //retry
                    GameMessage invalidMove = new StringMessage(moveSelection.getVirtualViewID(), StringMessage.invalidMoveMessage);
                    notify(invalidMove);
                }
            }
            else { //first placement
                try {
                    currentPlayer.setWorkerPos(currentPlayer.getCurrentWorker(), moveChoice.getX(), moveChoice.getY());
                    notifyBoard();
                    if (currentPlayer.areWorkerSettled()) {
                        endTurn(currentPlayer);
                        if (currentPlayer.equals(players.firstElement())){ //notify actual move
                            GameMessage choseWorker = new StringMessage(currentPlayer.getVirtualViewID(), StringMessage.choseWorker);
                            notify(choseWorker);
                        }
                        else { //next player's first placement
                            GameMessage firstPlacement = new StringMessage(currentPlayer.getVirtualViewID(), StringMessage.setFirstWorkerMessage);
                            notify(firstPlacement);
                        }
                    }
                    else { //second placement
                        currentPlayer.nextCurrentWorker(currentPlayer.getCurrentWorker());
                        GameMessage secondPlacement = new StringMessage(currentPlayer.getVirtualViewID(), StringMessage.setSecondWorkerMessage);
                        notify(secondPlacement);
                    }
                }
                catch (InvalidMoveException e) { //retry
                    GameMessage invalidMove = new StringMessage(moveSelection.getVirtualViewID(), StringMessage.invalidMoveMessage);
                    notify(invalidMove);
                }
            }
        }
        else {
            GameMessage wrongTurn = new StringMessage(moveSelection.getVirtualViewID(), StringMessage.wrongTurnMessage);
            notify(wrongTurn);
        }
    }

    /**
     * Verifies if the move can be done, if so performs it and notifies the observers with a corresponding message.
     * @param buildSelection the object representing the build action.
     */
    public void performBuild(PlayerAction buildSelection){
        if (buildSelection.getVirtualViewID() == currentPlayer.getVirtualViewID()) {
            BuildChoice buildChoice = (BuildChoice) buildSelection.getChoice();
            try {
                currentPlayer.setWorkerBoxesToBuild(currentPlayer.getCurrentWorker());
                currentPlayer.build(currentPlayer.getCurrentWorker(), getBox(buildChoice.getX(), buildChoice.getY()));
                checkTokens(currentPlayer.getCurrentWorker());
                if (buildSelection.getVirtualViewID() != currentPlayer.getVirtualViewID()){
                    GameMessage choseWorker = new StringMessage(currentPlayer.getVirtualViewID(), StringMessage.choseWorker);
                    notify(choseWorker);
                }
            }
            catch (InvalidBuildingException e) { //retry
                GameMessage invalidBuilding = new StringMessage(buildSelection.getVirtualViewID(), StringMessage.invalidBuildingMessage);
                notify(invalidBuilding);
            }
        }else {
            GameMessage wrongTurn = new StringMessage(buildSelection.getVirtualViewID(), StringMessage.wrongTurnMessage);
            notify(wrongTurn);
        }
    }

    /**
     * Verifies if the choice can be done, if so performs it and notifies the observers with a corresponding message.
     * @param choiceAction action chosen by the player
     */
    public void performChoice(PlayerAction choiceAction) {
        if (choiceAction.getVirtualViewID() == currentPlayer.getVirtualViewID()){
            BooleanChoice choice = (BooleanChoice) choiceAction.getChoice();
            currentPlayer.chose(choice.isChoice());
            checkTokens(currentPlayer.getCurrentWorker());
            if (choiceAction.getVirtualViewID() != currentPlayer.getVirtualViewID()){
                GameMessage choseWorker = new StringMessage(currentPlayer.getVirtualViewID(), StringMessage.choseWorker);
                notify(choseWorker);
            }
        }else {
            GameMessage wrongTurn = new StringMessage(choiceAction.getVirtualViewID(), StringMessage.wrongTurnMessage);
            notify(wrongTurn);
        }
    }

    /**
     * Notifies observers with a BoardMessage object.
     */
    protected void notifyBoard() {
        GameMessage board = new BoardMessage(null, getBoard());
        notify(board);
    }

    /**
     * Set the nex player in the players Vector to current.
     * @param currentPlayer player who has just finished his turn.
     */
    public void endTurn(Player currentPlayer) {
        if(currentPlayer.isWinner()){
            GameMessage winnerMessage = new StringMessage(currentPlayer.getVirtualViewID(),StringMessage.winMessage);
            notify(winnerMessage);
            players.remove(currentPlayer);
            while (players.size() > 0) {
                GameMessage youLost = new StringMessage(players.get(0).getVirtualViewID(), "You lost because" + currentPlayer.getPlayerName() + "has win");
                notify(youLost);
                players.remove(0);
            }
        }
        if (currentPlayer.isLoser()){
            GameMessage losingMessage = new StringMessage(currentPlayer.getVirtualViewID(), StringMessage.loseMessage);
            notify(losingMessage);
            players.remove(currentPlayer);
            for (Player player : players) {
                GameMessage hasLost = new StringMessage(player.getVirtualViewID(), currentPlayer.getPlayerName() + "has lost.");
                notify(hasLost);
            }
        }
        currentPlayer.setPlaying(false);
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

}
