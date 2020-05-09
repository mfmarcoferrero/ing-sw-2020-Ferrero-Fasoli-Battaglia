package it.polimi.ingsw.PSP54.client;

import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.client.view.*;

import java.util.Random;


public class CLIDebugMain {

    public static void main ( String [] args){

        CliView view = new CliView(null);
        Game game = new Game();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Random selector = new Random();
                int level = selector.nextInt(4);
                game.getBoard()[i][j].setLevel(level);
                game.getBoard()[i][j].setDome(true);
            }
        }


        //print Board
        view.printBoard(game.getBoard());
    }
}