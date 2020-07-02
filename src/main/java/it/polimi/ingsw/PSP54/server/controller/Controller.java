package it.polimi.ingsw.PSP54.server.controller;

import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.model.*;
import it.polimi.ingsw.PSP54.server.virtualView.VirtualView;
import it.polimi.ingsw.PSP54.utils.PlayerAction;
import it.polimi.ingsw.PSP54.utils.choices.*;

import java.util.ArrayList;
import java.util.HashMap;


public class Controller implements Observer<PlayerAction> {

    private final Game game;
    private static final ArrayList<VirtualView> virtualViewList = new ArrayList<>();
    private boolean gameEnded = false;

    public Controller (Game game) {
        this.game = game;
    }

    /**
     * Inserts a VirtualView object in the virtualViewList ArrayList.
     * @param virtualView the VirtualView to add.
     */
    public void addVirtualView (VirtualView virtualView) {
        virtualViewList.add(virtualView.getId(),virtualView);
    }

    /**
     * Inserts a new player in the game.
     * @param playerCredentials a message containing the player's credentials.
     */
    private void addPlayer (PlayerAction playerCredentials) {
        PlayerCredentials credentials = (PlayerCredentials) playerCredentials.getChoice();
        game.newPlayer(credentials.getPlayerName(), credentials.getAge(), playerCredentials.getVirtualViewID());
    }

    /**
     * Initializes the game by sorting the players and extracting a card for each one of them.
     */
    public void startGame() {
        game.sortPlayers();
        game.sendDeck();
    }

    /**
     * Performs the extraction of the cards the Challenger has selected.
     * @param extraction the PlayerAction containing the designed cards.
     */
    public void cardsExtraction(PlayerAction extraction) {
        HashMap<Integer, String> extractedCards = ((ExtractedCardsChoice) extraction.getChoice()).getExtractedCards();
        game.setExtractedCards(extractedCards);
        game.displayAvailableCards();
    }

    /**
     * Invokes model's methods to perform, if possible, the card's assignment.
     * @param action the object containing information regarding the assignment.
     */
    private void checkPowerAssignment(PlayerAction action) {
        game.performPowerAssignment(action);
        game.displayAvailableCards();
    }

    /**
     * Sets to current the player designed by the Challenger.
     * @param action a PlayerAction containing the index of the start player.
     */
    public void setStartPlayer(PlayerAction action) {
        game.translatePlayersVector(((StartPlayerChoice) action.getChoice()).getStartPlayerIndex());
        game.setCurrentPlayer(game.getPlayers().get(0));
        game.assignColors();
        game.start();
    }

    /**
     * Invokes model's method to perform, if possible, the worker's selection.
     * @param action the object containing information regarding the selection.
     */
    private void checkWorkerSelection(PlayerAction action){
        game.performWorkerChoice(action);
    }

    /**
     * Invokes model's method to perform, if possible, the move action.
     * @param action the object containing information regarding the move action.
     */
    private void checkMove(PlayerAction action) {
        game.performMove(action);
    }

    /**
     * Invokes model's method to perform, if possible, the build action.
     * @param action the object containing information regarding the build action.
     */
    private void checkBuild(PlayerAction action){
        game.performBuild(action);
    }

    /**
     * Invokes model's method to perform, if possible, the choice action.
     * @param action the object containing information regarding the player's choice.
     */
    private void checkChoice(PlayerAction action) {
        game.performChoice(action);
    }

    /**
     * Removes a VirtualView from the Game's ObserversList.
     * @param virtualViewID the VirtualView to remove.
     */
    public static void disableNotifications(Game game, int virtualViewID) {
        game.removeObserver(virtualViewList.get(virtualViewID));
    }

    /**
     * Called whenever the observed object is changed.
     * Calls different methods depending on the type of the PlayerChoice contained in the PlayerAction Message.
     * @param message an argument passed to the notify method.
     */
    @Override
    public void update(PlayerAction message) {
        if (!isGameEnded()) {
            PlayerChoice choice = message.getChoice();
            if (choice instanceof PlayerCredentials) {
                addPlayer(message);
            }
            if (choice instanceof ExtractedCardsChoice) {
                cardsExtraction(message);
            }
            if (choice instanceof PowerChoice) {
                checkPowerAssignment(message);
            }
            if (choice instanceof StartPlayerChoice) {
                setStartPlayer(message);
            }
            if (choice instanceof WorkerChoice) {
                checkWorkerSelection(message);
            }
            if (choice instanceof MoveChoice) {
                checkMove(message);
            }
            if (choice instanceof BuildChoice) {
                checkBuild(message);
            }
            if (choice instanceof BooleanChoice) {
                checkChoice(message);
            }
            if (choice instanceof EndGameChoice) {
                setGameEnded(true);
            }
        }
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }
}