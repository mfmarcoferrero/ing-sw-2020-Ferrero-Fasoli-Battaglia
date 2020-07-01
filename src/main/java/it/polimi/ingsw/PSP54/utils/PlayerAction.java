package it.polimi.ingsw.PSP54.utils;

import it.polimi.ingsw.PSP54.server.model.Player;
import it.polimi.ingsw.PSP54.utils.choices.PlayerChoice;

import java.io.Serializable;

/**
 * Represents the action of a player. A PlayerAction object is used as a message sent from a VirtualView to the Controller class.
 */
public class PlayerAction implements Serializable,Cloneable {

    private int virtualViewID;
    private PlayerChoice choice;
    private Player player;

    public PlayerAction(int virtualViewID, PlayerChoice choice) {
        this.virtualViewID = virtualViewID;
        this.choice = choice;
    }

    public PlayerAction(Player player) {
        this.player = player;
    }

    public int getVirtualViewID() {
        return virtualViewID;
    }

    public PlayerChoice getChoice() {
        return choice;
    }

    public Player getPlayer() {
        return player;
    }
}
