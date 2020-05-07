package it.polimi.ingsw.PSP54.utils;

import java.io.Serializable;

/**
 * Classe utilizzata come messaggio per notificare spostamenti
 */
public class Move implements Serializable,Cloneable {
    private int player_ind, worker_ind, x, y, virtualViewId;
    private boolean setFirstPos = false, isMale;

    public Move(boolean isMale, int x, int y) {
        this.x = x;
        this.y = y;
        if (isMale) {
            worker_ind = 0;
        }
        else
            worker_ind = 1;
    }

    public void setVirtualViewId(int virtualViewId) {
        this.virtualViewId = virtualViewId;
    }

    public int getVirtualViewId() {
        return virtualViewId;
    }

    public int getPlayer_ind() {
        return player_ind;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isSetFirstPos() {
        return setFirstPos;
    }

    public void setSetFirstPos(boolean setFirstPos) {
        this.setFirstPos = setFirstPos;
    }

    public void setPlayer_ind(int player_ind) {
        this.player_ind = player_ind;
    }

    public int getWorker_ind() {
        return worker_ind;
    }

    public void setWorker_ind(int worker_ind) {
        this.worker_ind = worker_ind;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }
}
