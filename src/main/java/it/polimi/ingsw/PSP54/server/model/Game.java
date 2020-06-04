package it.polimi.ingsw.PSP54.server.model;

import it.polimi.ingsw.PSP54.observer.Observable;
import it.polimi.ingsw.PSP54.utils.PlayerAction;
import it.polimi.ingsw.PSP54.utils.choices.*;
import it.polimi.ingsw.PSP54.utils.messages.*;


import java.io.Serializable;
import java.util.*;

/**
 * Class representing the state of a game.
 */
public class Game extends Observable<GameMessage> implements Serializable, Cloneable {

    public static final int APOLLO = 0, ARTEMIS = 1, ATHENA = 2, ATLAS = 3, DEMETER = 4, HEPHAESTUS = 5, MINOTAUR = 6, PAN = 7, PROMETHEUS = 8;
    public static final int SIMPLE_GOD_CARD_NUMBER = 9;
    public static final int BOARD_SIZE = 5;
    public static final String[] colors = {"blue", "red", "yellow"};
    private final Box[][] board;
    private final HashMap<Integer, String> cardMap = new HashMap<>();
    private HashMap<Integer, String> extractedCards = new HashMap<>();
    private Vector<Player> players;
    private Player currentPlayer;
    private Player winner;

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
        cardMap.put(PAN, "Pan");
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

