package it.polimi.ingsw.PSP54;

import it.polimi.ingsw.PSP54.model.Game;
import it.polimi.ingsw.PSP54.view.CliView;

public class TestMain {

    public static void main ( String [] args){

        CliView view = new CliView();
        Game game = new Game();

        game.startGame();

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++) {
                game.getBoard()[i][j].setLevel(0);
                game.getBoard()[i][j].setDome(true);
            }


        view.printBoard(game.getBoard());

    }

}
