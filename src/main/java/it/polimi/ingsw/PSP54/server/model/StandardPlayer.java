package it.polimi.ingsw.PSP54.server.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class representing the player whit his default actions and turn administration.
 */
public class StandardPlayer implements Player, Serializable, Cloneable {

    private int cardID;
    private Game game;
    private final String playerName;
    private int age, virtualViewId;
    private String color;
    private final Worker[] workers = new Worker[2];
    private Worker currentWorker;
    private boolean playing;
    private boolean loser;


    /**
     * Instantiates a new Player with corresponding workers.
     * @param playerName the name of the Player.
     * @param age age of the Player.
     * @param virtualViewId VirtualView of the player.
     */
    public StandardPlayer(String playerName, int age, int virtualViewId) {
        this.playerName = playerName;
        this.virtualViewId = virtualViewId;
        this.age = age;
        this.workers[0] = new Worker(true, this, null);
        this.workers[1] = new Worker(false, this, null);
        this.playing = false;
        this.loser = false;
    }

    /**
     * Called whenever a player needs to be decorated.
     * Decorates the player at the beginning of a game accordingly to the chosen card.
     * @param cardID the number of the card
     * @return the decorated player
     */
    @Override
    public Player assignPower(int cardID){

        Player actualPlayer = this;

        switch (cardID) {
            case Game.APOLLO:
                actualPlayer = new ApolloDecorator(this);
                actualPlayer.setCardID(Game.APOLLO);
                break;
            case Game.ARTEMIS:
                actualPlayer = new ArtemisDecorator(this);
                actualPlayer.setCardID(Game.ARTEMIS);
                break;
            case Game.ATHENA:
                actualPlayer = new AthenaDecorator(this);
                actualPlayer.setCardID(Game.ATHENA);
                break;
            case Game.ATLAS:
                actualPlayer = new AtlasDecorator(this);
                actualPlayer.setCardID(Game.ATLAS);
                break;
            case Game.DEMETER:
                actualPlayer = new DemeterDecorator(this);
                actualPlayer.setCardID(Game.DEMETER);
                break;
            case Game.HEPHAESTUS:
                actualPlayer = new HephaestusDecorator(this);
                actualPlayer.setCardID(Game.HEPHAESTUS);
                break;
            case Game.MINOTAUR:
                actualPlayer = new MinotaurDecorator(this);
                actualPlayer.setCardID(Game.MINOTAUR);
                break;
            case Game.PAN:
                actualPlayer = new PanDecorator(this);
                actualPlayer.setCardID(Game.PAN);
                break;
            case Game.PROMETHEUS:
                actualPlayer = new PrometheusDecorator(this);
                actualPlayer.setCardID(Game.PROMETHEUS);
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
        } else
            throw new InvalidMoveException();
    }

    /**
     * Checks if all the player's workers are already on the board.
     * @return true if both workers are placed, false otherwise.
     */
    public boolean areWorkerSettled() {
        return workers[0].getPos() != null && workers[1].getPos() != null;
    }

    /**
     * This method is invoked only for the initial worker's placement. It sets the unplaced worker to current.
     * @param currentWorker the worker that has just been settled.
     */
    public void nextCurrentWorker(Worker currentWorker) {
        if (currentWorker.equals(getWorkers()[0]))
            setCurrentWorker(getWorkers()[1]);
        else
            setCurrentWorker(getWorkers()[0]);
    }

    /**
     * Select the worker which player is going to use depending on the worker's sex.
     * @param male the worker's sex.
     * @return the chosen worker.
     */
    @Override
    public Worker getWorker(Boolean male) {
        if (male) {
            return this.workers[0];
        }
        else
            return this.workers[1];
    }

    /**
     * Method used to initialize current player's turn.
     * Sets the selected worker's moveToken to 1 and the buildToken to zero.
     * @param male represent the sex of the worker which the player is going to use.
     * @return the chosen worker with updated tokens.
     */
    @Override
    public Worker turnInit(Boolean male) {
        Worker currentWorker = getWorker(male);
        currentWorker.setMoveToken(1);
        currentWorker.setBuildToken(0);
        return currentWorker;
    }

