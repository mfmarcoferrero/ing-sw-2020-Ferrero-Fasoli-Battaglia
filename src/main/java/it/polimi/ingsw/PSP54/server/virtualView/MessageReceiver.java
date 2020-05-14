package it.polimi.ingsw.PSP54.server.virtualView;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.Connection;
import it.polimi.ingsw.PSP54.server.model.Player;
import it.polimi.ingsw.PSP54.utils.PlayerAction;
import it.polimi.ingsw.PSP54.utils.choices.CardChoice;
import it.polimi.ingsw.PSP54.utils.choices.PlayerChoice;
import it.polimi.ingsw.PSP54.utils.choices.PlayerCredentials;
import it.polimi.ingsw.PSP54.utils.choices.WorkerChoice;

/**
 * Represent the intermediary between a player's Connection and VirtualView.
 * It handles the incoming player's choices by translating them into actions that will then be performed by the MVC objects.
 */
public class MessageReceiver implements Observer<PlayerChoice> {
    private Connection connection;
    private final VirtualView virtualView;

    public MessageReceiver(Connection connection, VirtualView virtualView) {
        this.connection = connection;
        this.virtualView = virtualView;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    /**
     * Called whenever the observed object is changed.
     *
     * @param message an argument passed to the notify method.
     */
    @Override
    public void update(PlayerChoice message) {
        int id = getVirtualView().getId();
        PlayerAction action = new PlayerAction(id, message);
        getVirtualView().handleAction(action);
    }
}
