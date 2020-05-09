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


    /**
     * Viene notificato da connection
     * In base al tipo di messaggio che arriva decide di compiere un azione sulla virtual_view
     * @param message
     */
    @Override
    public void update(Move message){
        System.out.println("Received move message !!");
        message.setVirtualViewId(virtualView.getId());

        if (message.isSetFirstPos()) {
            virtualView.setWorker(message);
        }
        else
            virtualView.handleMove(message);
    }

    @Override
    public void update(Build message) {
        System.out.println("Received build message !!");
        message.setVirtualViewId(virtualView.getId());
        virtualView.handleBuild(message);
    }

    @Override
    public void update(String message) {
        System.out.println("Received a string !!");
        if (message.equals("show")) {
            virtualView.showBoard();
        }
    }

    @Override
    public void update(Choice message) {
        System.out.println("Received a chosen card !!");
        message.setVirtualViewID(this.virtualView.getId());
        virtualView.selectCard(message);
    }

    @Override
    public void update(Box[][] message) {

    }

    @Override
    public void update(GameMessage message) {
        //only Server -> Client
    }

    @Override
    public void update(PlayerMessage message) {

    }

    @Override
    public void update(CardsToDisplay message) {

    }
}