    /**
     * Method used to set available boxes for the worker to move.
     * Returns all adjacent boxes that are at least a level higher than the current worker's position.
     * @param worker current worker in use.
     * @return the vector containing available boxes.
     */
    @Override
    public ArrayList<Box> setWorkerBoxesToMove (Worker worker){

        ArrayList<Box> valid = new ArrayList<>();
        int deltaX, deltaY, deltaH;
        Box[][] board = getGame().getBoard();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                deltaX = Math.abs(worker.getPos().getX() - board[i][j].getX());
                deltaY = Math.abs(worker.getPos().getY() - board[i][j].getY());
                deltaH =  board[i][j].getLevel() - worker.getPos().getLevel();
                if ((deltaX <= 1 && deltaY <= 1) && deltaH <= 1 && !board[i][j].isOccupied() && !board[i][j].isDome())
                    valid.add(board[i][j]);
            }
        }
        worker.setBoxesToMove(valid);
        return valid;
    }

    /**
     * Set available boxes for the worker to build and stores them in worker's attribute.
     * @param worker current worker in use.
     * @return the vector containing buildable boxes.
     */
    @Override
    public ArrayList<Box> setWorkerBoxesToBuild (Worker worker) {
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
     * Method used to perform a move action.
     * If the destination box is contained in worker's boxesToMove performs the move, decrements worker's moveToken and increments the buildToken.
     * @param worker selected worker which the player wants to move.
     * @param dest selected destination box.
     * @throws InvalidMoveException if the move can't be done.
     */
    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException{
        ArrayList<Box> valid = worker.getBoxesToMove();
        int currentMoveToken = worker.getMoveToken();

        if (currentMoveToken > 0 && valid.contains(dest)){
            //save a reference to the current position
            Box current = worker.getPos();
            //free current box
            worker.getPos().setWorker(null);
            //perform move
            worker.setPos(dest);
            dest.setWorker(worker);
            //set tokens
            worker.setMoveToken(currentMoveToken - 1);
            worker.setBuildToken(1);
            getGame().notifyBoard();
            if (current.getLevel() < dest.getLevel())
                checkWinner(worker);

        } else throw new InvalidMoveException();
    }

    /**
     * Method used to perform a build action.
     * If the destination box is contained in worker's boxesToBuild performs the build and decrement worker's buildToken.
     * @param worker selected worker which the player wants to move.
     * @param dest selected box where to build.
     * @throws InvalidBuildingException if the build can't be done.
     */
    @Override
    public void build (Worker worker, Box dest) throws InvalidBuildingException {

        ArrayList<Box> valid = worker.getBoxesToBuild();
        int currentBuildToken = worker.getBuildToken();

        if (currentBuildToken > 0 && valid.contains(dest)) {
            if (dest.getLevel() == 3)
                dest.setDome(true);
            else {
                int currentLevel = dest.getLevel();
                dest.setLevel(currentLevel + 1);
            }
            worker.setBuildToken(currentBuildToken - 1);
            getGame().notifyBoard();
        }
        else
            throw new InvalidBuildingException();
    }

    @Override
    public void checkWinner(Worker worker) {
        if(worker.getPos().getLevel()==3)
            game.notifyWinner(worker.getOwner());
    }

    /**
     * Method used to perform a binary choice.
     * Empty method. Binary choice are used to perform special actions. A StandardPlayer cannot make this type of choice.
     * @param choice the player's choice.
     */
    @Override
    public void chose(boolean choice) { }

    //setters & getters
    /**
     * Creates a reference to the current Game, in order to access board's and other players' info.
     * @param game the current Game.
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
    public Worker getCurrentWorker() {
        return currentWorker;
    }

    @Override
    public void setCurrentWorker(Worker currentWorker) {
        this.currentWorker = currentWorker;
    }

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

    @Override
    public boolean isLoser() {
        return loser;
    }

    @Override
    public void setLoser(boolean loser) {
        this.loser = loser;
    }
}