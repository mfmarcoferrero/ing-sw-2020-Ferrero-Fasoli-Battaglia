package it.polimi.ingsw.PSP54.server.virtualView;

import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.Connection;
import it.polimi.ingsw.PSP54.utils.Move;
import it.polimi.ingsw.PSP54.utils.Build;

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
    public void update(Object message) {
        System.out.println("Received not default message !!");

        if (message instanceof Move) {
            virtualView.handleMove((Move) message);
        }
        if (message instanceof Build) {
            virtualView.handleBuild((Build) message);
        }
        if (message instanceof  String) {
            if (message.equals("show")) {
                virtualView.showBoard();
            }
        }
    }
}
