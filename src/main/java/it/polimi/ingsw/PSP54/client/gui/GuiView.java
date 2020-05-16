package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.client.Client;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.utils.*;

public class GuiView implements Observer {
    private Client client;

    public GuiView(Client client) {
        this.client = client;
    }

    @Override
    public void update(String message) {
        if (message.equals(GameMessage.welcomeMessage)) {
            System.out.println(message);
        }
        if (message.equals(GameMessage.setNumberOfPlayersMessage)) {
            System.out.println(message);
        }
    }

    @Override
    public void update(Choice message) {

    }

    @Override
    public void update(Move message) {

    }

    @Override
    public void update(Build message) {

    }

    @Override
    public void update(PlayerMessage message) {

    }

    @Override
    public void update(Box[][] message) {

    }

    @Override
    public void update(GameMessage message) {

    }

    @Override
    public void update(CardsToDisplay message) {

    }
}
