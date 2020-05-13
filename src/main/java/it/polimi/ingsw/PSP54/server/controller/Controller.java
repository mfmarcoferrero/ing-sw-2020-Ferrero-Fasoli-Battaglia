package it.polimi.ingsw.PSP54.server.controller;

import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.model.*;
import it.polimi.ingsw.PSP54.server.virtualView.VirtualView;
import it.polimi.ingsw.PSP54.utils.PlayerAction;
import it.polimi.ingsw.PSP54.utils.choices.CardChoice;
import it.polimi.ingsw.PSP54.utils.choices.PlayerChoice;
import it.polimi.ingsw.PSP54.utils.choices.PlayerCredentials;
import it.polimi.ingsw.PSP54.utils.choices.WorkerChoice;

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
     * @param selectedCard the message containing informations regarding the assignment.
     */
    private void invokePowerAssignment(PlayerAction selectedCard) {
        game.performPowerAssignment(selectedCard);
        game.displayCards();
    }

    private void invokeWorkerChoice(PlayerAction choice){
        game.performWorkerChoice(choice);
    }

    /**
     *Invokes model's methods to perform, if possible, the settlement of the workers.
     * @param moveChoice the message containing informations regarding which worker and where is going to be settled.
     */
    /*private void performWorkerSet(MoveChoice moveChoice){

        if (game.getCurrentPlayer().getVirtualViewID() == moveChoice.getVirtualViewId()) {
            moveChoice.setPlayer_ind(game.getPlayers().indexOf(game.getCurrentPlayer()));//fills the message informations
            try {
                game.setWorker(moveChoice); //perform actual placement
                showAllBoards();

                if (game.getCurrentPlayer().areWorkerSettled()) { //check current player turn status
                    if (game.getCurrentPlayer() == game.getPlayers().lastElement()){ //check game turns status

                        game.endTurn(game.getCurrentPlayer()); //players.next()
                        virtualViewList.get(game.getCurrentPlayer().getVirtualViewID()).sendMessage(GameMessage.moveMessage);

                    } else { //send first placement message to next player
                        game.endTurn(game.getCurrentPlayer());
                        virtualViewList.get(game.getCurrentPlayer().getVirtualViewID()).sendMessage(GameMessage.setFirstWorkerMessage);
                    }
                } else
                    virtualViewList.get(moveChoice.getVirtualViewId()).sendMessage(GameMessage.setSecondWorkerMessage);

            } catch (InvalidMoveException e) {//redo
                virtualViewList.get(moveChoice.getVirtualViewId()).sendMessage(GameMessage.wrongPlacementMessage);
                if (game.noWorkerPlaced(game.getCurrentPlayer())){
                    virtualViewList.get(moveChoice.getVirtualViewId()).sendMessage(GameMessage.setFirstWorkerMessage);
                }
                virtualViewList.get(moveChoice.getVirtualViewId()).sendMessage(GameMessage.setSecondWorkerMessage);
            }
        } else //wrong turn
            virtualViewList.get(moveChoice.getVirtualViewId()).sendMessage(GameMessage.wrongTurnMessage);
    }*/

    /**
     * Metodo per effettuare una mossa
     * @param moveChoice
     */
     /*private void performMove(MoveChoice moveChoice){
         if (game.getCurrentPlayer().getVirtualViewID() == moveChoice.getVirtualViewId()) {
             moveChoice.setPlayer_ind(game.getPlayers().indexOf(game.getCurrentPlayer()));
             try {
                 game.move(moveChoice); //perform actual move
                 showAllBoards();
                 virtualViewList.get(moveChoice.getVirtualViewId()).sendMessage(GameMessage.buildMessage);
             } catch (InvalidMoveException e) {
                 virtualViewList.get(moveChoice.getVirtualViewId()).sendMessage(GameMessage.invalidMoveMessage);
             }
         } else
             virtualViewList.get(moveChoice.getVirtualViewId()).sendMessage(GameMessage.wrongTurnMessage);
    }*/

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
     * Inserts a new player in the game.
     * @param playerCredentials a message containing the player's credentials.
     */
    private void addPlayer (PlayerAction playerCredentials) {
        PlayerCredentials credentials = (PlayerCredentials) playerCredentials.getChoice();
        game.newPlayer(credentials.getPlayerName(), credentials.getAge(), playerCredentials.getVirtualViewID());
    }

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
            invokePowerAssignment(message);
        }
        if (choice instanceof WorkerChoice){
            invokeWorkerChoice(message);
        }
    }
}
