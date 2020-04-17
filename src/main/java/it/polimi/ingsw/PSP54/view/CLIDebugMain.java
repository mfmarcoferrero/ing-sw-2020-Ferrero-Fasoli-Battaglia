package it.polimi.ingsw.PSP54.view;

import it.polimi.ingsw.PSP54.view.*;
import it.polimi.ingsw.PSP54.model.*;

public class CLIDebugMain {

    public static void main ( String [] args){

        CliView view = new CliView();
        Game game = new Game();

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++) {
                game.getBoard()[i][j].setLevel(3);
                game.getBoard()[i][j].setDome(false);
            }


        view.printBoard(game.getBoard());

    }

}
