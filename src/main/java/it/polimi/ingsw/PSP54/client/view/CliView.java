package it.polimi.ingsw.PSP54.client.view;

import it.polimi.ingsw.PSP54.client.Client;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.server.model.Player;
import it.polimi.ingsw.PSP54.utils.choices.*;
import it.polimi.ingsw.PSP54.utils.messages.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.PrintStream;
import java.text.AttributedCharacterIterator;
import java.util.*;

public class CliView extends java.applet.Applet implements Observer<GameMessage> {

	private final Scanner inputReader = new Scanner(System.in);
	private final PrintStream output = new PrintStream(System.out);
	private final Client client;
	private HashMap<String, Integer> Credentials;
	private static int numberOfPlayers;
	private boolean maleSelected;

	public CliView(Client client) {
		this.client = client;
	}

	/**
	 * Returns the correct character to print in the center of every box, it's called by printBoard(Box[][] board).
	 * @param box current box which is being printed.
	 * @return the String containing ANSI/UNICODE codes representing the Dome, the Workers or the level of the current box.
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
	 * Prints the ground line of every box in the same line of the board.
	 * @param initGround String containing the left border and the ANSI code for the ground.
	 * @param endGround String containing the right border.
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
	 * Prints the board.
	 * @param board bi-dimensional array of boxes representing the game board.
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
	 * Acquires username and age of the player from command line and stores them in the credentials HashMap.
	 */
	public void acquirePlayerCredentials() {
		output.println("What's your name?");
		String name = inputReader.next();
		output.println("What's your age?");
		int age = acquireInteger();

		HashMap<String, Integer> credentials = new HashMap<>();
		credentials.put(name, age);
		setCredentials(credentials);
	}

	/**
	 * Creates a PlayerChoice object containing player's credentials and sends it via socket.
	 * @param credentials the HashMap containing player's name and age.
	 */
	public void sendPlayerCredentials(HashMap<String, Integer> credentials) {

		for (Map.Entry<String, Integer> map : credentials.entrySet()){
			String name = map.getKey();
			int age = map.getValue();
			PlayerChoice playerCredentials = new PlayerCredentials(name, age);
			client.asyncSend(playerCredentials);
		}

	}

	public void NewName(PlayerCredentials credential){
		String name = inputReader.next();
		credential.setName(name);
		HashMap<String, Integer> credentials = new HashMap<>();
		credentials.put(credential.getPlayerName(),credential.getAge());
		setCredentials(credentials);
	}

	/**
	 * Acquires game's number of players and stores it into a local variable.
	 */
	public void acquireNumberOfPlayers() {
		int numberOfPlayers = acquireInteger();
		while (numberOfPlayers < 2 || numberOfPlayers > 3) {
			output.println("Illegal number of player! It must be '2' or '3', try again");
			numberOfPlayers = inputReader.nextInt();
		}
		setNumberOfPlayers(numberOfPlayers);
	}

	/**
	 * Sends the number of players via socket.
	 * @param numberOfPlayers the number of players.
	 */
	public void sendNumberOfPlayers(int numberOfPlayers){
		client.asyncSend(numberOfPlayers);
	}

	/**
	 * Asks player which worker he wants to use.
	 * @return true if the choice is the male worker, false if it's the female Worker.
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
	 * Keeps asking the user for a string ("y" or "n") until correct input is entered.
	 * @return true if the given string is "y", false otherwise.
	 */
	public boolean acquireBooleanChoice() {
		boolean loop = true;
		boolean choice = false;
		while (loop) {
			String dome = inputReader.next();
			if(dome.equals("y")) {
				choice = true;
				loop = false;
			}
			else if(dome.equals("n")){
				loop = false;
			} else
				output.println("Incorrect Input!");
		}
		return choice;
	}