        int c = 0;
        for (Player player : players) {
            player.setColor(colors[c]);
            c++;
        }
    }

    /**
     * Translates the Players' Vector so that the Start Player is at index = 0.
     * @param index index of the Start Player before the method's call.
     */
    public void translatePlayersVector(int index) {
        Vector<Player> temp = new Vector<>(2, 1);

        for (int i = index; i < players.capacity(); i++)
            temp.add(players.get(i));

        for (int i = 0; i < index; i++)
            temp.add(players.get(i));

        setPlayers(temp);
    }

    /**
     * Sends via socket a message containing
     */
    public void sendDeck() {
        GameMessage deck = new DeckMessage(currentPlayer.getVirtualViewID(), cardMap);
        notify(deck);
    }

    /**
     * Show cards that can be chosen to current player.
     * If all cards are already taken moves on to the next game step.
     */
    public void displayAvailableCards() {
        if (!getExtractedCards().isEmpty()) {
            GameMessage cards = new AvailableCardsMessage(currentPlayer.getVirtualViewID(), getExtractedCards());
            notify(cards);
        }
    }

    /**
     * Verifies if the assignment can be done and if so decorates the current player with the chosen power.
     * @param cardSelection the PlayerAction containing the chosen card.
     */
    public synchronized void performPowerAssignment(PlayerAction cardSelection) {
        if (getCurrentPlayer().getVirtualViewID() == cardSelection.getVirtualViewID()) {
            PowerChoice cardChoice = (PowerChoice) cardSelection.getChoice();
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
                case PAN:
                    players.set(currentIndex, currentPlayer.assignPower(PAN));
                    currentPlayer = players.get(currentIndex);
                    powerInfoMessage = new StringMessage(cardSelection.getVirtualViewID(), StringMessage.panMessage);
                    notify(powerInfoMessage);
                    break;
                case PROMETHEUS:
                    players.set(currentIndex, currentPlayer.assignPower(PROMETHEUS));
                    currentPlayer = players.get(currentIndex);
                    powerInfoMessage = new StringMessage(cardSelection.getVirtualViewID(), StringMessage.prometheusMessage);
                    notify(powerInfoMessage);
                    break;
            }
            extractedCards.remove(((PowerChoice) cardSelection.getChoice()).getChoiceKey());
            if (players.indexOf(currentPlayer) != players.indexOf(players.firstElement()))
                endTurn(currentPlayer);
            else{
                GameMessage playersMessage = new PlayersMessage(currentPlayer.getVirtualViewID(), players);
                notify(playersMessage);
            }
        }else {
            GameMessage wrongTurn = new StringMessage(cardSelection.getVirtualViewID(), StringMessage.wrongTurnMessage);
            notify(wrongTurn);
        }
    }

    /**
     * Generates a Map associating the player's names and their corresponding powers.
     * @return the generated HashMap.
     */
    private HashMap<String,Integer> getCardsPlayersMap() {
        HashMap<String,Integer> cardsPlayersMap = new HashMap<>();
        for (Player p : players){
            String name = p.getPlayerName();
            Integer cardValue = p.getCardID();
            cardsPlayersMap.put(name,cardValue);
        }
        return cardsPlayersMap;
    }

    /**
     * Sends all the necessary messages to begin the actual game.
     * It is called after the Challenger's start player selection.
     */
    public void start() {
        notifyBoard();
        CardsPlayersMessage cardsPlayersMessage = new CardsPlayersMessage(null, getCardsPlayersMap());
        notify(cardsPlayersMessage);
        GameMessage setFirstWorker = new StringMessage(currentPlayer.getVirtualViewID(), StringMessage.setFirstWorkerMessage);
        notify(setFirstWorker);
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
            ArrayList<Box> valid = currentPlayer.setWorkerBoxesToBuild(currentWorker);
            GameMessage available = new AvailableBoxesMessage(currentPlayer.getVirtualViewID(), valid);
            notify(available);
            if(valid.isEmpty())
                performLosing(currentPlayer);
            else {
                GameMessage build = new StringMessage(currentPlayer.getVirtualViewID(), StringMessage.buildMessage);
                notify(build);
            }
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
                    ArrayList<Box> valid =currentPlayer.setWorkerBoxesToMove(currentPlayer.getCurrentWorker());
                    GameMessage available = new AvailableBoxesMessage(currentPlayer.getVirtualViewID(), valid);
                    notify(available);
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
        currentPlayer.setPlaying(false);
        int i = players.indexOf(currentPlayer);
        if (i == players.indexOf(players.lastElement())){
            setCurrentPlayer(players.get(0));
        } else {
            setCurrentPlayer(players.get(i + 1));
        }
    }

    /**
     * Notifies all the player with a message containing the name of the winner.
     * @param currentPlayer the player that has won.
     */
    public void notifyWinner(Player currentPlayer) {
        setWinner(currentPlayer);
        GameMessage winMessage = new WinMessage(null, currentPlayer);
        notify(winMessage);
    }

    /**
     * Handles the losing of a player by removing him from the game.
     * @param currentPlayer the player that has lost.
     */
    public void performLosing(Player currentPlayer) {
        if (players.size()==3){
            for (Player player : players) {
                if (Objects.equals(player, currentPlayer)) {
                    GameMessage loseMessage = new LoseMessage(currentPlayer.getVirtualViewID(), currentPlayer);
                    notify(loseMessage);
                } else {
                    GameMessage lose = new StringMessage(player.getVirtualViewID(), currentPlayer.getPlayerName() + StringMessage.loseMessage);
                    notify(lose);
                }
            }
            removePlayer(currentPlayer);
        }
        else if (players.size()==2) {
            players.remove(currentPlayer);
            players.add(0, currentPlayer);
            GameMessage winMessage = new WinMessage(null, players.lastElement());
            notify(winMessage);
        }
    }

    /**
     * Eliminates a player from the game by removing his workers from the board.
     * @param player the player to be removed.
     */
    public void removePlayer(Player player){
        GameMessage message = new DeleteMessage(
                null,
                player.getWorker(true).getPos().getX(),
                player.getWorker(true).getPos().getY(),
                player.getWorker(false).getPos().getX(),
                player.getWorker(false).getPos().getY(),
                player.getWorker(true).getPos().getLevel(),
                player.getWorker(false).getPos().getLevel());
        notify(message);
        player.getWorker(true).getPos().setWorker(null);
        player.getWorker(false).getPos().setWorker(null);
        int i=players.indexOf(player);
        players.remove(player);
        setCurrentPlayer(players.elementAt(i));
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
     * If the passed Player can play sets its playing attribute to 'true' and notifies the VirtualView.
     * @param currentPlayer the member of the players Vector which is going to play.
     */
    public void setCurrentPlayer(Player currentPlayer) {
        if (currentPlayer.getWorker(true).getPos() == null ||currentPlayer.getWorker(false) == null){
            currentPlayer.setPlaying(true);
            this.currentPlayer = currentPlayer;
            GameMessage yourTurn = new StringMessage(currentPlayer.getVirtualViewID(), StringMessage.turnMessage);
            notify(yourTurn);
        } else {
            ArrayList<Box> validMoveMale = currentPlayer.setWorkerBoxesToMove(currentPlayer.getWorker(true));
            ArrayList<Box> validMoveFemale = currentPlayer.setWorkerBoxesToMove(currentPlayer.getWorker(false));
            if (validMoveMale.isEmpty() && validMoveFemale.isEmpty())
                performLosing(currentPlayer);
            if (!validMoveMale.isEmpty() || !validMoveFemale.isEmpty()) {
                currentPlayer.setPlaying(true);
                this.currentPlayer = currentPlayer;
                GameMessage yourTurn = new StringMessage(currentPlayer.getVirtualViewID(), StringMessage.turnMessage);
                notify(yourTurn);
            }
        }
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

    /**
     * Sets the extracted cards and ends current player's turn.
     * @param extractedCards the Map associating the card's ids and their names.
     */
    public void setExtractedCards(HashMap<Integer, String> extractedCards) {
        this.extractedCards = extractedCards;
        endTurn(currentPlayer);
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}

