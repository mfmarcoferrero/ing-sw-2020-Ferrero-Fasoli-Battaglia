	package it.polimi.ingsw.PSP54.view;
	import it.polimi.ingsw.PSP54.model.Box;
	import java.io.PrintStream;
	import java.util.InputMismatchException;
	import java.util.Scanner;

	public class CliView {

		private final Scanner scanner = new Scanner(System.in);
		private final PrintStream outputStream = new PrintStream(System.out);

		/**
		 *Returns the correct character to print in the center of every box, it's called by printBoard(Box[][] board)
		 * @param box current box which is being printed
		 * @return the String containing ANSI/UNICODE codes representing the Dome, the Workers or the level of the current box
		 */
		private String getBoxCenter(Box box){
			String string = "";

			if(box.isDome())
				string = string + Color.ANSI_DOME + Symbol.UNICODE_DOME;

			else if(box.getWorker() != null) {
				switch (box.getWorker().getOwner().getColor()) {
					case "blue":
						if (box.getWorker().getMale())
							string = string + Color.ANSI_P1 + Symbol.UNICODE_MALE_WORKER;
						else
							string = string + Color.ANSI_P1 + Symbol.UNICODE_FEMALE_WORKER;
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
			return string;
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
				for (int k = 0; k < 9; k++)
					line.append(Symbol.UNICODE_SQUARE);

				line.append(endGround);
				toPrint.append(line);
			}
			outputStream.println(toPrint.toString());
		}

		//TODO: Implement StringBuilder rather than concatenation
		/**
		 *prints the board
		 * @param board bi-dimensional array of boxes representing the game board
		 */
		public void printBoard (Box[][] board) {

			StringBuilder line = null;
			StringBuilder toPrint;

			//String containing upper border
			String upperBorder = Color.ANSI_BORDER + "____________________________________________________________________________________________________";

			//String containing lower border
			String lowerBorder = Color.ANSI_BORDER + "⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺";

			//String containing middle border
			String middleBorder = Color.ANSI_BORDER + "----------------------------------------------------------------------------------------------------";

			//Strings containing beginning/end of lines, it contains border and blocks with
			String initGround = Color.ANSI_BORDER + "|" + Color.ANSI_GROUND;
			String endGround = Color.ANSI_BORDER + "|";

			String initFirst = initGround + Symbol.UNICODE_SQUARE + Color.ANSI_FIRST;
			String endFirst = Color.ANSI_GROUND.toString() + Symbol.UNICODE_SQUARE + endGround;

			String initSecond = initFirst + Symbol.UNICODE_SQUARE + Color.ANSI_SECOND;
			String endSecond = Color.ANSI_FIRST.toString() + Symbol.UNICODE_SQUARE + endFirst;

			String initThird = initSecond + Symbol.UNICODE_SQUARE + Color.ANSI_THIRD;
			String endThird = Color.ANSI_SECOND.toString() + Symbol.UNICODE_SQUARE + endSecond;

			//print border and first ground level
			outputStream.println(upperBorder);

			//print Board
			for (int i = 0; i < 5; i++) {

				//print the first line
				printGround(initGround, endGround);

				int l = 0;

				//print the seven conditioned line depending on level of the box or on the presence of a dome/worker in it
				while (l < 7) {

					//generate and print the five strings containing the representation of the of the first line of boxes
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
						outputStream.println(toPrint.toString());
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
						outputStream.println(toPrint.toString());
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
					outputStream.println(toPrint.toString());
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
						outputStream.println(toPrint.toString());
						l++;
				}
				}

				//print the last line
				printGround(initGround, endGround);
				if (i < 4)
					outputStream. println(middleBorder);
			}

			//print lower border
			outputStream.println(lowerBorder);
		}

		/**
		 *asks the age of the player until input is an integer
		 * @return player age
		 */
		public int acquireAge(){

			boolean loop = true;
			Integer playerAge = null;

			while (loop) {
				outputStream.println("Enter your age:");
				try{
					playerAge = scanner.nextInt();
					if (playerAge > 0 && playerAge < 100)
						loop = false;
					else
						outputStream.println("Incorrect Input!");
				}catch (InputMismatchException e){
					outputStream.println("Incorrect input!");
				}
			}

			return playerAge;
		}

		/**
		 * asks the name of the player until input is a string
		 * @return player name
		 */

		public String acquireName(){

			boolean loop = true;
			String playerName = null;


			while (loop) {
				outputStream.println("Enter your name:");
				playerName = scanner.next();
				try{
					int incorrect = Integer.parseInt(playerName);
					outputStream.println("Incorrect Input!");
				}catch (IllegalArgumentException e){
					loop = false;
				}
			}

			return playerName;
		}

		/**
		 *asks player which worker he wants to use
		 * @return true if the choice is the male worker, false if it's the female Worker
		 */

		public boolean acquireWorkerSelection(){

			boolean loop = true;
			Boolean isMale = null;

			while (loop) {
				outputStream.println("Select your worker: [Enter 'male' or 'female']");
				String workerSex = scanner.next();
				if(workerSex.equals("male")) {
					isMale = true;
					loop = false;
				}
				else if(workerSex.equals("female")){
					isMale = false;
					loop = false;
				}else
					outputStream.println("Incorrect Input!");
				}
			return isMale;
		}

		/**
		 * print a message to the current player who is about to move
		 */

		public  void moveMessage(){
			outputStream.println("where do you want to move?");
		}

		/**
		 * prints a message to the current player who is about to build
		 */

		public void buildMessage(){
			outputStream.println("Where do you want to build?");
		}

		/**
		 * asks coordinates which player wants to move
		 */
		public void acquireCoordinates() {

			//TODO: return Box

			/*boolean loopX = true;
			boolean loopY = true;
			Integer x = null;
			Integer y = null;

			outputStream.println("Set cell coordinates");
			//set x
			while (loopX) {
				outputStream.println("Enter x:");
				try{
					x = scanner.nextInt();
					if (0<x && x<6)
						loopX = false;
					else
						outputStream.println("Incorrect Coordinates! [x must be 1<=x<=5]");
				}catch (InputMismatchException e){
					outputStream.println("Incorrect input!");
				}
			}
			//set y
			while (loopY) {
				outputStream.println("Enter your age:");
				try {
					y = scanner.nextInt();
					if (y>0 && y<6)
						loopY = false;
					else
						outputStream.println("Incorrect Coordinates! [y must be 1<=y<=5]");
				} catch (InputMismatchException e) {
					outputStream.println("Incorrect input!");
				}
			}

			return new Box(x,y);*/
		}

		/**
		 *asks player which level he wants to build
		 * @return the acquired level
		 */
		public int acquireLevel(){

			Integer level = null;
			boolean loop = true;

			outputStream.println("which level do you want to build?");
			//set level
			while(loop){
				outputStream.println("Enter Level:");
				String tempLev = scanner.next();
				try{
					level = Integer.parseInt(tempLev);
					if (level>0 && level<4)
						loop = false;
					else
						outputStream.println("Incorrect Level! [level bust be 1<=level<=3");
				}catch (IllegalArgumentException e){
					outputStream.println("Incorrect input!");
				}
			}

			return level;
		}
	}
