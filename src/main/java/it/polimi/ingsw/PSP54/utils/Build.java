package it.polimi.ingsw.PSP54.utils;

import java.io.Serializable;

/**
 * Classe utilizzata come messsaggio per notificare costruzioni
 */
public class Build implements Serializable,Cloneable {
    private int player_ind, x, y, virtualViewId;
    private boolean setDome, male;

    public Build(boolean male, int x, int y) {
        this.male = male;
        this.x = x;
        this.y = y;
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

}
