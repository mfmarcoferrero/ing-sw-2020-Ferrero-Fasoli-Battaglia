package it.polimi.ingsw.PSP54.server.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Worker implements Serializable, Cloneable {
    private final Boolean male;
    private final StandardPlayer owner;
    private Box pos;
    private int moveToken;
    private int buildToken;
    private ArrayList<Box> boxesToBuild;
    private ArrayList<Box> boxesToMove;

    public Worker(Boolean male, StandardPlayer owner, Box pos) {
        this.male = male;
        this.owner = owner;
        this.pos = pos;
    }

    //setters & getters

    public Boolean getMale() {
        return male;
    }

    public StandardPlayer getOwner() {
        return owner;
    }

    public Box getPos() {
        return pos;
    }

    public void setPos(Box pos) {
        this.pos = pos;
    }

    public int getMoveToken() {
        return moveToken;
    }

    public void setMoveToken(int moveToken) {
        this.moveToken = moveToken;
    }

    public int getBuildToken() {
        return buildToken;
    }

    public void setBuildToken(int buildToken) {
        this.buildToken = buildToken;
    }

    public ArrayList<Box> getBoxesToBuild() {
        return boxesToBuild;
    }

    public void setBoxesToBuild(ArrayList<Box> boxesToBuild) {
        this.boxesToBuild = boxesToBuild;
    }

    public ArrayList<Box> getBoxesToMove() {
        return boxesToMove;
    }

    public void setBoxesToMove(ArrayList<Box> boxesToMove) {
        this.boxesToMove = boxesToMove;
    }
}
