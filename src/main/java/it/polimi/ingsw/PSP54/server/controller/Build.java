package it.polimi.ingsw.PSP54.server.controller;

import it.polimi.ingsw.PSP54.server.model.Box;

public class Build {
    private int player_ind, worker_ind, x, y;
    private boolean setDome;

    public Build(int player_ind, int worker_ind, int x, int y, boolean setDome) {
        this.worker_ind = worker_ind;
        this.player_ind = player_ind;
        this.x = x;
        this.y = y;
        this.setDome = setDome;
    }

    public int getPlayer_ind() {
        return player_ind;
    }

    public int getWorker_ind() {
        return worker_ind;
    }

    public boolean isSetDome() {
        return setDome;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
