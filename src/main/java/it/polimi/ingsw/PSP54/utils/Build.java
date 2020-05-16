package it.polimi.ingsw.PSP54.utils;

import java.io.Serializable;

/**
 * Classe utilizzata come messsaggio per notificare costruzioni
 */
public class Build implements Serializable,Cloneable {
    private int player_ind, x, y, virtualViewId,worker_ind;
    private boolean setDome, male, endTurn = false;

    public Build (boolean endTurn){
        this.endTurn = true;
    }

    public Build(boolean male, int x, int y) {
        this.male = male;
        this.x = x;
        this.y = y;
        if (isMale()) {
            worker_ind = 0;
        }
        else
            worker_ind = 1;
    }

    public int getPlayer_ind() {
        return player_ind;
    }

    public boolean isSetDome() {
        return setDome;
    }

    public void setVirtualViewId(int virtualViewId) {
        this.virtualViewId = virtualViewId;
    }

    public int getVirtualViewId() {
        return virtualViewId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPlayer_ind(int player_ind) {
        this.player_ind = player_ind;
    }

    public boolean isMale() {
        return male;
    }

    public boolean isEndTurn() {
        return endTurn;
    }

    public void setEndTurn(boolean endTurn) {
        this.endTurn = endTurn;
    }

    public int getWorker_ind() {
        return worker_ind;
    }
}
