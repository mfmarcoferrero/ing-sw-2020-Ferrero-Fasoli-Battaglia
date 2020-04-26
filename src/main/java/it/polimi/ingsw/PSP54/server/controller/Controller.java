package it.polimi.ingsw.PSP54.server.controller;

import it.polimi.ingsw.PSP54.server.model.*;
import it.polimi.ingsw.PSP54.server.virtualView.VirtualView;
import it.polimi.ingsw.PSP54.utils.Build;
import it.polimi.ingsw.PSP54.utils.Move;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private Game game;
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
        this.virtualViewList.add(virtualView.getVirtualViewId(),virtualView);
    }

    /**
     * In base al tipo di messaggio che viene notificato dalla virtual view
     * esegue un operazione sul model
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        for (VirtualView v : virtualViewList){
            if (!(o instanceof VirtualView)){
                throw new IllegalArgumentException();
            }
        }
        if(!((arg instanceof Move) || (arg instanceof Build) || (arg instanceof Player))) {
            throw new IllegalArgumentException();
        }
        if (arg instanceof Player) {
            Player p = (Player) arg;
            try {
                game.newPlayer(p.getPlayerName(),p.getAge(),p.getWorkerColour());
            } catch (IllegalNumberOfPlayersException e) {
                return;
            }
        }
        if (arg instanceof Move && ((Move) arg).isSetFirstPos()) {
            Move m = (Move) arg;
            try {
                game.setWorker(m);
                virtualViewList.get(((Move) arg).getPlayer_ind()).setFirstWorkerSetDone(true);
            } catch (InvalidMoveException e) {
                return;
            }
        }
        if (arg instanceof Move && !(((Move) arg).isSetFirstPos())) {
            Move m = (Move) arg;
            try {
                game.move(m);
                virtualViewList.get(((Move) arg).getPlayer_ind()).setMoveDone(true);
            } catch (InvalidMoveException e) {
                return;
            }
        }
        if (arg instanceof Build) {
            Build b = (Build) arg;
            try {
                game.build(b);
                virtualViewList.get(((Build) arg).getPlayer_ind()).setBuildDone(true);
            } catch (InvalidBuildingException e) {
                return;
            }
        }
    }
}
