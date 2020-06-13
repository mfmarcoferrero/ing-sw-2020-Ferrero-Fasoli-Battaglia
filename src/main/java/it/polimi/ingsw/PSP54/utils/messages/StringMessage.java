package it.polimi.ingsw.PSP54.utils.messages;

import java.io.Serializable;

public class StringMessage extends GameMessage implements Serializable, Cloneable {

    private final String message;

    //init
    public static final String welcomeMessage = "Welcome!";
    public static final String setNumberOfPlayersMessage = "Hey, set the number of player:";
    public static final String turnMessage = "It's your turn!";
    public static final String setFirstWorkerMessage = "Chose the first worker to place: [Enter m/f]";
    public static final String setSecondWorkerMessage = "Set your second worker:";
    //cards
    public static final String apolloMessage = "You have Apollo!\nYour Move: Your Worker may move on an occupied cell, changing position with that worker.";
    public static final String athenaMessage = "You have Athena!\nOpponent's Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";
    public static final String atlasMessage = "You have Atlas!\nYour Build: Your Worker may build a dome at any level.";
    public static final String demeterMessage = "You have Demeter!\nYour Build: Your Worker may build one additional time, but not on the same space.";
    public static final String artemisMessage = "You have Artemis!\nYour Move: Your Worker may move one additional time, but not back to its initial space.";
    public static final String hephaestusMessage = "You have Hephaestus!\nYour Build: Your Worker may build one additional block (not dome) on top of your first block.";
    public static final String minotaurMessage = "You have Minotaur!\nYour Move: Your worker may move into an opponent worker's space, if their worker can be forced one space straight backwards to an occupied space at any level.";
    public static final String panMessage = "You have Pan!\nWin Condition: You also win if your Worker moves down two or more levels.";
    public static final String prometheusMessage = "You have Prometheus!\nYour Turn: If your worker does not move up, it may build both before and after moving.";
    //standard
    public static final String winMessage = "You won";
    public static final String choseWorker = "Chose your worker: [Enter m/f]";
    public static final String moveMessage = "Make your move!";
    public static final String buildMessage = "Where do you want to build?";
    public static final String waitMessage = "Wait for the other player's choice!";
    public static final String loseMessage = " has lose!";
    public static final String wrongTurnMessage = "It is not your turn!";
    public static final String invalidMoveMessage = "You can't move here, please retry.";
    public static final String invalidBuildingMessage = "You can't build here, please retry.";
    public static final String workerCantMove = "You can't chose this worker, he can't move!\nPlease, retry:";
    //specials
    public static final String nameAlreadyTaken = "the name you choose already exist, please try with a new one";
    public static final String newWorkerMove = "The worker you have selected can't do the action, so enter f if you were using m or m if you were using f";
    public static final String buildFirst = "Do you want to build first? [Enter y/n]";
    public static final String moveAgain = "Do you want to move again? [Enter y/n]";
    public static final String buildAgain = "Do you want to build again? [Enter y/n]";
    public static final String buildOrDome = "Do you want to build a dome? [Enter y/n]";
    public static final String EndForDisconnection = "the match is cancelled because one of the player has lose connection";
    //connection
    public static final String closedLobby = "The lobby you were in has closed, please login again.";
    public static final String closedConnection = "Connection closed from server side";
    public static final String serverUnreachable = "Server is Unreachable";

    public String getMessage() {
        return message;
    }

    public StringMessage(Integer virtualViewID, String message) {
        super(virtualViewID);
        this.message = message;
    }
}
