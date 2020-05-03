package it.polimi.ingsw.PSP54.server.model;

import java.util.ArrayList;
import java.util.Vector;

/**
 *
 */
public class AthenaDecorator extends GodDecorator{

    private boolean movedUp; //settled on the previous turn
    private final int[] playersPowers = new int[3];

    public AthenaDecorator(Player player) {
        super(player);
    }

    /**
     * Decorates other players with AthenaSideEffectDecorator
     * @param players the vector containing all players
     */
    public void assignAthenaSideEffect(Vector<Player> players){

        int numberOfPlayers = players.capacity();

        for (int i = 0; i < numberOfPlayers; i++) {
            if (players.get(i) != this){
                playersPowers[i] = players.get(i).getCardID();
                players.set(i, new AthenaSideEffectDecorator(players.get(i)));
            }
        }
    }

    /**
     * Redecorates other players with their original decorator
     * @param players the vector containing all players
     */
    public void reassignPreviousPowers(Vector<Player> players){

        int numberOfPlayers = players.capacity();

        for (int i = 0; i < numberOfPlayers; i++) {
            if (players.get(i) != this){
                players.set(i, (players.get(i))); //casts affected players back to StandardPlayer
                players.set(i, players.get(i).assignPower(playersPowers[i])); //redecorates players
            }
        }
    }

    /**
     * If valid performs move and modify action tokes
     * @param worker selected worker which the player wants to move
     * @param dest selected destination box
     * @throws InvalidMoveException if the move can't be done
     */
    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {

        ArrayList<Box> valid = worker.getBoxesToMove();
        int currentMoveToken = worker.getMoveToken();

        if (currentMoveToken > 0 && valid.contains(dest)) {
            if (movedUp) {
                if (dest.getLevel() <= worker.getPos().getLevel()) {
                    this.reassignPreviousPowers(getGame().getPlayers());
                    movedUp = false;
                }
            } else if (dest.getLevel() > worker.getPos().getLevel()) {
                this.assignAthenaSideEffect(getGame().getPlayers());
                movedUp = true;
            }
            super.move(worker, dest);

        }else throw new InvalidMoveException();

    }
}
