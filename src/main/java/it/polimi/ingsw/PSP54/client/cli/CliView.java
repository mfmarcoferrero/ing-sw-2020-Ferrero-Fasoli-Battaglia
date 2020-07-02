package it.polimi.ingsw.PSP54.client.cli;

import it.polimi.ingsw.PSP54.client.Client;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.server.model.Player;
import it.polimi.ingsw.PSP54.utils.choices.*;
import it.polimi.ingsw.PSP54.utils.messages.*;

import java.io.PrintStream;
import java.net.SocketException;
import java.util.*;

public class CliView implements Observer<GameMessage> {

    private final Scanner inputReader = new Scanner(System.in);
    private final PrintStream output = new PrintStream(System.out);
    private final Client client;
    private HashMap<String, Integer> credentials;
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
    private String getBoxCenter(Box box) {

        String background = null;
        String string = "";

        switch (box.getLevel()) {
            case 0:
                background = Color.ANSI_GROUND_BACKGROUND.toString();
                break;
            case 1:
                background = Color.ANSI_FIRST_BACKGROUND.toString();
                break;
            case 2:
                background = Color.ANSI_SECOND_BACKGROUND.toString();
                break;
            case 3:
                background = Color.ANSI_THIRD_BACKGROUND.toString();
                break;
        }

        if (box.isDome())
            string = string + Color.ANSI_DOME_BACKGROUND + Color.ANSI_DOME + Symbol.UNICODE_DOME;

        else if (box.getWorker() != null) {
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
        } else
            string = Symbol.UNICODE_SQUARE.toString();
        return string + background;
    }

    /**
     * Prints the ground line of every box in the same line of the board.
     * @param initGround String containing the left border and the ANSI code for the ground.
     * @param endGround  String containing the right border.
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
    public void printBoard(Box[][] board) {

        StringBuilder line = null;
        StringBuilder toPrint;

        //String containing middle border
        String border = Color.ANSI_BORDER_BACKGROUND.toString() + Color.ANSI_BORDER
                + "⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼"
                + Color.ANSI_RESET;

        //Strings containing beginning/end of lines, it contains border and blocks with corresponding colors
        String initGround = Color.ANSI_BORDER_BACKGROUND.toString() + Color.ANSI_BORDER + "|" + Color.ANSI_GROUND_BACKGROUND + Color.ANSI_GROUND;
        String endGround = Color.ANSI_BORDER_BACKGROUND.toString() + Color.ANSI_BORDER + "|"
                + Color.ANSI_RESET;

        String initFirst = initGround + Symbol.UNICODE_SQUARE + Color.ANSI_FIRST_BACKGROUND + Color.ANSI_FIRST;
        String endFirst = Color.ANSI_GROUND_BACKGROUND.toString() + Color.ANSI_GROUND.toString() + Symbol.UNICODE_SQUARE + endGround;

        String initSecond = initFirst + Symbol.UNICODE_SQUARE + Color.ANSI_SECOND_BACKGROUND + Color.ANSI_SECOND;
        String endSecond = Color.ANSI_FIRST_BACKGROUND.toString() + Color.ANSI_FIRST.toString() + Symbol.UNICODE_SQUARE + endFirst;

        String initThird = initSecond + Symbol.UNICODE_SQUARE + Color.ANSI_THIRD_BACKGROUND + Color.ANSI_THIRD;
        String endThird = Color.ANSI_SECOND_BACKGROUND.toString() + Color.ANSI_SECOND.toString() + Symbol.UNICODE_SQUARE + endSecond;

        //print border and first ground level
        output.println(border);

        //print Board
        for (int i = 0; i < 5; i++) {

            //print the first line
            printGround(initGround, endGround);

            int l = 0;

            //print seven lines depending on boxes level or on the presence of a dome/worker in it
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
                } else if (l == 1 || l == 5) {
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
                        } else if (board[i][j].getLevel() > 1) {
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
                } else if (l == 2 || l == 4) {
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
                        } else if (board[i][j].getLevel() == 2) {
                            line = new StringBuilder(initSecond);
                            for (int k = 0; k < 5; k++) {
                                line.append(Symbol.UNICODE_SQUARE);
                            }
                            line.append(endSecond);
                        } else if (board[i][j].getLevel() == 3) {
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
                } else if (l == 3) {
                    toPrint = new StringBuilder();
                    for (int j = 0; j < 5; j++) {
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
                        } else if (board[i][j].getLevel() == 2) {
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
                        } else if (board[i][j].getLevel() == 3) {
                            line = new StringBuilder(initThird);
                            line.append(Symbol.UNICODE_SQUARE);
                            String center = getBoxCenter(board[i][j]);
                            line.append(center).append(Color.ANSI_THIRD);
                            line.append(Symbol.UNICODE_SQUARE);
                            line.append(endThird);
                        }
                        toPrint.append(line);
                        if (j == 4) {
                            toPrint.append(Color.ANSI_BORDER);
                            toPrint.append(i + 1);

                        }
                    }
                    output.println(toPrint.toString());
                    l++;
                }
            }

            //print the last line
            printGround(initGround, endGround);
            if (i < 4)
                output.println(border);
        }

        //print lower border
        output.println(border);
        output.println(Color.ANSI_BORDER +
                "          1                   2                   3                   4                   5         "
                + Color.ANSI_RESET);
    }

    /**
     * Acquires username and age of the player from command line and stores them in the credentials HashMap.
     */
    public void acquirePlayerCredentials() {
        output.println("What's your name?");
        String name = inputReader.next();

        output.println("What's your age?");
        int age = acquireInteger("Incorrect input! [Enter an integer]");

        HashMap<String, Integer> credentials = new HashMap<>();
        credentials.put(name, age);
        setCredentials(credentials);
    }

