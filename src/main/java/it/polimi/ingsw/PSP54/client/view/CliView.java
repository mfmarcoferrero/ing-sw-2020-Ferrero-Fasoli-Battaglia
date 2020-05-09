package it.polimi.ingsw.PSP54.client.view;

import it.polimi.ingsw.PSP54.client.Client;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.utils.*;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class CliView implements Observer {

	private final Scanner inputReader = new Scanner(System.in);
	private final PrintStream output = new PrintStream(System.out);
	private Client client;
	private boolean maleSelected;
	private static final String workerSelection = "Select your worker: [Enter m/f]";

	public CliView(Client client) {
		this.client = client;
	}

	/**
	 *Returns the correct character to print in the center of every box, it's called by printBoard(Box[][] board)
	 * @param box current box which is being printed
	 * @return the String containing ANSI/UNICODE codes representing the Dome, the Workers or the level of the current box
	 */
	private String getBoxCenter(Box box){

		String background = null;
		String string = "";

		switch (box.getLevel()){
			case 0 :
				background = Color.ANSI_GROUND_BACKGROUND.toString();
				break;
			case 1 :
				background = Color.ANSI_FIRST_BACKGROUND.toString();
				break;
			case 2 :
				background = Color.ANSI_SECOND_BACKGROUND.toString();
				break;
			case 3 :
				background = Color.ANSI_THIRD_BACKGROUND.toString();
				break;
		}

		if(box.isDome())
			string = string + Color.ANSI_DOME_BACKGROUND + Color.ANSI_DOME + Symbol.UNICODE_DOME;

		else if(box.getWorker() != null) {
			switch (box.getWorker().getOwner().getColor()) {
				case "blue":
					if (box.getWorker().getMale())
						string = string + background + Color.ANSI_P1 + Symbol.UNICODE_MALE_WORKER;
					else
						string = string + background + Color.ANSI_P1 + Symbol.UNICODE_FEMALE_WORKER;
					break;
				case "red":
					if (box.getWorker().getMale())
						string = string + Color.ANSI_P2 + Symbol.UNICODE_MALE_WORKER;
					else
						string = string + Color.ANSI_P2 + Symbol.UNICODE_FEMALE_WORKER;

					break;
				case "yellow":
					if (box.getWorker().getMale())
						string = string + Color.ANSI_P3 + Symbol.UNICODE_MALE_WORKER;
					else
						string = string + Color.ANSI_P3 + Symbol.UNICODE_FEMALE_WORKER;

					break;
			}
		}else
			string = Symbol.UNICODE_SQUARE.toString();
		return string + background;
	}

	/**
	 * Prints the ground line of every box in the same line of the board
	 * @param initGround String containing the left border and the ANSI code for the ground
	 * @param endGround String containing the right border
	 */
	private void printGround(String initGround, String endGround) {
		StringBuilder line;
		StringBuilder toPrint = new StringBuilder();
		for (int j = 0; j < 5; j++) {
			line = new StringBuilder(initGround);
			for (int k = 0; k < 9; k++) {
				line.append(Symbol.UNICODE_SQUARE);
			}
			line.append(endGround);
			toPrint.append(line);
		}
		output.println(toPrint);
	}

	/**
	 *prints the board
	 * @param board bi-dimensional array of boxes representing the game board
	 */
	public void printBoard (Box[][] board) {

		StringBuilder line = null;
		StringBuilder toPrint;

		//String containing upper border
		String upperBorder = Color.ANSI_BACKGROUND_RESET.toString() + Color.ANSI_BORDER
				+ "⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽⎽"
				+ Color.ANSI_BACKGROUND_RESET;

		//String containing lower border
		String lowerBorder = Color.ANSI_BACKGROUND_RESET.toString() + Color.ANSI_BORDER
				+ "⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺"
				+ Color.ANSI_RESET;

		//String containing middle border
		String middleBorder = Color.ANSI_BACKGROUND_RESET.toString() + Color.ANSI_BORDER
				+ "⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼";

		//Strings containing beginning/end of lines, it contains border and blocks with corresponding colors
		String initGround = Color.ANSI_BACKGROUND_RESET.toString() + Color.ANSI_BORDER + "|" + Color.ANSI_GROUND_BACKGROUND + Color.ANSI_GROUND;
		String endGround = Color.ANSI_BACKGROUND_RESET.toString() + Color.ANSI_BORDER + "|";

		String initFirst = initGround + Symbol.UNICODE_SQUARE + Color.ANSI_FIRST_BACKGROUND + Color.ANSI_FIRST ;
		String endFirst = Color.ANSI_GROUND_BACKGROUND.toString() + Color.ANSI_GROUND.toString() + Symbol.UNICODE_SQUARE + endGround;

		String initSecond = initFirst + Symbol.UNICODE_SQUARE + Color.ANSI_SECOND_BACKGROUND + Color.ANSI_SECOND;
		String endSecond = Color.ANSI_FIRST_BACKGROUND.toString() + Color.ANSI_FIRST.toString() + Symbol.UNICODE_SQUARE + endFirst;

		String initThird = initSecond + Symbol.UNICODE_SQUARE + Color.ANSI_THIRD_BACKGROUND + Color.ANSI_THIRD;
		String endThird = Color.ANSI_SECOND_BACKGROUND.toString() + Color.ANSI_SECOND.toString() + Symbol.UNICODE_SQUARE + endSecond;

		//print border and first ground level
		output.println(upperBorder);

		//print Board
		for (int i = 0; i < 5; i++) {

			//print the first line
			printGround(initGround, endGround);

			int l = 0;

			//print the seven conditioned line depending on level of the box or on the presence of a dome/worker in it
			while (l < 7) {

				//generate and print the five strings containing the representation of the of the first row of boxes
				if (l == 0 || l == 6) {
					toPrint = new StringBuilder();
					for (int j = 0; j < 5; j++) {
						if (board[i][j].getLevel() == 0) {
							line = new StringBuilder(initGround);
							for (int k = 0; k < 9; k++) {
								line.append(Symbol.UNICODE_SQUARE);
							}
							line.append(endGround);
						} else if (board[i][j].getLevel() > 0) {
							line = new StringBuilder(initFirst);
							for (int k = 0; k < 7; k++) {
								line.append(Symbol.UNICODE_SQUARE);
							}
							line.append(endFirst);
						}
						toPrint.append(line);
					}
					output.println(toPrint.toString());
					l++;
				}
				else if (l == 1 || l == 5){
					toPrint = new StringBuilder();
					for (int j = 0; j < 5; j++) {
						if (board[i][j].getLevel() == 0) {
							line = new StringBuilder(initGround);
							for (int k = 0; k < 9; k++) {
								line.append(Symbol.UNICODE_SQUARE);
							}
							line.append(endGround);
						} else if (board[i][j].getLevel() == 1) {
							line = new StringBuilder(initFirst);
							for (int k = 0; k < 7; k++) {
								line.append(Symbol.UNICODE_SQUARE);
							}
							line.append(endFirst);
						}else if (board[i][j].getLevel() > 1){
							line = new StringBuilder(initSecond);
							for (int k = 0; k < 5; k++) {
								line.append(Symbol.UNICODE_SQUARE);
							}
							line.append(endSecond);
						}
						toPrint.append(line);
					}
					output.println(toPrint.toString());
					l++;
				}
				else if (l == 2 || l == 4){
					toPrint = new StringBuilder();
					for (int j = 0; j < 5; j++) {
						if (board[i][j].getLevel() == 0) {
							line = new StringBuilder(initGround);
							for (int k = 0; k < 9; k++) {
								line.append(Symbol.UNICODE_SQUARE);
							}
							line.append(endGround);
						} else if (board[i][j].getLevel() == 1) {
							line = new StringBuilder(initFirst);
							for (int k = 0; k < 7; k++) {
								line.append(Symbol.UNICODE_SQUARE);
							}
							line.append(endFirst);
						}else if (board[i][j].getLevel() == 2){
							line = new StringBuilder(initSecond);
							for (int k = 0; k < 5; k++) {
								line.append(Symbol.UNICODE_SQUARE);
							}
							line.append(endSecond);
						}else if (board[i][j].getLevel() == 3){
							line = new StringBuilder(initThird);
							for (int k = 0; k < 3; k++) {
								line.append(Symbol.UNICODE_SQUARE);
							}
							line.append(endThird);
						}
						toPrint.append(line);
					}
					output.println(toPrint.toString());
					l++;
				}
				else if (l == 3){
					toPrint = new StringBuilder();
					for (int j = 0; j < 5; j++){
						if (board[i][j].getLevel() == 0) {
							line = new StringBuilder(initGround);
							for (int k = 0; k < 4; k++) {
								line.append(Symbol.UNICODE_SQUARE);
							}
							String center = getBoxCenter(board[i][j]);
							line.append(center).append(Color.ANSI_GROUND);
							for (int k = 0; k < 4; k++) {
								line.append(Symbol.UNICODE_SQUARE);
							}
							line.append(endGround);
						} else if (board[i][j].getLevel() == 1) {
							line = new StringBuilder(initFirst);
							for (int k = 0; k < 3; k++) {
								line.append(Symbol.UNICODE_SQUARE);
							}
							String center = getBoxCenter(board[i][j]);
							line.append(center).append(Color.ANSI_FIRST);
							for (int k = 0; k < 3; k++) {
								line.append(Symbol.UNICODE_SQUARE);
							}
							line.append(endFirst);
						}else if (board[i][j].getLevel() == 2){
							line = new StringBuilder(initSecond);
							for (int k = 0; k < 2; k++) {
								line.append(Symbol.UNICODE_SQUARE);
							}
							String center = getBoxCenter(board[i][j]);
							line.append(center).append(Color.ANSI_SECOND);
							for (int k = 0; k < 2; k++) {
								line.append(Symbol.UNICODE_SQUARE);
							}
							line.append(endSecond);
						}else if (board[i][j].getLevel() == 3){
							line = new StringBuilder(initThird);
							line.append(Symbol.UNICODE_SQUARE);
							String center = getBoxCenter(board[i][j]);
							line.append(center).append(Color.ANSI_THIRD);
							line.append(Symbol.UNICODE_SQUARE);
							line.append(endThird);
						}
						toPrint.append(line);
					}
					output.println(toPrint.toString());
					l++;
				}
			}

			//print the last line
			printGround(initGround, endGround);
			if (i < 4)
				output. println(middleBorder);
		}

		//print lower border
		output.println(lowerBorder);
	} //TODO: insert coordinates along borders?

	/**
	 *asks player which worker he wants to use
	 * @return true if the choice is the male worker, false if it's the female Worker
	 */
	public boolean acquireWorkerSelection() {

		boolean loop = true;
		Boolean male = null;

		while (loop) {
			String workerSex = inputReader.next();
			if(workerSex.equals("m")) {
				male = true;
				loop = false;
			}
			else if(workerSex.equals("f")){
				male = false;
				loop = false;
			} else
				output.println("Incorrect Input!");
		}
		return male;
	}

	/**
	 * asks coordinates which player wants to move
	 * @return an array containing the selected coordinates
	 */
	public int[] acquireCoordinates() {


		int[] coordinates = new int[2];

		output.println("Enter cell coordinates");
		//set x
		output.println("x:");
		int y = getCoordinate();
		//set y
		output.println("y:");
		int x = getCoordinate();

		coordinates[0] = x;
		coordinates[1] = y;

		return coordinates;
	}

	private int getCoordinate() {
		int k = 0;
		boolean loop = true;
		while (loop) {
			String component  = inputReader.next();
			try{
				k = Integer.parseInt(component);
				if (0 < k && k < 6)
					loop = false;
				else
					output.println("Incorrect Input!");
			} catch (IllegalArgumentException e) {
				output.println("Incorrect input!");
			}
		}
		return k - 1; //return coordinate translated to array index
	}

	public void acquirePlayerCredentials() {
		output.println("What's your name?");
		String name = inputReader.next();
		output.println("What's your age?");
		int age = acquireInteger();
		PlayerMessage playerMessage = new PlayerMessage(name,age,0);
		client.asyncWriteToSocket(playerMessage);
	}

	public void acquireNumberOfPlayers() {
		int numberOfPlayers;
		numberOfPlayers = acquireInteger();
		while (numberOfPlayers < 2 || numberOfPlayers > 3) {
			output.println("Illegal number of player! It must be '2' or '3', try again");
			numberOfPlayers = inputReader.nextInt();
		}
		client.asyncWriteToSocket(numberOfPlayers);
	}

	/**
	 * Asks an integer until input is valid.
	 * @return the given number.
	 */
	public int acquireInteger() {
		boolean loop = true;
		int i = 0;
		while (loop) {
			String toParse = inputReader.next();
			try{
				i = Integer.parseInt(toParse);
				loop = false;
			}catch (IllegalArgumentException e){
				output.println("Incorrect input, enter an integer!");
			}
		}
		return i;
	}

	/**
	 * Creates a message for the player containing the extracted cards
	 */
	public void displayCards(HashMap<Integer,String> extractedCards){
		Vector<String> cardsName = new Vector<>(extractedCards.values());
		Vector<Integer> cardsValues = new Vector<>(extractedCards.keySet());
		boolean found = false;

		if (extractedCards.size() == 1){
			output.println("You are the last player so you can't choose");
			client.asyncWriteToSocket(new Choice(cardsValues.get(0)));
		}
		else {
			output.println("Choose your card: (write the name of the card you want)");
			for (int i = 0; i < cardsName.size(); i++) {
				output.println((i + 1) + ") " + cardsName.get(i));
			}

			String chosenCard = inputReader.next();
			while (!found) {
				for (int i = 0; i < cardsName.size(); i++) {
					if (chosenCard.equals(cardsName.get(i))) {
						client.asyncWriteToSocket(new Choice(cardsValues.get(i)));
						found = true;
						break;
					}
				}
				if (!found) {
					output.println("Invalid input: (write the name of the card you want)");
					chosenCard = inputReader.next();
				}
			}
		}
	}

	public void sendWorkerPlacement(boolean male) {
		int [] coordinates = acquireCoordinates();
		Move move = new Move(male, coordinates[0], coordinates[1]);
		move.setSetFirstPos(true);
		client.asyncWriteToSocket(move);
	}

	public void sendMove(boolean male) {
		int[] coordinates = acquireCoordinates();
		Move move = new Move(male, coordinates[0], coordinates[1]);
		client.asyncWriteToSocket(move);
	}

	@Override
	public void update(String message) {
		output.println(message);
		if (message.equals(GameMessage.welcomeMessage)) {
			acquirePlayerCredentials();
		}
		if (message.equals(GameMessage.setNumberOfPlayersMessage)) {
			acquireNumberOfPlayers();
		}
		if (message.equals(GameMessage.setFirstWorkerMessage)){
			output.println(workerSelection);
			setMaleSelected(acquireWorkerSelection());
			sendWorkerPlacement(isMaleSelected());
		}
		if (message.equals(GameMessage.setSecondWorkerMessage)){
			sendWorkerPlacement(!isMaleSelected());
		}
		if (message.equals(GameMessage.moveMessage)){
			output.println(workerSelection);
			setMaleSelected(acquireWorkerSelection());
			sendMove(isMaleSelected());
		}
		if (message.equals(GameMessage.invalidMoveMessage)){
			sendMove(isMaleSelected());
		}

	}

	@Override
	public void update(Box[][] message) {
		printBoard(message);
	}

	@Override
	public void update(CardsToDisplay message) {
		displayCards(message.getExtractedCards());
	}

	@Override
	public void update(GameMessage message) {

	}

	@Override
	public void update(Choice message) {

	}

	@Override
	public void update(Move message){

	}

	@Override
	public void update(Build message){

	}

	@Override
	public void update(PlayerMessage message){

	}

	//setters & getters


	public boolean isMaleSelected() {
		return maleSelected;
	}

	public void setMaleSelected(boolean maleSelected) {
		this.maleSelected = maleSelected;
	}
}
