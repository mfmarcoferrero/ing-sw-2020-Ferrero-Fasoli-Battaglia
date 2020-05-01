package it.polimi.ingsw.PSP54.observer;

import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.server.model.Player;
import it.polimi.ingsw.PSP54.utils.Build;
import it.polimi.ingsw.PSP54.utils.Move;
import it.polimi.ingsw.PSP54.utils.PlayerMessage;

public interface Observer<T> {

    void update(String message) throws Exception;

    void update(Move message) throws Exception;

    void update(Build message) throws Exception;

    void update(PlayerMessage message) throws Exception;

    void update(Box[][] message) throws Exception;
}
