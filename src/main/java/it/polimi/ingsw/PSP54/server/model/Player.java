package it.polimi.ingsw.PSP54.server.model;

import java.util.ArrayList;

public interface Player {

    /**
     * Called whenever a player needs to be decorated.
     * @param cardID the number of the card
     * @return the decorated player
     */
    Player assignPower(int cardID);

    /**
     * Sets initial position on the board for the selected worker.
     * @param worker the selected worker.
     * @param x the board abscissa.
     * @param y the board ordinate.
     */
    void setWorkerPos (Worker worker, int x, int y) throws InvalidMoveException;

    /**
     * Called to determine if the player's worker are placed on the board.
     * @return true if both workers are placed, false otherwise.
     */
    boolean areWorkerSettled();

    /**
     * Called only for the initial worker's placement. It sets the next worker to current.
     * @param currentWorker previous current worker.
     */
    void nextCurrentWorker(Worker currentWorker);

    /**
     * Select the worker which player is going to use depending on the worker's sex.
     * @param male the worker's sex.
     * @return the chosen worker.
     */
    Worker getWorker(Boolean male);

    /**
     * Method used to initialize current player's turn.
     * @param male represent the sex of the worker which the player is going to use.
     * @return the chosen worker with updated tokens.
     */
    Worker turnInit(Boolean male);

    /**
     * Method used to set available boxes for the worker to move.
     * @param worker current worker in use.
     * @return the vector containing available boxes.
     */
    ArrayList<Box> setWorkerBoxesToMove(Worker worker);

    /**
     * Method used to set available boxes for the worker to build.
     * @param worker current worker in use.
     * @return the vector containing buildable boxes.
     */
    ArrayList<Box> setWorkerBoxesToBuild(Worker worker);

    /**
     * Method used to perform a move action.
     * @param worker selected worker which the player wants to move.
     * @param dest selected destination box.
     * @throws InvalidMoveException if the move can't be done.
     */
    void move(Worker worker, Box dest) throws InvalidMoveException;

    /**
     * Method used to perform a build action.
     * @param worker selected worker which the player wants to move.
     * @param dest selected box where to build.
     */
    void build(Worker worker, Box dest) throws InvalidBuildingException;

    /**
     * Called to check if the player has won.
     * @param worker the current worker which the player has moved.
     */
    void checkWinner(Worker worker);

    /**
     * Method used to perform a binary choice.
     * @param choice the player's choice.
     */
    void chose(boolean choice);

    //setters & getters

    /**
     * Creates a reference to the current Game, in order to access board's and other players' info.
     * @param game the current Game.
     */
    void setGame(Game game);

    Game getGame();

    String getPlayerName();

    void setAge(int age);

    int getAge();

    void setColor(String color);

    String getColor();

    boolean isWinner();

    void setWinner(boolean winner);

    boolean isLoser();

    void setLoser(boolean loser);

    int getCardID();

    void setCardID(int cardID);

    Worker[] getWorkers();

    Worker getCurrentWorker();

    void setCurrentWorker(Worker currentWorker);

    int getVirtualViewID ();

    void setVirtualViewId(int virtualViewId);

    boolean isPlaying();

    void setPlaying(boolean playing);
}
