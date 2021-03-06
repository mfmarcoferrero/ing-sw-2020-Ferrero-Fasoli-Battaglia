package it.polimi.ingsw.PSP54.server.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstract decorator class that delegates all methods
 */
public abstract class GodDecorator implements Player, Serializable, Cloneable {

    private final Player playerToDecorate;

    public GodDecorator(Player player) {
        this.playerToDecorate = player;
    }

    @Override
    public boolean areWorkerSettled() {
        return playerToDecorate.areWorkerSettled();
    }

    @Override
    public Worker getWorker(Boolean male) {
        return playerToDecorate.getWorker(male);
    }

    @Override
    public ArrayList<Box> setWorkerBoxesToMove(Worker worker) {
        return playerToDecorate.setWorkerBoxesToMove(worker);
    }

    @Override
    public ArrayList<Box> setWorkerBoxesToBuild(Worker worker) {
        return playerToDecorate.setWorkerBoxesToBuild(worker);
    }

    @Override
    public Worker getCurrentWorker() {
        return playerToDecorate.getCurrentWorker();
    }

    @Override
    public void setCurrentWorker(Worker currentWorker) {
        playerToDecorate.setCurrentWorker(currentWorker);
    }

    @Override
    public void nextCurrentWorker(Worker currentWorker) {
        playerToDecorate.nextCurrentWorker(currentWorker);
    }

    @Override
    public Worker turnInit(Boolean male) {
        return playerToDecorate.turnInit(male);
    }

    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {
        playerToDecorate.move(worker, dest);
    }

    @Override
    public void build(Worker worker, Box dest) throws InvalidBuildingException {
        playerToDecorate.build(worker, dest);
    }

    @Override
    public void chose(boolean choice) {
        playerToDecorate.chose(choice);
    }

    @Override
    public int getAge() {
        return playerToDecorate.getAge();
    }

    @Override
    public String getPlayerName() {
        return playerToDecorate.getPlayerName();
    }

    @Override
    public void setAge(int age) {
        playerToDecorate.setAge(age);
    }

    @Override
    public void setColor(String color) {
        playerToDecorate.setColor(color);
    }

    @Override
    public String getColor() {
        return playerToDecorate.getColor();
    }

    @Override
    public void setGame(Game game) {
        playerToDecorate.setGame(game);
    }

    @Override
    public Game getGame() {
        return playerToDecorate.getGame();
    }

    @Override
    public void checkWinner(Worker worker) {
        playerToDecorate.checkWinner(worker);
    }

    @Override
    public Player assignPower(int cardID) {
        return playerToDecorate.assignPower(cardID);
    }

    @Override
    public void setCardID(int cardID) {
        playerToDecorate.setCardID(cardID);
    }

    @Override
    public int getCardID() {
        return playerToDecorate.getCardID();
    }

    @Override
    public void setWorkerPos(Worker worker, int x, int y) throws InvalidMoveException {
        playerToDecorate.setWorkerPos(worker, x, y);
    }

    @Override
    public Worker[] getWorkers() { return playerToDecorate.getWorkers();}

    @Override
    public int getVirtualViewID() {
        return playerToDecorate.getVirtualViewID();
    }

    @Override
    public boolean isPlaying() {
        return playerToDecorate.isPlaying();
    }

    @Override
    public void setPlaying(boolean playing) {
        playerToDecorate.setPlaying(playing);
    }

    @Override
    public void setVirtualViewId(int virtualViewId) {
        playerToDecorate.setVirtualViewId(virtualViewId);
    }

    @Override
    public boolean isLoser() {
        return playerToDecorate.isLoser();
    }

    @Override
    public void setLoser(boolean loser) {
        playerToDecorate.setLoser(loser);
    }
}