    /**
     * Acquires game's number of players and stores it into a local variable.
     */
    public void acquireNumberOfPlayers() {
        int numberOfPlayers = acquireInteger("Incorrect Input! [Enter an integer]");
        while (numberOfPlayers < 2 || numberOfPlayers > 3) {
            output.println("Illegal number of player! It must be '2' or '3', try again");
            numberOfPlayers = inputReader.nextInt();
        }
        setNumberOfPlayers(numberOfPlayers);
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
            if (workerSex.equals("m")) {
                male = true;
                loop = false;
            } else if (workerSex.equals("f")) {
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
            if (dome.equals("y")) {
                choice = true;
                loop = false;
            } else if (dome.equals("n")) {
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
            String component = inputReader.next();
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
    public int acquireInteger(String errorMessage) {
        boolean loop = true;
        int i = 0;
        while (loop) {
            String toParse = inputReader.next();
            try {
                i = Integer.parseInt(toParse);
                loop = false;
            } catch (IllegalArgumentException e) {
                output.println(errorMessage);
            }
        }
        return i;
    }

    /**
     * Asks the challenger to select a God Power Card for each player.
     * @param deck the HashMap containing the id and the name of every card in the game.
     * @return an HashMap containing the selected cards.
     */
    public HashMap<Integer, String> challengerCardsSetup(HashMap<Integer, String> deck) {
        ArrayList<String> cardsName = new ArrayList<>(deck.values());
        ArrayList<Integer> cardsValues = new ArrayList<>(deck.keySet());
        HashMap<Integer, String> extractedCards = new HashMap<>();
        // prints available cards
        output.println("Chose the God Powers for this game: [Enter the number of the cards]");
        for (int i = 0; i < deck.size(); i++) {
            output.println((i + 1) + ") " + cardsName.get(i));
        }
        // acquire extracted cards
        for (int i = 0; i < numberOfPlayers; i++) {
            boolean loop = true;
            int chosenCard = acquireInteger("Invalid Input! [Enter the number of a card]") - 1;
            while (loop) {
                if (cardsValues.contains(chosenCard)) {
                    if (!extractedCards.containsKey(chosenCard)) {
                        extractedCards.put(chosenCard, cardsName.get(chosenCard));
                        output.println("You selected " + cardsName.get(chosenCard));
                        loop = false;
                    } else
                        output.println("You've already chosen this card, please try another");
                } else
                    output.println("Invalid Input! [Enter the number of a card]");
                if (loop)
                    chosenCard = acquireInteger("Invalid Input! [Enter the number of a card]") - 1;
            }
        }
        return extractedCards;
    }

    /**
     * Asks the player which power he wants and sends the choice via socket.
     */
    public void acquireAndSendCardSelection(HashMap<Integer, String> extractedCards) {
        ArrayList<String> cardsNames = new ArrayList<>(extractedCards.values());
        ArrayList<Integer> cardsIDs = new ArrayList<>(extractedCards.keySet());
        HashMap<Integer, Integer> extractedCardsMap = new HashMap<>();
        boolean loop = true;

        if (extractedCards.size() == 1) {
            PlayerChoice cardChoice = new PowerChoice(cardsIDs.get(0));
            client.asyncSend(cardChoice);
        } else {
            output.println("Choose your card: [Enter the number of the God]");
            for (int i = 0; i < extractedCards.size(); i++) {
                output.println((i + 1) + ") " + cardsNames.get(i));
                extractedCardsMap.put(i + 1, cardsIDs.get(i));
            }
            int chosenCard = acquireInteger("Invalid Input! [Enter the number of a God]");
            while (loop) {
                if (extractedCardsMap.containsKey(chosenCard)) {
                    PlayerChoice cardChoice = new PowerChoice(extractedCardsMap.get(chosenCard));
                    client.asyncSend(cardChoice);
                    loop = false;
                }
                if (loop) {
                    output.println("Invalid Input! [Enter the number of a God]");
                    chosenCard = acquireInteger("Invalid Input! [Enter the number of a God]");
                }
            }
        }
    }


    /**
     * Asks the Challenger to chose the starting player.
     * @param players the vector containing all players in the game.
     * @return the index of the designed player.
     */
    public int acquireStartPlayerSelection(Vector<Player> players) {
        output.println("Select the Start Player: [Enter the number of the player]");
        for (Player player : players)
            output.println((players.indexOf(player) + 1) + ") " + player.getPlayerName());
        int startIndex = acquireInteger("Invalid Input! [Enter the number of a Player]") - 1;
        while (startIndex < 0 || startIndex > numberOfPlayers - 1) {
            output.println("Invalid Input! [Enter the number of a Player]");
            startIndex = acquireInteger("Invalid Input! [Enter the number of a Player]") - 1;
        }
        return startIndex;
    }

    /**
     * Sends a StartPlayerChoice object via socket.
     * @param startPlayerIndex the index of the starting player.
     */
    public void sendStartPlayerSelection(int startPlayerIndex) {
        PlayerChoice startPlayer = new StartPlayerChoice(startPlayerIndex);
        client.asyncSend(startPlayer);
    }

    /**
     * Creates a PlayerChoice object containing player's credentials and sends it via socket.
     * @param credentials the HashMap containing player's name and age.
     */
    public void sendPlayerCredentials(HashMap<String, Integer> credentials) {
        for (Map.Entry<String, Integer> map : credentials.entrySet()) {
            String name = map.getKey();
            int age = map.getValue();
            PlayerChoice playerCredentials = new PlayerCredentials(name, age);
            client.asyncSend(playerCredentials);
        }
    }

    /**
     * Sends the number of players via socket.
     * @param numberOfPlayers the number of players.
     */
    public void sendNumberOfPlayers(int numberOfPlayers) {
        client.asyncSend(new NumberOfPlayers(numberOfPlayers));
    }

    /**
     * Sends an ExtractedCardsChoice object via socket.
     * @param extractedCards the HashMap to be encapsulated in the message to be sent.
     */
    public void sendExtractedCards(HashMap<Integer, String> extractedCards) {
        PlayerChoice selectedCards = new ExtractedCardsChoice(extractedCards);
        client.asyncSend(selectedCards);
    }

    /**
     * Sends a WorkerChoice object via socket.
     */
    public void sendWorkerSelection() {
        PlayerChoice workerSelection = new WorkerChoice(isMaleSelected());
        client.asyncSend(workerSelection);
    }

    /**
     * Sends a MoveChoice object via socket.
     *
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

    /**
     * Handles the ending of a match by printing the name of the winner and closing the game.
     * @param winner the player that has won.
     */
    public void endOfMatch(Player winner) {
        if (getCredentials().containsKey(winner.getPlayerName()))
            output.println("YOU WON!");
        else
            output.println(winner.getPlayerName() + " IS THE WINNER");
        playAgain();
    }

    /**
     * Handles the loosing of a player.
     */
    public void losingPlayer(Player loser) {
        if (getCredentials().containsKey(loser.getPlayerName())) {
            output.println("YOU LOST!");
            playAgain();
        }
        else
            output.println(loser.getPlayerName() + " HAS LOST!");
    }

    /**
     * Asks the player if he wants to play again, if so adds him to a new lobby, closes the connection otherwise.
     */
    public void playAgain() {
        boolean loop=true;
        output.println("Do you want to play again? [Enter y/n]");
        while (loop) {
            String Choice = inputReader.next();
            if(Choice.equals("y")) {
                loop = false;
                PlayerChoice playAgain = new NewGameChoice();
                client.asyncSend(playAgain);
            }
            else if(Choice.equals("n")){
                loop = false;
                PlayerChoice stopPlaying = new StopPlayingChoice();
                client.asyncSend(stopPlaying);
                client.setActive(false);
                System.exit(0);
            } else
                output.println("Incorrect Input!");
        }
    }

    /**
     * Called whenever the observed object is changed.
     * Calls different methods depending on the type of the incoming GameMessage.
     * @param message an argument passed to the notify method.
     */
    @Override
    public void update(GameMessage message) {

        if (message instanceof StringMessage) {
            String stringMessage = ((StringMessage) message).getMessage();
            output.println(stringMessage);
            switch (stringMessage) {
                case StringMessage.welcomeMessage:
                case StringMessage.nameAlreadyTaken:
                    acquirePlayerCredentials();
                    sendPlayerCredentials(getCredentials());
                    break;
                case StringMessage.setNumberOfPlayersMessage:
                    acquireNumberOfPlayers();
                    sendNumberOfPlayers(getNumberOfPlayers());
                    break;
                case StringMessage.setFirstWorkerMessage:
                case StringMessage.choseWorker:
                case StringMessage.workerCantMove:
                    setMaleSelected(acquireWorkerSelection());
                    sendWorkerSelection();
                    break;
                case StringMessage.setSecondWorkerMessage:
                case StringMessage.moveMessage:
                case StringMessage.invalidMoveMessage: {
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
                case StringMessage.buildOrDome:
                case StringMessage.buildFirst: {
                    boolean choice = acquireBooleanChoice();
                    sendBooleanChoice(choice);
                    break;
                }
                case StringMessage.endForDisconnection:
                    playAgain();
                    break;
                case StringMessage.closedConnection:
                    System.exit(0);
                    break;
            }
        }
        if (message instanceof OpponentMessage) {
            setNumberOfPlayers(((OpponentMessage) message).getNumberOfPlayers());
        }
        if (message instanceof DeckMessage) {
            HashMap<Integer, String> extractedCards = challengerCardsSetup(((DeckMessage) message).getDeck());
            sendExtractedCards(extractedCards);
        }
        if (message instanceof PlayersMessage) {
            int startIndex = acquireStartPlayerSelection(((PlayersMessage) message).getPlayers());
            sendStartPlayerSelection(startIndex);
        }
        if (message instanceof LoseMessage) {
            losingPlayer(((LoseMessage)message).getPlayer());
        }
        if (message instanceof WinMessage) {
            endOfMatch(((WinMessage) message).getPlayer());
        }
        if (message instanceof AvailableCardsMessage) {
            acquireAndSendCardSelection(((AvailableCardsMessage) message).getCards());
        }
        if (message instanceof BoardMessage) {
            printBoard(((BoardMessage) message).getBoard());
        }
        if (message instanceof LobbyAccessMessage){
            client.ping(client.getSocketOut());
            try {
                client.getSocket().setSoTimeout(5000);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    //setters & getters

    public boolean isMaleSelected() {
        return maleSelected;
    }

    public void setMaleSelected(boolean maleSelected) {
        this.maleSelected = maleSelected;
    }

    public HashMap<String, Integer> getCredentials() {
        return credentials;
    }

    public void setCredentials(HashMap<String, Integer> credentials) {
        this.credentials = credentials;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        CliView.numberOfPlayers = numberOfPlayers;
    }

}