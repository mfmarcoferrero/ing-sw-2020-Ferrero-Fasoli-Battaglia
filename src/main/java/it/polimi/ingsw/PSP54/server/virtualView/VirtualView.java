package it.polimi.ingsw.PSP54.server.virtualView;

import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import it.polimi.ingsw.PSP54.server.controller.*;
import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.server.model.Player;

public class VirtualView extends Observable implements Observer {

    private Scanner scanner;
    private PrintStream outputStream;
    private Move move;
    private Build build;
    private Box [][] board;
    private boolean moveDone = false, buildDone = false, firstWorkerSetDone = false;

    public VirtualView() {
        scanner = new Scanner(System.in);
        outputStream = new PrintStream(System.out);
    }

    public void addPlayer(Player p) {
        setChanged();
        notifyObservers(p);
    }

    public void setWorker() {
        while (firstWorkerSetDone == false && move.isSetFirstPos()){
            setChanged();
            notifyObservers(move);
        }
    }

    public void playerTurn() {
        while (moveDone == false || buildDone == false) {
            if (moveDone == false && !(move.isSetFirstPos())) {
                setChanged();
                notifyObservers(move);
            }
            if(buildDone == false){
                setChanged();
                notifyObservers(build);
            }
        }
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    public void setMoveDone(boolean moveDone) {
        this.moveDone = moveDone;
    }

    public void setBuildDone(boolean buildDone) {
        this.buildDone = buildDone;
    }

    public void setFirstWorkerSetDone(boolean workerSet) {
        this.firstWorkerSetDone = workerSet;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(!(o instanceof Game) || !(arg instanceof Box[][])){
            throw new IllegalArgumentException();
        }
        board = (Box[][])arg;
    }
}