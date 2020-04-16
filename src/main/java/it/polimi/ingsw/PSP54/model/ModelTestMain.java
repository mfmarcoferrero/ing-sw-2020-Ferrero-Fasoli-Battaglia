package it.polimi.ingsw.PSP54.model;

import java.io.PrintStream;
import java.util.Scanner;

public class ModelTestMain {

    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        PrintStream output = new PrintStream(System.out);

        Game game = new Game();

        //creates Players
        for (int i = 0; i < 3; i++) {
            output.println("enter name:");
            game.newPlayer(input.next());
            output.println("enter age:");
            game.getPlayers().get(i).setAge(input.nextInt());
        }

        int numberOfPLayers = game.getPlayers().capacity();

        //foreach player sets a reference to current game
        for (int i = 0; i < numberOfPLayers; i++) {
            game.getPlayers().get(i).setGame(game);
        }

        game.sortPlayers(game.getPlayers());

        game.assignColors(game.getPlayers());

        //extract 3 unique random cards
        int[] extractedCards = game.extractCards();

        //TODO: every player must chose only between extracted cards and an already taken card cannot be chosen
        for (int i = 0; i < 2; i++) {
            game.getPlayers().set(i , game.getPlayers().get(i).assignPower(extractedCards[i]));
        }
        game.getPlayers().set(2 , game.getPlayers().get(2).assignPower(2));

        printPlayersSpecs(output, game, numberOfPLayers);

        game.getPlayers().get(2).addSideEffect();

        System.out.println();

        printPlayersSpecs(output, game, numberOfPLayers);

        game.getPlayers().get(2).rmvSideEffect();

        System.out.println();

        printPlayersSpecs(output, game, numberOfPLayers);

    }

    private static void printPlayersSpecs(PrintStream output, Game game, int numberOfPLayers) {
        for (int i = 0; i < numberOfPLayers; i++) {
            output.println("player number " + (i+1) + " is: " + game.getPlayers().get(i).getPlayerName() + ", age: "+ game.getPlayers().get(i).getAge() + ", color: " + game.getPlayers().get(i).getColor());
            output.print("Player's power: ");
            game.getPlayers().get(i).printPower();
        }

        System.out.println();
    }
}
