package it.polimi.ingsw.PSP54.server.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class representing the player whit his default actions and turn administration
 */
public class StandardPlayer implements Player, Serializable, Cloneable {

    private static final int APOLLO = 0, ARTEMIS = 1, ATHENA = 2, ATLAS = 3, DEMETER = 4;
    private int cardID;
    private Game game;
    private String playerName;
    private int age, virtualViewId;
    private String color;
    private final Worker[] workers = new Worker[2];
    private boolean playing;
    private boolean winner;
    private boolean loser;


    public StandardPlayer(String playerName) {
        this.playerName = playerName;
        this.workers[0] = new Worker(true, this, null);
        this.workers[1] = new Worker(false, this, null);
        this.winner = false;
        this.loser = false;
        this.playing = false;
    }

    /**
     * Instantiates a new Player with corresponding workers
     * @param playerName the name of the Player
     */
    public StandardPlayer(String playerName, int age, int virtualViewId) {
        this.playerName = playerName;
        this.virtualViewId = virtualViewId;
        this.age = age;
        this.workers[0] = new Worker(true, this, null);
        this.workers[1] = new Worker(false, this, null);
        this.winner = false;
        this.loser = false;
        this.playing = false;
    }

    /**
     * Decorates the current player
     * @param cardID the number of the card
     * @return the decorated player
     */
    @Override
    public Player assignPower(int cardID){ //where to be performed?

        Player actualPlayer = new StandardPlayer(null,0, 0);

        switch (cardID) {
            case APOLLO:
                actualPlayer = new ApolloDecorator(this);
                actualPlayer.setCardID(APOLLO);
                break;
            case ARTEMIS:
                actualPlayer = new ArtemisDecorator(this);
                actualPlayer.setCardID(ARTEMIS);
                break;
            case ATHENA:
                actualPlayer = new AthenaDecorator(this);
                actualPlayer.setCardID(ATHENA);
                break;
            case ATLAS:
                actualPlayer = new AtlasDecorator(this);
                actualPlayer.setCardID(ATLAS);
                break;
            case DEMETER:
                actualPlayer = new DemeterDecorator(this);
                actualPlayer.setCardID(DEMETER);
                break;
        }

        return actualPlayer;
    }

    /**
     * Sets initial position on the board for the selected worker.
     * @param worker the selected worker.
     * @param x the board abscissa.
     * @param y the board ordinate.
     */
    @Override
    public void setWorkerPos (Worker worker, int x, int y) throws InvalidMoveException{
        if (!getGame().getBoard()[x][y].isOccupied()) {
            worker.setPos(getGame().getBoard()[x][y]);
            getGame().getBoard()[x][y].setWorker(worker);
            setWorkerBoxesToMove(worker);
            setWorkerBoxesToBuild(worker);
        } else
            throw new InvalidMoveException();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean areWorkerSettled() {

        return workers[0].getPos() != null && workers[1].getPos() != null;
    }

    /**
     * Select the worker which player is going to use depending on the worker's sex
     * @param male the worker's sex
     * @return the chosen worker
     */
    @Override
    public Worker choseWorker(Boolean male) {

        if (male)
            return this.workers[0];
        else
            return this.workers[1];
    }

    /**
     *Initialize current player's turn by setting worker's action tokens
     * @param male represent the sex of the worker which the player is going to use
     * @return the chosen worker with updated tokens
     */
    @Override
    public Worker turnInit(Boolean male) {
        Worker currentWorker = choseWorker(male);
        currentWorker.setMoveToken(1);
        currentWorker.setBuildToken(0);
        return currentWorker;
    }

    /**
     * Sets available boxes for the worker to move and stores them in worker's attribute
     * @param worker current worker in use
     * @return the vector containing available boxes
     */
    @Override
    public ArrayList<Box> setWorkerBoxesToMove (Worker worker) {

        ArrayList<Box> boxes = new ArrayList<>();
        int deltaX, deltaY, deltaH;
        Box[][] board = getGame().getBoard();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                deltaX = Math.abs(worker.getPos().getX() - board[i][j].getX());
                deltaY = Math.abs(worker.getPos().getY() - board[i][j].getY());
                deltaH =  board[i][j].getLevel() - worker.getPos().getLevel();
                if ((deltaX <= 1 && deltaY <= 1) && deltaH <= 1 && !board[i][j].isOccupied() && !board[i][j].isDome())
                    boxes.add(board[i][j]);
            }
        }
        worker.setBoxesToMove(boxes);
        return boxes;
    }

    /**
     * Set available boxes for the worker to build and stores them in worker's attribute
     * @param worker current worker in use
     * @return the vector containing buildable boxes
     */
    @Override
    public ArrayList<Box> setWorkerBoxesToBuild (Worker worker){
        ArrayList<Box> boxes = new ArrayList<>();
        int deltaX, deltaY;
        Box[][] board = getGame().getBoard();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                deltaX = Math.abs(worker.getPos().getX() - board[i][j].getX());
                deltaY = Math.abs(worker.getPos().getY() - board[i][j].getY());
                if ((deltaX <= 1 && deltaY <= 1) && !board[i][j].isOccupied() && !board[i][j].isDome())
                    boxes.add(board[i][j]);
            }
        }
        worker.setBoxesToBuild(boxes);
        return boxes;
    }

    /**
     * If valid performs move and modify action tokes
     * @param worker selected worker which the player wants to move
     * @param dest selected destination box
     * @throws InvalidMoveException if the move can't be done
     */
    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException{

        ArrayList<Box> valid = worker.getBoxesToMove();
        int currentMoveToken = worker.getMoveToken();


        if (currentMoveToken > 0 && valid.contains(dest)){
            //free current box
            worker.getPos().setWorker(null);
            //perform move
            worker.setPos(dest);
            dest.setWorker(worker);
            //set tokens
            worker.setMoveToken(currentMoveToken - 1);
            worker.setBuildToken(1);
            setWorkerBoxesToMove(worker);
            setWorkerBoxesToBuild(worker);

        } else throw new InvalidMoveException();

    }

    /**
     *If valid performs build and modify action tokens
     * @param worker selected worker which the player wants to move
     * @param dest selected box where to build
     */
    @Override
    public void build (Worker worker, Box dest) throws InvalidBuildingException{

        ArrayList<Box> valid = worker.getBoxesToBuild();
        int currentBuildToken = worker.getBuildToken();

        if (currentBuildToken > 0 && valid.contains(dest)) {
            if (dest.getLevel() == 3)
                dest.setDome(true);
            else {
                int currentLevel = dest.getLevel();
                dest.setLevel(currentLevel+1);
            }
            worker.setBuildToken(currentBuildToken - 1);
            setWorkerBoxesToMove(worker);
            setWorkerBoxesToBuild(worker);
        }
        else
            throw new InvalidBuildingException();
    }

    /**
     * Creates a reference to the current Game, in order to access board's and other players' info
     * @param game the current Game
     */
    @Override
    public void setGame(Game game) { //it has to be invoked by Controller or Game class
        this.game = game;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public boolean isWinner() {
        return winner;
    }

    @Override
    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    @Override
    public boolean isLoser() {
        return loser;
    }

    @Override
    public void setLoser(boolean loser) {
        this.loser = loser;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int getCardID() {
        return cardID;
    }

    @Override
    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    @Override
    public Worker[] getWorkers() { return workers; }

    @Override
    public int getVirtualViewID() {
        return this.virtualViewId;
    }

    @Override
    public void setVirtualViewId(int virtualViewId) {
        this.virtualViewId = virtualViewId;
    }

    @Override
    public boolean isPlaying() {
        return playing;
    }

    @Override
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

}