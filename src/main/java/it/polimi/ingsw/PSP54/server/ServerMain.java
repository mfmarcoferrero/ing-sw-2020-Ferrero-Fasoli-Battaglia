package it.polimi.ingsw.PSP54.server;
import it.polimi.ingsw.PSP54.server.controller.*;
import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.server.model.Player;
import it.polimi.ingsw.PSP54.server.virtualView.VirtualView;


public class ServerMain {
    public static void main(String[] args) {
        VirtualView virtualView = new VirtualView();
        Game game = new Game();
        Controller controller = new Controller(game,virtualView);
        virtualView.addObserver(controller);
        game.addObserver(virtualView);

        virtualView.addPlayer(new Player("Marco",22,null,null));
        virtualView.addPlayer(new Player("Alessandro",22,null,null));
        virtualView.addPlayer(new Player("Matteo",22,null,null));
        virtualView.addPlayer(new Player("Giovanni",22,null,null));

    }
}
