package it.polimi.ingsw.PSP54.utils.messages;

import it.polimi.ingsw.PSP54.server.model.Player;

import java.util.Vector;

/**
 * Represents the GameMessage containing all players of the current game.
 */
public class PlayersMessage extends GameMessage{
    private final Vector<Player> players;

    public PlayersMessage(Integer virtualViewID, Vector<Player> players) {
        super(virtualViewID);
        this.players = players;
    }

    public Vector<Player> getPlayers() {
        return players;
    }
}
