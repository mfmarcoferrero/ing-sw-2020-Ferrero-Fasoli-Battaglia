package it.polimi.ingsw.PSP54.utils;

import java.io.Serializable;
import java.util.HashMap;

public class GameMessage implements Serializable,Cloneable {

    private int virtualViewID;
    private String message;
    private HashMap<Integer, String> cards;
    public static String turnMessage = "It's your turn!";
    public static String powersSet = "All powers are set!";
    public static String welcomeMessage = "Welcome!";
    public static String powerReceived = "I've received my power !!";
    public static String setNumberOfPlayersMessage = "Hey, set the number of player:";
    public static String cantSelect = "You can't chose your card now";
    public static String apolloMessage = "You have Apollo!\nYour Move: Your Worker may move on an occupied cell, changing position with that worker.";
    public static String athenaMessage = "You have Athena!\nOpponent's Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";
    public static String atlasMessage = "You have Atlas!\nYour Build: Your Worker may build a dome at any level.";
    public static String demeterMessage = "You have Demeter!\nYour Build: Your Worker may build one additional time, but not on the same space.";
    public static String artemisMessage = "You have Artemis!\nYour Move: Your Worker may move one additional time, but not back to its initial space.";
    public static String firstPlacement = "Chose which worker to place first: [enter 'm' or 'f']";
    public static String moveMessage = "Make your move!";
    public static String buildMessage = "Where do you want to build?";
    public static String waitMessage = "Wait for the other player's choice!";
    public static String winMessage = "You win!";
    public static String loseMessage = "You lose!";
    public static String wrongTurnMessage = "It is not your turn!";
    public static String wrongPlacementMessage = "You can't place your worker here, please retry.";
    public static String invalidMoveMessage = "Change move, it's not valid!";
    public static String invalidBuildingMessage = "Change building, it's not valid!";
    public static String occupiedBoxMessage = "The chosen box is not empty!";
    public static String setFirstWorkerMessage = "Set your first worker:";
    public static String setSecondWorkerMessage = "Set your second worker:";
    public static String choiceMessage = "Do you want to move or build?";
    public static String buildOrDome = "Do you want to build a dome?";

    public GameMessage(int virtualViewID, String message) {
        this.virtualViewID = virtualViewID;
        this.message = message;
    }

    //getters & setters

    public int getVirtualViewID() {
        return virtualViewID;
    }

    public void setVirtualViewID(int virtualViewID) {
        this.virtualViewID = virtualViewID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<Integer, String> getCards() {
        return cards;
    }

    public void setCards(HashMap<Integer, String> cards) {
        this.cards = cards;
    }
}
