package it.polimi.ingsw.PSP54.server.controller;

import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.model.*;
import it.polimi.ingsw.PSP54.server.virtualView.VirtualView;
import it.polimi.ingsw.PSP54.utils.*;

import java.util.ArrayList;



public class Controller implements Observer {

    private final Game game;
    private ArrayList<VirtualView> virtualViewList = new ArrayList<>();


    public Controller (Game game) {
        this.game = game;
    }

    /**
     * Il controller può avere 2 o 3 virtualView collegate in bese al numero di giocatori per partita
     * Quindi viene utilizzato un vector
     * @param virtualView
     */
    public void addVirtualView (VirtualView virtualView) {
        this.virtualViewList.add(virtualView.getId(),virtualView);
    }

    public void startGame(){
        game.sortPlayers();
        game.assignColors();
        game.extractCards();
        game.displayCards();
    }

    /**
     * Remove already assigned cards from Model's extractedCards list
     * @param choice the message containing the card to remove
     */
    private synchronized void performCardChoice(CardChoice choice) {

        game.powerAssignment(choice);
    }

    /**
     * Metodo per effettuare una mossa
     * @param move
     */
    private synchronized void performMove(Move move){
        if(!game.getPlayers().get(move.getPlayer_ind()).isPlaying()){
            virtualViewList.get(move.getVirtualViewId()).showMessage(GameMessage.wrongTurnMessage);
            return;
        }
        try{
            if (move.isSetFirstPos()){
                    game.setWorker(move);
                    virtualViewList.get(move.getVirtualViewId()).showMessage(GameMessage.setSecondWorkerMessage);
            }
            else
                game.move(move);
        } catch (InvalidMoveException e) {
            virtualViewList.get(move.getVirtualViewId()).showMessage(GameMessage.invalidMoveMessage);
        }
    }

    /**
     * Metodo per effettuare una costruzione
     * @param build
     */
    private synchronized void performBuild(Build build){
        if(!game.getPlayers().get(build.getPlayer_ind()).isPlaying()){
            virtualViewList.get(build.getVirtualViewId()).showMessage(GameMessage.wrongTurnMessage);
            return;
        }
        try{
            game.build(build);
        } catch (InvalidBuildingException e) {
            virtualViewList.get(build.getVirtualViewId()).showMessage(GameMessage.invalidBuildingMessage);
        }
    }

    /**
     * Metodo per inserire un giocatore nel model
     * @param p
     */
    private synchronized void addPlayer (PlayerMessage p) {

        game.newPlayer(p.getPlayerName(), p.getAge(), p.getVirtualViewID());

    }



    @Override
    public void update(Move message){
        performMove(message);
    }

    @Override
    public void update(Build message){
        performBuild(message);
    }

    @Override
    public void update(PlayerMessage message){
        addPlayer(message);
    }

    @Override
    public void update(String message){

    }

    @Override
    public void update(CardChoice message){
        performCardChoice(message);
    }

    @Override
    public void update(Box[][] message) {

    }

    @Override
    public void update(StringToDisplay message) {

    }

    @Override
    public void update(GameMessage message) {

    }
}
