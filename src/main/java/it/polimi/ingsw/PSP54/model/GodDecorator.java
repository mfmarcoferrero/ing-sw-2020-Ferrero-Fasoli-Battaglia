package it.polimi.ingsw.PSP54.model;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Abstract decorator class that delegates all methods
 */
public abstract class GodDecorator implements Player {

    private final Player playerToDecorate;

    public GodDecorator(Player player) {
        this.playerToDecorate = player;
    }

    @Override
    public Worker choseWorker(Boolean male) {
        return playerToDecorate.choseWorker(male);
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
    public Worker turnInit(Boolean male) {
        return playerToDecorate.turnInit(male);
    }

    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {
        playerToDecorate.move(worker, dest);
    }

    @Override
    public void build(Worker worker, Box dest) {
        playerToDecorate.build(worker, dest);
    }

    @Override
    public void endTurn() {
        playerToDecorate.endTurn();
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
    public boolean isWinner() {
        return playerToDecorate.isWinner();
    }

    @Override
    public void setWinner(boolean winner) {
        playerToDecorate.setWinner(winner);
    }

    @Override
    public boolean isLoser() {
        return playerToDecorate.isLoser();
    }

    @Override
    public void setLoser(boolean loser) {
        playerToDecorate.setLoser(loser);
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
    public void setWorkerPos(Worker worker, int x, int y) {
        playerToDecorate.setWorkerPos(worker, x, y);
    }

    //only for debug purpose

    @Override
    public void addSideEffect() {
        playerToDecorate.addSideEffect();
    }

    @Override
    public void rmvSideEffect() {
        playerToDecorate.rmvSideEffect();
    }

    @Override
    public void printPower() {
        playerToDecorate.printPower();
    }

}

