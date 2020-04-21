package it.polimi.ingsw.PSP54.server.controller;

import it.polimi.ingsw.PSP54.server.model.*;
import it.polimi.ingsw.PSP54.server.virtualView.VirtualView;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private Game game;
    private VirtualView virtualView;

    public Controller (Game game, VirtualView virtualView) {
        this.game = game;
        this.virtualView = virtualView;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o != virtualView || !((arg instanceof Move) || (arg instanceof Build) || (arg instanceof Player))){
            throw new IllegalArgumentException();
        }
        if (arg instanceof Player){
            Player p = (Player) arg;
            try {
                game.newPlayer(p.getPlayerName(),p.getAge(),p.getWorkerColour());
            } catch (IllegalNumberOfPlayersException e){
                return;
            }
        }
        if (arg instanceof Move && ((Move) arg).isSetFirstPos()) {
            Move m = (Move) arg;
            try {
                game.setWorker(m);
                virtualView.setFirstWorkerSetDone(true);
            } catch (InvalidMoveException e) {
                return;
            }
        }
        if (arg instanceof Move && !(((Move) arg).isSetFirstPos())) {
            Move m = (Move) arg;
            try {
                game.move(m);
                virtualView.setMoveDone(true);
            } catch (InvalidMoveException e) {
                return;
            }
        }
        if (arg instanceof Build) {
            Build b = (Build) arg;
            try {
                game.build(b);
                virtualView.setBuildDone(true);
            } catch (InvalidBuildingException e) {
                return;
            }
        }
    }
}
