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
    public static final String cantSelect = "You can't chose your card now";
    public static final String apolloMessage = "You have Apollo!\nYour Move: Your Worker may move on an occupied cell, changing position with that worker.";
    public static final String athenaMessage = "You have Athena!\nOpponent's Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";
    public static final String atlasMessage = "You have Atlas!\nYour Build: Your Worker may build a dome at any level.";
    public static final String demeterMessage = "You have Demeter!\nYour Build: Your Worker may build one additional time, but not on the same space.";
    public static final String artemisMessage = "You have Artemis!\nYour Move: Your Worker may move one additional time, but not back to its initial space.";

    public static final String choseWorker = "Chose your worker: [Enter m/f]";
    public static final String moveMessage = "Make your move!";
    public static final String buildMessage = "Where do you want to build?";
    public static final String waitMessage = "Wait for the other player's choice!";
    public static final String winMessage = "You win!";
    public static final String loseMessage = "You lose!";
    public static final String wrongTurnMessage = "It is not your turn!";
    public static final String wrongPlacementMessage = "You can't place your worker here, please retry.";
    public static final String invalidMoveMessage = "You can't move here, please retry.";
    public static final String invalidBuildingMessage = "You can't build here, please retry.";
    public static final String occupiedBoxMessage = "The chosen box is not empty!";
    //specials
    public static final String moveAgain = "Do you want to move again?";
    public static final String buildOrMove = "Do you want to move or build?";
    public static final String buildAgain = "Do you want to build again?";
    public static final String buildOrDome = "Do you want to build a dome?";

    public StringMessage(Integer virtualViewID, String message) {

        super(virtualViewID);
        this.message = message;

    }

    public String getMessage() {
        return message;
    }

}
