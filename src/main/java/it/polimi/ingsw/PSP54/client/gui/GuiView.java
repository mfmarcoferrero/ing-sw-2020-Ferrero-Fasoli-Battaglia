package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.client.Client;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;

public class GuiView implements Observer<GameMessage> {
    private final Client client;

    public GuiView(Client client) {
        this.client = client;
    }

    @Override
    public void update(GameMessage message) {
        if (message instanceof StringMessage){
            String stringMessage = ((StringMessage) message).getMessage();
            if (stringMessage.equals(StringMessage.welcomeMessage)) {
                System.out.println(message);
            }
            if (stringMessage.equals(StringMessage.setNumberOfPlayersMessage)) {
                System.out.println(message);
            }
        }

    }


}
