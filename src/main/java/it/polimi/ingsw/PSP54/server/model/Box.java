package it.polimi.ingsw.PSP54.server.model;

public class Box {
    private int x;
    private int y;
    private int level;
    private boolean dome;
    private boolean complete;
    private Worker worker;

    public Box(int x, int y, int level, boolean dome) {
        this.x = x;
        this.y = y;
        this.level = level;
        this.dome = dome;
    }

    //getters & setters

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isDome() {
        return dome;
    }

    public void setDome(boolean dome) {
        this.dome = dome;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    //TODO: return the worker which is occupying the box?
    public boolean isOccupied(){

        boolean occupied = false;
        if (this.getWorker()!= null)
            occupied = true;
        return occupied;
    }

}
