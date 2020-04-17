package it.polimi.ingsw.PSP54.model;

import java.util.Vector;

//TODO: Test
public class AthenaDecorator extends GodDecorator{

    private boolean movedUp; //settled on the previous turn
    private final int[] playersPowers = new int[3];

    public AthenaDecorator(Player player) {
        super(player);
    }

    /*if one of your worked moved up on your last turn, opponent players cannot move up this turn

    PSEUDOCODE:

    move:

        if (goesUp)
            if (!movedUp)
                assignAthenaSideEffectDecorator(player)
                movedUp = true

        else
            reassignPreviousPowers


    assign:

        assignSideEffect(game.players)

    */

    //TODO:JavaDoc
    public void assignAthenaSideEffect(Vector<Player> players){

        int numberOfPlayers = players.capacity();

        for (int i = 0; i < numberOfPlayers; i++) {
            if (players.get(i) != this){
                playersPowers[i] = players.get(i).getCardID();
                players.set(i, new AthenaSideEffectDecorator(players.get(i)));
            }
        }
    }

    //TODO:JavaDoc
    public void reassignPreviousPowers(Vector<Player> players){

        int numberOfPlayers = players.capacity();

        for (int i = 0; i < numberOfPlayers; i++) {
            if (players.get(i) != this){
                players.set(i, (players.get(i))); //casts affected players back to StandardPlayer
                players.set(i, players.get(i).assignPower(playersPowers[i])); //redecorates players
            }
        }
    }

    //TODO:JavaDoc
    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {

        Vector<Box> valid = worker.getBoxesToMove();
        int currentMoveToken = worker.getMoveToken();

        if (currentMoveToken >= 0 && valid.contains(dest)){
            if (dest.getLevel() == worker.getPos().getLevel()+1)
                if (!movedUp){
                    this.assignAthenaSideEffect(getGame().getPlayers());
                    movedUp = true;
                }
            else
                this.reassignPreviousPowers(getGame().getPlayers());

            //perform move
            worker.setPos(dest);
            dest.setWorker(worker);
            //decrement token
            worker.setMoveToken(currentMoveToken-1);
            //set buildable boxes
            worker.setBoxesToBuild(setWorkerBoxesToBuild(worker));
        }else throw new InvalidMoveException();
    }

    //Only for debug purpose
    @Override
    public void addSideEffect() {
        this.assignAthenaSideEffect(getGame().getPlayers());

    }

    @Override
    public void rmvSideEffect() {
        this.reassignPreviousPowers(getGame().getPlayers());
    }

    @Override
    public void printPower() {
        System.out.println("Athena");
    }
}
