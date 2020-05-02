package it.polimi.ingsw.PSP54.server.virtualView;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.Connection;
import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.utils.Move;
import it.polimi.ingsw.PSP54.utils.Build;
import it.polimi.ingsw.PSP54.utils.PlayerMessage;

public class MessageReceiver implements Observer {
    private Connection connection;
    private VirtualView virtualView;

    public MessageReceiver(Connection connection, VirtualView virtualView) {
        this.connection = connection;
        this.virtualView = virtualView;
    }

    /**
     * Viene notificato da connection
     * In base al tipo di messaggio che arriva decide di compiere un azione sulla virtual_view
     * @param message
     */
    @Override
    public void update(Move message) throws Exception{
        System.out.println("Received move message !!");
        message.setVirtualViewId(virtualView.getVirtualViewId());

        if (message.isSetFirstPos()){
            virtualView.setWorker(message);
        }
        else
            virtualView.handleMove(message);
    }

    @Override
    public void update(Build message) throws Exception {
        System.out.println("Received build message !!");
        message.setVirtualViewId(virtualView.getVirtualViewId());
        virtualView.handleBuild(message);
    }

    @Override
    public void update(String message) throws Exception {
        System.out.println("Received a string !!");
        if (message.equals("show")) {
            virtualView.showBoard();
        }
    }

    @Override
    public void update(Box[][] message) throws Exception {
        return;
    }

    @Override
    public void update(PlayerMessage message) throws Exception {
        return;
    }

}
