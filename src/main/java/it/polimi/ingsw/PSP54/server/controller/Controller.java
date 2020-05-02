package it.polimi.ingsw.PSP54.server.controller;

import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.model.*;
import it.polimi.ingsw.PSP54.server.virtualView.VirtualView;
import it.polimi.ingsw.PSP54.utils.*;

import java.util.ArrayList;



public class Controller implements Observer {

    private Game game;
    private ArrayList<VirtualView> virtualViewList = new ArrayList<>();

    public Controller (Game game) {
        this.game = game;

        game.sortPlayers();
        game.assignColors();
        game.extractCards();
        game.getPlayers().get(0).firstTurnInit();


    }

    /**
     * Il controller può avere 2 o 3 virtualView collegate in bese al numero di giocatori per partita
     * Quindi viene utilizzato un vector
     * @param virtualView
     */
    public void addVirtualView (VirtualView virtualView) {
        this.virtualViewList.add(virtualView.getVirtualViewId(),virtualView);
    }

    /**
     * Metodo per effettuare una mossa
     * @param move
     */
    private synchronized void performMove(Move move) throws Exception {
        if(!game.getPlayers().get(move.getPlayer_ind()).isPlaying()){
            virtualViewList.get(move.getVirtualViewId()).showMessage(GameMessage.wrongTurnMessage);
            return;
        }
        try{
            if (move.isSetFirstPos()){
                try {
                    game.setWorker(move);
                    virtualViewList.get(move.getVirtualViewId()).showMessage(GameMessage.setSecondWorkerMessage);
                }
                catch (InvalidMoveException e){
                    virtualViewList.get(move.getVirtualViewId()).showMessage(GameMessage.invalidMoveMessage);
                    return;
                }
            }
            else
                game.move(move);
        } catch (InvalidMoveException e) {
            virtualViewList.get(move.getVirtualViewId()).showMessage(GameMessage.invalidMoveMessage);
            return;
        }
    }

    /**
     * Metodo per effettuare una costruzione
     * @param build
     */
    private synchronized void performBuild(Build build) throws Exception{
        if(!game.getPlayers().get(build.getPlayer_ind()).isPlaying()){
            virtualViewList.get(build.getVirtualViewId()).showMessage(GameMessage.wrongTurnMessage);
            return;
        }
        try{
            game.build(build);
        } catch (InvalidBuildingException e) {
            virtualViewList.get(build.getVirtualViewId()).showMessage(GameMessage.invalidBuildingMessage);
            return;
        }
    }

    private synchronized void handleTurnEnd() {
        //TODO
    }

    /**
     * Metodo per inserire un giocatore nel model
     * @param p
     * @throws Exception
     */
    private synchronized void addPlayer (PlayerMessage p) throws Exception {
        try {
            game.newPlayer(p.getPlayerName(), p.getAge(), p.getVirtualViewID());
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void update(Move message) throws Exception {
        performMove(message);
    }

    @Override
    public void update(Build message) throws Exception {
        performBuild(message);
    }

    @Override
    public void update(PlayerMessage message) throws Exception {
        addPlayer(message);
    }

    @Override
    public void update(String message) throws Exception {
        return;
    }

    @Override
    public void update(Box[][] message) throws Exception {
        return;
    }
}
