package it.polimi.ingsw.PSP54.server.controller;

import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.model.*;
import it.polimi.ingsw.PSP54.server.virtualView.VirtualView;
import it.polimi.ingsw.PSP54.utils.PlayerAction;
import it.polimi.ingsw.PSP54.utils.choices.*;

import java.util.ArrayList;


public class Controller implements Observer<PlayerAction> {

    private final Game game;
    private ArrayList<VirtualView> virtualViewList = new ArrayList<>();

    public Controller (Game game) {
        this.game = game;
    }

    /**
     * Inserts a VirtualView object in the virtualViewList ArrayList.
     * @param virtualView the VirtualView to add.
     */
    public void addVirtualView (VirtualView virtualView) {
        this.virtualViewList.add(virtualView.getId(),virtualView);
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
        game.assignColors();
        game.extractCards();
        game.displayCards();
    }

    /**
     * Invokes model's methods to perform, if possible, the card's assignment.
     * @param action the message containing informations regarding the assignment.
     */
    private void checkPowerAssignment(PlayerAction action) {
        game.performPowerAssignment(action);
        game.displayCards();
    }

    /**
     *
     * @param action
     */
    private void checkWorkerSelection(PlayerAction action){
        game.performWorkerChoice(action);
    }

    /**
     *
     * @param action
     */
    private void checkMove(PlayerAction action) {
        game.performMove(action);
    }

    /**
     *
     * @param action
     */
    private void checkBuild(PlayerAction action){
        game.performBuild(action);
    }

    /**
     * Metodo per effettuare una costruzione
     * @param build
     */
    /*private void performBuild(Build build){
        if (game.getCurrentPlayer().getVirtualViewID() == build.getVirtualViewId()) {
            build.setPlayer_ind(game.getPlayers().indexOf(game.getCurrentPlayer()));
            try{
                game.build(build);
                showAllBoards(); //check current player turn status
                game.endTurn(game.getCurrentPlayer());
                virtualViewList.get(game.getCurrentPlayer().getVirtualViewID()).sendMessage(GameMessage.moveMessage);
            } catch (InvalidBuildingException e) {
                virtualViewList.get(build.getVirtualViewId()).sendMessage(GameMessage.invalidBuildingMessage);
            }
        } else
            virtualViewList.get(build.getVirtualViewId()).sendMessage(GameMessage.wrongTurnMessage);
    }*/

    /**
     * Called whenever the observed object is changed.
     *
     * @param message an argument passed to the notify method.
     */
    @Override
    public void update(PlayerAction message) {
        PlayerChoice choice = message.getChoice();
        if (choice instanceof PlayerCredentials){
            addPlayer(message);
        }
        if (choice instanceof CardChoice){
            checkPowerAssignment(message);
        }
        if (choice instanceof WorkerChoice){
            checkWorkerSelection(message);
        }
        if (choice instanceof MoveChoice){
            checkMove(message);
        }
        if (choice instanceof BuildChoice){
            checkBuild(message);
        }
    }
}