	/**
	 * Asks coordinates which player wants to move.
	 * @return an array containing the selected coordinates.
	 */
	public int[] acquireCoordinates() {
		int[] coordinates = new int[2];

		output.println("Enter cell coordinates.");
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

	/**
	 * Asks an integer lower than 6 and greater than 0 until the input is correct.
	 * @return the given input.
	 */
	private int getCoordinate() {
		int k = 0;
		boolean loop = true;
		while (loop) {
			String component  = inputReader.next();
			try {
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
			} catch (IllegalArgumentException e){
				output.println("Incorrect input, enter an integer!");
			}
		}
		return i;
	}

	/**
	 * Asks the player which power he wants and sends the choice via socket.
	 */
	public void acquireCardSelection(HashMap<Integer,String> extractedCards){
		Vector<String> cardsName = new Vector<>(extractedCards.values());
		Vector<Integer> cardsValues = new Vector<>(extractedCards.keySet());
		boolean found = false;

		if (extractedCards.size() == 1){
			PlayerChoice cardChoice = new CardChoice(cardsValues.get(0));
			client.asyncSend(cardChoice);
		}
		else {
			output.println("Choose your card: [Enter the name of the God]");
			for (int i = 0; i < cardsName.size(); i++) {
				output.println((i + 1) + ") " + cardsName.get(i));
			}
			String chosenCard = inputReader.next();
			while (!found) {
				for (int i = 0; i < cardsName.size(); i++) {
					if (chosenCard.equals(cardsName.get(i))) {
						PlayerChoice cardChoice = new CardChoice(cardsValues.get(i));
						client.asyncSend(cardChoice);
						found = true;
						break;
					}
				}
				if (!found) {
					output.println("Invalid input! [Enter the name of the God]");
					chosenCard = inputReader.next();
				}
			}
		}
	}

	/**
	 * Sends a WorkerChoice object via socket.
	 */
	public void sendWorkerSelection(){
		PlayerChoice workerSelection = new WorkerChoice(isMaleSelected());
		client.asyncSend(workerSelection);
	}

	/**
	 * Sends a MoveChoice object via socket.
	 * @param coordinates an array containing the destination cell's coordinates.
	 */
	public void sendMove(int[] coordinates) {
		PlayerChoice move = new MoveChoice(coordinates[0], coordinates[1]);
		client.asyncSend(move);
	}

	/**
	 * Sends a BuildChoice object via socket.
	 * @param coordinates an array containing the destination cell's coordinates.
	 */
	public void sendBuild(int[] coordinates) {
		PlayerChoice build = new BuildChoice(coordinates[0], coordinates[1]);
		client.asyncSend(build);
	}

	/**
	 * Sends a BooleanChoice object via socket.
	 * @param choice the choice made by the user.
	 */
	public void sendBooleanChoice(boolean choice) {
		PlayerChoice booleanChoice = new BooleanChoice(choice);
		client.asyncSend(booleanChoice);
	}

	public void EndOfmatch(Player p){
		Graphics win = new Graphics() {
			@Override
			public Graphics create() {
				return null;
			}

			@Override
			public void translate(int x, int y) {

			}

			@Override
			public java.awt.Color getColor() {
				return null;
			}

			@Override
			public void setColor(java.awt.Color c) {

			}

			@Override
			public void setPaintMode() {

			}

			@Override
			public void setXORMode(java.awt.Color c1) {

			}

			@Override
			public java.awt.Font getFont() {
				return null;
			}

			@Override
			public void setFont(java.awt.Font font) {

			}

			@Override
			public FontMetrics getFontMetrics(java.awt.Font f) {
				return null;
			}

			@Override
			public Rectangle getClipBounds() {
				return null;
			}

			@Override
			public void clipRect(int x, int y, int width, int height) {

			}

			@Override
			public void setClip(int x, int y, int width, int height) {

			}

			@Override
			public Shape getClip() {
				return null;
			}

			@Override
			public void setClip(Shape clip) {

			}

			@Override
			public void copyArea(int x, int y, int width, int height, int dx, int dy) {

			}

			@Override
			public void drawLine(int x1, int y1, int x2, int y2) {

			}

			@Override
			public void fillRect(int x, int y, int width, int height) {

			}

			@Override
			public void clearRect(int x, int y, int width, int height) {

			}

			@Override
			public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

			}

			@Override
			public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

			}

			@Override
			public void drawOval(int x, int y, int width, int height) {

			}

			@Override
			public void fillOval(int x, int y, int width, int height) {

			}

			@Override
			public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

			}

