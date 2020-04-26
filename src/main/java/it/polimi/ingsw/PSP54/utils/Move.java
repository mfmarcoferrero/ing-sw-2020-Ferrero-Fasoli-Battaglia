package it.polimi.ingsw.PSP54.utils;

/**
 * Classe utilizzata come messaggio per notificare spostamenti
 */
public class Move {
    private int player_ind, worker_ind, x, y;
    private boolean setFirstPos = true;

    public Move(int player_ind, int worker_ind, int x, int y) {
        this.x = x;
        this.y = y;
        this.worker_ind = worker_ind;
        this.player_ind = player_ind;
    }

    public int getWorker_ind() {
        return worker_ind;
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
}
