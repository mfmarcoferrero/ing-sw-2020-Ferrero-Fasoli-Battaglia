package it.polimi.ingsw.PSP54.observer;

import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.utils.*;

public interface Observer {

    //bi-directional
    void update(String message);

    //from Client to Server
    void update(Choice message);

    void update(Move message);

    void update(Build message);

    void update(PlayerMessage message);

    void update(Box[][] message);

    //from Server to Client
    void update(StringToDisplay message);

    void update(GameMessage message);
}