			@Override
			public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

			}

			@Override
			public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {

			}

			@Override
			public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {

			}

			@Override
			public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {

			}

			@Override
			public void drawString(String str, int x, int y) {

			}

			@Override
			public void drawString(AttributedCharacterIterator iterator, int x, int y) {

			}

			@Override
			public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
				return false;
			}

			@Override
			public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
				return false;
			}

			@Override
			public boolean drawImage(Image img, int x, int y, java.awt.Color bgcolor, ImageObserver observer) {
				return false;
			}

			@Override
			public boolean drawImage(Image img, int x, int y, int width, int height, java.awt.Color bgcolor, ImageObserver observer) {
				return false;
			}

			@Override
			public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
				return false;
			}

			@Override
			public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, java.awt.Color bgcolor, ImageObserver observer) {
				return false;
			}

			@Override
			public void dispose() {

			}
		};
		Font f = new Font("AardvarkCafe",Font.PLAIN,24);
		win.setFont(f);
		win.setColor(java.awt.Color.getHSBColor(286,70,72));
		win.drawString(p.getPlayerName().toUpperCase()+" IS THE WINNER",1,1);
		client.SuspendThread();
	}

	public void losingClient (){
		Graphics lose = new Graphics() {
			@Override
			public Graphics create() {
				return null;
			}

			@Override
			public void translate(int x, int y) {

			}

			@Override
			public java.awt.Color getColor() {
				return null;
			}

			@Override
			public void setColor(java.awt.Color c) {

			}

			@Override
			public void setPaintMode() {

			}

			@Override
			public void setXORMode(java.awt.Color c1) {

			}

			@Override
			public Font getFont() {
				return null;
			}

			@Override
			public void setFont(Font font) {

			}

			@Override
			public FontMetrics getFontMetrics(Font f) {
				return null;
			}

			@Override
			public Rectangle getClipBounds() {
				return null;
			}

			@Override
			public void clipRect(int x, int y, int width, int height) {

			}

			@Override
			public void setClip(int x, int y, int width, int height) {

			}

			@Override
			public Shape getClip() {
				return null;
			}

			@Override
			public void setClip(Shape clip) {

			}

			@Override
			public void copyArea(int x, int y, int width, int height, int dx, int dy) {

			}

			@Override
			public void drawLine(int x1, int y1, int x2, int y2) {

			}

			@Override
			public void fillRect(int x, int y, int width, int height) {

			}

			@Override
			public void clearRect(int x, int y, int width, int height) {

			}

			@Override
			public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

			}

			@Override
			public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

			}

			@Override
			public void drawOval(int x, int y, int width, int height) {

			}

			@Override
			public void fillOval(int x, int y, int width, int height) {

			}

			@Override
			public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

			}

			@Override
			public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

			}

			@Override
			public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {

			}

			@Override
			public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {

			}

			@Override
			public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {

			}

			@Override
			public void drawString(String str, int x, int y) {

			}

			@Override
			public void drawString(AttributedCharacterIterator iterator, int x, int y) {

			}

			@Override
			public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
				return false;
			}

			@Override
			public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
				return false;
			}

			@Override
			public boolean drawImage(Image img, int x, int y, java.awt.Color bgcolor, ImageObserver observer) {
				return false;
			}

			@Override
			public boolean drawImage(Image img, int x, int y, int width, int height, java.awt.Color bgcolor, ImageObserver observer) {
				return false;
			}

			@Override
			public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
				return false;
			}

			@Override
			public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, java.awt.Color bgcolor, ImageObserver observer) {
				return false;
			}

			@Override
			public void dispose() {

			}
		};
			Font f = new Font("AardvarkCafe",Font.PLAIN,24);
			lose.setFont(f);
			lose.setColor(java.awt.Color.getHSBColor(169,68,100));
			lose.drawString("YOU LOSE",1,1);
			client.SuspendThread();
	}

	/**
	 * Called whenever the observed object is changed.
	 *
	 * @param message an argument passed to the notify method.
	 */
	@Override
	public void update(GameMessage message) {

		if (message instanceof StringMessage){
			String stringMessage = ((StringMessage) message).getMessage();
			output.println(stringMessage);
			switch (stringMessage) {
				case StringMessage.newWorkermove:
				case StringMessage.setFirstWorkerMessage:
				case StringMessage.choseWorker:
					setMaleSelected(acquireWorkerSelection());
					sendWorkerSelection();
					break;
				case StringMessage.welcomeMessage:
				case StringMessage.namealreadyTaken:
					acquirePlayerCredentials();
					sendPlayerCredentials(getCredentials());
					break;
				case StringMessage.setNumberOfPlayersMessage:
					acquireNumberOfPlayers();
					sendNumberOfPlayers(getNumberOfPlayers());
					break;
				case StringMessage.setSecondWorkerMessage:
				case StringMessage.moveMessage:
				case StringMessage.invalidMoveMessage: { //insert coordinates
					int[] coordinates = acquireCoordinates();
					sendMove(coordinates);
					break;
				}
				case StringMessage.buildMessage:
				case StringMessage.invalidBuildingMessage: {
					int[] coordinates = acquireCoordinates();
					sendBuild(coordinates);
					break;
				}
				case StringMessage.moveAgain:
				case StringMessage.buildAgain:
				case StringMessage.buildOrDome: {
					boolean choice = acquireBooleanChoice();
					sendBooleanChoice(choice);
					break;
				}
			}
		}
		if(message instanceof LoseMessage){
			losingClient();
		}
		if(message instanceof WinMessage){
			EndOfmatch(((WinMessage) message).getPlayer());
		}
		if (message instanceof CardsMessage){
			acquireCardSelection(((CardsMessage) message).getCards());
		}
		if (message instanceof BoardMessage){
			printBoard(((BoardMessage)message).getBoard());
		}
		if(message instanceof DeleteMessage)
			printAfterDelete((DeleteMessage) message);
	}

	public void printAfterDelete(DeleteMessage message){
		int n;
	}

	//setters & getters

	public boolean isMaleSelected() {
		return maleSelected;
	}

	public void setMaleSelected(boolean maleSelected) {
		this.maleSelected = maleSelected;
	}

	public HashMap<String, Integer> getCredentials() {
		return Credentials;
	}

	public void setCredentials(HashMap<String, Integer> credentials) {
		this.Credentials = credentials;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		CliView.numberOfPlayers = numberOfPlayers;
	}

}
