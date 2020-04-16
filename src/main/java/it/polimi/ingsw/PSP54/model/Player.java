package it.polimi.ingsw.PSP54.model;

import java.util.Vector;

public interface Player {

    //TODO:Setters & getters

    Worker choseWorker(Boolean male);

    Vector setWorkerBoxesToMove(Worker worker);

    Vector setWorkerBoxesToBuild(Worker worker);

    Worker turnInit(Boolean male);

    void move(Worker worker, Box dest) throws InvalidMoveException;

    void build(Worker worker, Box dest);

    void endTurn();

    String getPlayerName();

    void setAge(int age);

    int getAge();

    void setColor(String color);

    String getColor();

    void setGame(Game game);

    Game getGame();

    boolean isWinner();

    void setWinner(boolean winner);

    boolean isLoser();

    void setLoser(boolean loser);

    Player assignPower(int cardID);

    int getCardID();

    void setCardID(int cardID);

    //Only for debug purpose

    void addSideEffect ();

    void rmvSideEffect ();

    void printPower();
}
