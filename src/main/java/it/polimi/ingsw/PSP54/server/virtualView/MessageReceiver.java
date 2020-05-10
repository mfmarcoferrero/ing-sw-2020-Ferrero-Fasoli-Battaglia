package it.polimi.ingsw.PSP54.server.virtualView;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.Connection;
import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.utils.*;

/**
 * Class that filters messages arriving from the clients and eventually passes them to the MVC
 */
public class MessageReceiver implements Observer {
    private Connection connection;
    private VirtualView virtualView;

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

    public void setVirtualView(VirtualView virtualView) {
        this.virtualView = virtualView;
    }
}
