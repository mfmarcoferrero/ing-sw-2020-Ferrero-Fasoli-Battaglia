package it.polimi.ingsw.PSP54;

import it.polimi.ingsw.PSP54.client.Client;
import it.polimi.ingsw.PSP54.server.Connection;
import it.polimi.ingsw.PSP54.utils.messages.PingMessage;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.TimerTask;

public class Ping extends TimerTask {
    Connection connection;
    Client client;
    public Ping(Connection connection1){
        connection=connection1;
    }
    public Ping(Client client1){client=client1;};

    @Override
    public void run() {
        if(connection != null)
            connection.asyncSend(new PingMessage(connection.getName()));
        if(client != null)
            client.asyncSend(new PingMessage());
    }
}
