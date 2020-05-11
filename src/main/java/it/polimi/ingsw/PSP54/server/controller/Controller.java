package it.polimi.ingsw.PSP54.server.controller;

import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.model.*;
import it.polimi.ingsw.PSP54.server.virtualView.VirtualView;
import it.polimi.ingsw.PSP54.utils.PlayerAction;
import it.polimi.ingsw.PSP54.utils.choices.CardChoice;
import it.polimi.ingsw.PSP54.utils.choices.MoveChoice;
import it.polimi.ingsw.PSP54.utils.choices.PlayerCredentials;
import it.polimi.ingsw.PSP54.utils.messages.GameMessage;

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
     *
     */
    public void startGame() {
        game.sortPlayers();
        game.assignColors();
        game.extractCards();
        displayCards();
    }

    /**
     * Show cards that can be chosen to current player.
     */
    private void displayCards () {
        if (!game.isPowersSet()) {
            CardsToDisplay cards = new CardsToDisplay();
            cards.setExtractedCards(game.getExtractedCards());
            virtualViewList.get(game.getCurrentPlayer().getVirtualViewID()).showMessage(cards);
        }
        else {
            for (VirtualView v : virtualViewList) {
                v.showBoard();
                if (v.getId() == game.getCurrentPlayer().getVirtualViewID()) {
                    v.showMessage(GameMessage.setFirstWorkerMessage);
                }
            }
        }
    }

    /**
     * Invokes model's methods to perform, if possible, the card's assignment.
     * @param cardChoice the message containing informations regarding the assignment.
     */
    private void performCardChoice(CardChoice cardChoice) {
        if (game.getCurrentPlayer().getVirtualViewID() == cardChoice.getVirtualViewID()) {
            game.chosePower(cardChoice);
            displayCards();
        } else
            virtualViewList.get(cardChoice.getVirtualViewID()).showMessage(GameMessage.wrongTurnMessage);
    }

    /**
     *Invokes model's methods to perform, if possible, the settlement of the workers.
     * @param moveChoice the message containing informations regarding which worker and where is going to be settled.
     */
    private void performWorkerSet(MoveChoice moveChoice){

        if (game.getCurrentPlayer().getVirtualViewID() == moveChoice.getVirtualViewId()) {
            moveChoice.setPlayer_ind(game.getPlayers().indexOf(game.getCurrentPlayer()));//fills the message informations
            try {
                game.setWorker(moveChoice); //perform actual placement
                showAllBoards();

                if (game.getCurrentPlayer().areWorkerSettled()) { //check current player turn status
                    if (game.getCurrentPlayer() == game.getPlayers().lastElement()){ //check game turns status

                        game.endTurn(game.getCurrentPlayer()); //players.next()
                        virtualViewList.get(game.getCurrentPlayer().getVirtualViewID()).showMessage(GameMessage.moveMessage);

                    } else { //send first placement message to next player
                        game.endTurn(game.getCurrentPlayer());
                        virtualViewList.get(game.getCurrentPlayer().getVirtualViewID()).showMessage(GameMessage.setFirstWorkerMessage);
                    }
                } else
                    virtualViewList.get(moveChoice.getVirtualViewId()).showMessage(GameMessage.setSecondWorkerMessage);

            } catch (InvalidMoveException e) {//redo
                virtualViewList.get(moveChoice.getVirtualViewId()).showMessage(GameMessage.wrongPlacementMessage);
                if (game.noWorkerPlaced(game.getCurrentPlayer())){
                    virtualViewList.get(moveChoice.getVirtualViewId()).showMessage(GameMessage.setFirstWorkerMessage);
                }
                virtualViewList.get(moveChoice.getVirtualViewId()).showMessage(GameMessage.setSecondWorkerMessage);
            }
        } else //wrong turn
            virtualViewList.get(moveChoice.getVirtualViewId()).showMessage(GameMessage.wrongTurnMessage);
    }

    /**
     * Metodo per effettuare una mossa
     * @param moveChoice
     */
     private void performMove(MoveChoice moveChoice){
         if (game.getCurrentPlayer().getVirtualViewID() == moveChoice.getVirtualViewId()) {
             moveChoice.setPlayer_ind(game.getPlayers().indexOf(game.getCurrentPlayer()));
             try {
                 game.move(moveChoice); //perform actual move
                 showAllBoards();
                 virtualViewList.get(moveChoice.getVirtualViewId()).showMessage(GameMessage.buildMessage);
             } catch (InvalidMoveException e) {
                 virtualViewList.get(moveChoice.getVirtualViewId()).showMessage(GameMessage.invalidMoveMessage);
             }
         } else
             virtualViewList.get(moveChoice.getVirtualViewId()).showMessage(GameMessage.wrongTurnMessage);
    }

    /**
     * Metodo per effettuare una costruzione
     * @param build
     */
    private void performBuild(Build build){
        if (game.getCurrentPlayer().getVirtualViewID() == build.getVirtualViewId()) {
            build.setPlayer_ind(game.getPlayers().indexOf(game.getCurrentPlayer()));
            try{
                game.build(build);
                showAllBoards(); //check current player turn status
                game.endTurn(game.getCurrentPlayer());
                virtualViewList.get(game.getCurrentPlayer().getVirtualViewID()).showMessage(GameMessage.moveMessage);
            } catch (InvalidBuildingException e) {
                virtualViewList.get(build.getVirtualViewId()).showMessage(GameMessage.invalidBuildingMessage);
            }
        } else
            virtualViewList.get(build.getVirtualViewId()).showMessage(GameMessage.wrongTurnMessage);
    }


    /**
     * Metodo per inserire un giocatore nel model
     * @param p
     */
    private void addPlayer (PlayerCredentials p) {
        game.newPlayer(p.getPlayerName(), p.getAge(), p.getVirtualViewID());
    }


    private void showAllBoards() {
        synchronized (this) {
            for (VirtualView v : virtualViewList) {
                v.showBoard();
            }
        }
    }

    /**
     * Called whenever the observed object is changed.
     *
     * @param message an argument passed to the notify method.
     */
    @Override
    public void update(PlayerAction message) {

    }
}
