package it.polimi.ingsw.PSP54.view;

import it.polimi.ingsw.PSP54.model.Game;
import it.polimi.ingsw.PSP54.view.CliView;

import java.util.Random;

public class CLIDebugMain {

    public static void main ( String [] args){

        CliView view = new CliView();
        Game game = new Game();

        game.startGame();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                game.getBoard()[i][j].setLevel(3);
                game.getBoard()[i][j].setDome(false);
            }
        }
        //add player
        game.newPlayer(view.acquireName(), view.acquireAge(), "blue");
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
