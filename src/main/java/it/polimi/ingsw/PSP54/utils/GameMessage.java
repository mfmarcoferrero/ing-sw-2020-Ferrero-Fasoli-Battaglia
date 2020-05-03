package it.polimi.ingsw.PSP54.utils;

public class GameMessage {

    private int virtualViewID;
    private String message;
    public static String turnMessage = "It's your turn!";
    public static String moveMessage = "Make your move (x,y):";
    public static String waitMessage = "Wait for the other player's move!";
    public static String winMessage = "You win!";
    public static String loseMessage = "You lose!";
    public static String drawMessage = "Draw!";
    public static String wrongTurnMessage = "It is not your turn!";
    public static String invalidMoveMessage = "Change move, it's not valid!";
    public static String invalidBuildingMessage = "Change building, it's not valid!";
    public static String occupiedBoxMessage = "The chosen box is not empty!";
    public static String setFirstWorkerMessage = "Set your first worker \n Enter (x,y):";
    public static String setSecondWorkerMessage = "Set your second worker \n Enter (x,y):";
    public static String choiceMessage = "Do you want to move or build?";

    public GameMessage(int virtualViewID, String message) {
        this.virtualViewID = virtualViewID;
        this.message = message;
    }

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
}
