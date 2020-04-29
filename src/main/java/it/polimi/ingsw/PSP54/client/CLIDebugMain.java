package it.polimi.ingsw.PSP54.client;

import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.client.view.*;
import it.polimi.ingsw.PSP54.server.model.IllegalNumberOfPlayersException;

import java.util.Random;


public class CLIDebugMain {

    public static void main ( String [] args){

        CliView view = new CliView();
        Game game = new Game();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Random selector = new Random();
                int level = selector.nextInt(4);
                game.getBoard()[i][j].setLevel(level);
                game.getBoard()[i][j].setDome(true);
            }
        }
        //add player
        try {
            game.newPlayer(view.acquireName(), view.acquireAge(), "blue");
        } catch (IllegalNumberOfPlayersException e) {
            e.printStackTrace();
        }
        //set worker coordinates
        int[] coordinates = view.acquireCoordinates();
        //set worker 1 in coordinates
        game.getBoard()[coordinates[0]][coordinates[1]].setWorker(game.getPlayers().get(0).getWorkerList().get(0));

        //print worker color
        System.out.println(game.getPlayers().get(0).getWorkerList().get(0).getColor());
        //print Board
        view.printBoard(game.getBoard());
    }
}