package it.polimi.ingsw.PSP54.utils;

public enum StringToDisplay {

    //init
    welcomeMessage("Welcome!"),
    setNumberOfPlayersMessage("Hey, set the number of player:"),
    turnMessage("It's your turn!"),
    setFirstWorkerMessage("Chose the first worker to place: [Enter m/f]"),
    setSecondWorkerMessage("Set your second worker:"),
    //cards
    cantSelect("You can't chose your card now"),
    apolloMessage("You have Apollo!\nYour Move: Your Worker may move on an occupied cell, changing position with that worker."),
    athenaMessage("You have Athena!\nOpponent's Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn."),
    atlasMessage("You have Atlas!\nYour Build: Your Worker may build a dome at any level."),
    demeterMessage("You have Demeter!\nYour Build: Your Worker may build one additional time, but not on the same space."),
    artemisMessage("You have Artemis!\nYour Move: Your Worker may move one additional time, but not back to its initial space."),
    choseWorker("Chose your worker: [Enter m/f]"),
    moveMessage("Make your move!"),
    buildMessage("Where do you want to build?"),
    waitMessage("Wait for the other player's choice!"),
    winMessage("You win!"),
    loseMessage("You lose!"),
    wrongTurnMessage("It is not your turn!"),
    wrongPlacementMessage("You can't place your worker here, please retry."),
    invalidMoveMessage("You can't move here, please retry."),
    invalidBuildingMessage("Change building, it's not valid!"),
    occupiedBoxMessage("The chosen box is not empty!"),
    //specials
    moveAgain("Do you want to move again?"),
    buildAgain("Do you want to move again?"),
    buildOrDome("Do you want to build a dome?");

    private final String string;

    StringToDisplay(java.lang.String string) {
        this.string = string;
    }

    @Override
    public String toString(){
        return string;
    }
}
