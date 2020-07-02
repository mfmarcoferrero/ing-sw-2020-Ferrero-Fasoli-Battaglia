package it.polimi.ingsw.PSP54.client.gui;


import it.polimi.ingsw.PSP54.client.Client;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.server.model.Player;
import it.polimi.ingsw.PSP54.utils.choices.PowerChoice;
import it.polimi.ingsw.PSP54.utils.messages.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.SocketException;
import java.util.Vector;

public class GuiManager implements Observer<GameMessage> {

    private static GuiManager instance = null;
    Thread guiThread;
    private LogInSceneController logInSceneController;
    private NumberOfPlayersSceneController numberOfPlayersSceneController;
    private BoardSceneController boardSceneController;
    private CardsChoiceSceneController cardsChoiceSceneController;
    private DeckChoiceSceneController deckChoiceSceneController;
    private FirstPlayerChoiceSceneController firstPlayerChoiceSceneController;
    private EndSceneController endSceneController;
    private AvailableCardsMessage cardsToDisplay;
    private Vector<String> names;
    private Vector<Integer> cardValues;
    private Vector<Player> players;
    private String myName;
    private int numberOfPlayers = 0;
    private boolean cardExtractor = false, moveChoice = false, buildChoice = false,
            firstWorkerSet = false, secondWorkerSet = false, boxChoice = false, booleanChoice = false,
            winner = false, loser = false;
    private Client client;
    private BoardMessage board = null;
    private boolean gameMaster = false;
    private Stage stage;

    /**
     * Create a static instance of a GuiManager
     * Using a singleton pattern every controller of a scene can get the only GuiManager
     * instance related to a single client
     * @param client
     * @return
     */
    public static GuiManager getInstance(Client client) {
        if (instance == null){
            instance = new GuiManager();
            instance.setClient(client);
        }
        return instance;
    }

    /**
     * Get the static instance of this class
     * @return
     */
    public static GuiManager getInstance() {
        return instance;
    }

    /**
     * Called from a LogInSceneController
     * Save the reference to this controller in GuiManager static instance
     * @param logInSceneController
     */
    void setLogInSceneController(LogInSceneController logInSceneController) {
        this.logInSceneController = logInSceneController;
    }

    /**
     * Called from a NumberOfPlayersSceneController
     * Save the reference to this controller in GuiManager static instance
     * @param numberOfPlayersSceneController
     */
    void setNumberOfPlayersSceneController(NumberOfPlayersSceneController numberOfPlayersSceneController) {
        this.numberOfPlayersSceneController = numberOfPlayersSceneController;
    }

    /**
     * Called from a BoardSceneController
     * Save the reference to this controller in GuiManager static instance
     * @param boardSceneController
     */
    void setBoardSceneController(BoardSceneController boardSceneController){
        this.boardSceneController = boardSceneController;
    }

    /**
     * Called from a CardsChoiceSceneController
     * Save the reference to this controller in GuiManager static instance
     * @param cardsChoiceSceneController
     */
    void setCardsChoiceSceneController(CardsChoiceSceneController cardsChoiceSceneController){
        this.cardsChoiceSceneController = cardsChoiceSceneController;
    }

    /**
     * Called from a DeckChoiceSceneController
     * Save the reference to this controller in GuiManager static instance
     * @param deckChoiceSceneController
     */
    void setDeckChoiceSceneController(DeckChoiceSceneController deckChoiceSceneController){
        this.deckChoiceSceneController = deckChoiceSceneController;
    }

    /**
     * Called from a FirstPlayerChoiceSceneController
     * Save the reference to this controller in GuiManager static instance
     * @param firstPlayerChoiceSceneController
     */
    void setFirstPlayerChoiceSceneController(FirstPlayerChoiceSceneController firstPlayerChoiceSceneController){
        this.firstPlayerChoiceSceneController = firstPlayerChoiceSceneController;
    }

    /**
     * Called from a EndSceneController
     * Save the reference to this controller in GuiManager static instance
     * @param endSceneController
     */
    void setEndSceneController(EndSceneController endSceneController){
        this.endSceneController = endSceneController;
    }

    /**
     * Load a fxml file in a scene
     * @param scene
     * @param path of fxml file
     * @param <T>
     * @return
     */
    static <T> T setLayout(Scene scene, String path) {
        scene.getStylesheets().add(GuiManager.class.getResource("/fontstyle.css").toExternalForm());
        FXMLLoader loader = new FXMLLoader(GuiManager.class.getClassLoader().getResource(path));
        Pane pane;
        try {
            pane = loader.load();
            scene.setRoot(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return loader.getController();
    }

    /**
     * Load an image from resources/icons in an ImageView
     * @param val
     * @param imageView
     */
    public void setCardImage(int val, ImageView imageView) {
        if (val == Game.APOLLO){
            imageView.setImage(new Image("icons/01.png"));
        }
        if (val == Game.ARTEMIS){
            imageView.setImage(new Image("icons/02.png"));
        }
        if (val == Game.ATHENA){
            imageView.setImage(new Image("icons/03.png"));
        }
        if (val == Game.ATLAS){
            imageView.setImage(new Image("icons/04.png"));
        }
        if (val == Game.DEMETER){
            imageView.setImage(new Image("icons/05.png"));
        }
        if (val == Game.HEPHAESTUS){
            imageView.setImage(new Image("icons/06.png"));
        }
        if (val == Game.MINOTAUR){
            imageView.setImage(new Image("icons/08.png"));
        }
        if (val == Game.PAN){
            imageView.setImage(new Image("icons/09.png"));
        }
        if (val == Game.PROMETHEUS){
            imageView.setImage(new Image("icons/10.png"));
        }
    }

    /**
     * Called whenever the observed object is changed.
     * When a welcome message is notified, a new thread running GuiMain start.
     * When other message are notified, current scene is changed or modified
     * @param message an argument passed to the notify method.
     */
    @Override
    public void update(GameMessage message) {

        if (message instanceof StringMessage){
            String stringMessage = ((StringMessage) message).getMessage();
            System.out.println(stringMessage);
            switch (stringMessage) {
                case StringMessage.welcomeMessage:
                    guiThread = new Thread(new GuiMain());
                    guiThread.start();
                    break;
                case StringMessage.setNumberOfPlayersMessage:
                    gameMaster = true;
                    Platform.runLater(() -> logInSceneController.setNumberOfPlayersScene());
                    break;
                case StringMessage.nameAlreadyTaken:
                    Platform.runLater(() -> logInSceneController.setInvalidNameLabel());
                    break;
                case StringMessage.setFirstWorkerMessage:
                    firstWorkerSet = true;
                    Platform.runLater(() -> {
                        boardSceneController.setMessageLabel("SET YOUR FIRST WORKER");
                        boardSceneController.showMaleOrFemaleMessage();
                    });
                    break;
                case StringMessage.choseWorker:
                    Platform.runLater(() -> {
                        boardSceneController.setMessageLabel("CHOOSE YOUR WORKER");
                        boardSceneController.showMaleOrFemaleMessage();
                    });
                    break;
                case StringMessage.setSecondWorkerMessage:
                    firstWorkerSet = false;
                    secondWorkerSet = true;
                    Platform.runLater(() -> {
                        setBoxChoice(true);
                        boardSceneController.setMessageLabel("SET YOUR SECOND WORKER");
                    });
                case StringMessage.moveMessage:
                    Platform.runLater(() -> {
                        if (firstWorkerSet || secondWorkerSet){
                            setBoxChoice(true);
                        }
                        else {
                            boardSceneController.setMessageLabel("MAKE YOUR MOVE");
                            setBoxChoice(true);
                            setMoveChoice(true);
                        }
                    });
                    break;
                case StringMessage.invalidMoveMessage: {
                    Platform.runLater(() -> {
                        setMoveChoice(true);
                        setBoxChoice(true);
                        boardSceneController.setMessageLabel("INVALID MOVE, RETRY!!!");
                    });
                    break;
                }
                case StringMessage.buildMessage:
                    Platform.runLater(() -> {
                        setBuildChoice(true);
                        setBoxChoice(true);
                        boardSceneController.setMessageLabel("BUILD !!!");
                    });
                    break;
                case StringMessage.invalidBuildingMessage: {
                    Platform.runLater(() -> {
                        setBuildChoice(true);
                        setBoxChoice(true);
                        boardSceneController.setMessageLabel("INVALID BUILDING, RETRY!!!");
                    });
                    break;
                }
                case StringMessage.moveAgain:
                    Platform.runLater(() -> {
                        setBooleanChoice(true);
                        boardSceneController.showMoveAgainMessage();
                    });
                    break;
                case StringMessage.buildAgain:
                    Platform.runLater(() -> {
                        setBooleanChoice(true);
                        boardSceneController.showBuildAgainMessage();
                    });
                    break;
                case StringMessage.buildOrDome:
                    Platform.runLater(() -> {
                        setBooleanChoice(true);
                        boardSceneController.showBuildOrDomeMessage();
                    });
                    break;
                case StringMessage.buildFirst:
                    Platform.runLater(() -> {
                        setBooleanChoice(true);
                        boardSceneController.showBuildFirstMessage();
                    });
                    break;
                case StringMessage.workerCantMove:
                    Platform.runLater(() -> {
                        Platform.runLater(() -> {
                            boardSceneController.setMessageLabel("THIS WORKER CAN'T MOVE. CHOOSE AGAIN ! ");
                            boardSceneController.showMaleOrFemaleMessage();
                        });
                    });
                    break;
                case StringMessage.endForDisconnection:
                    Platform.runLater(() -> {
                        endSceneController = setLayout(stage.getScene(),"FXML/end_scene.fxml");
                        if (endSceneController != null){
                            endSceneController.setFont(null,true);
                        }
                    });
                    break;
                case StringMessage.closedConnection:
                    System.exit(0);
                    break;
            }
        }
        if (message instanceof AvailableCardsMessage){
            this.cardsToDisplay = (AvailableCardsMessage) message;
            Vector<Integer> extractedCards = new Vector<>(cardsToDisplay.getCards().keySet());
            if (cardExtractor) {
                int myCard = extractedCards.get(0);
                sendObject(new PowerChoice(myCard));
            }
            else {
                if (gameMaster) {
                    Platform.runLater(() -> numberOfPlayersSceneController.setCardsChoiceScene());
                } else {
                    Platform.runLater(() -> logInSceneController.setCardsChoiceScene());
                }
            }
        }
        if (message instanceof BoardMessage) {
            if (board == null) {
                board = (BoardMessage) message;
                if (cardExtractor) {
                    Platform.runLater(() -> firstPlayerChoiceSceneController.setBoardScene());
                } else {
                    Platform.runLater(() -> cardsChoiceSceneController.setBoardScene());
                }
            } else {
                board = (BoardMessage) message;
                Platform.runLater(() -> boardSceneController.setBoardImage(board.getBoard()));
            }
        }
        if (message instanceof CardsPlayersMessage) {
            names = new Vector<>(((CardsPlayersMessage) message).getCardsPlayersMap().keySet());
            cardValues = new Vector<> (((CardsPlayersMessage) message).getCardsPlayersMap().values());
        }
        if(message instanceof DeckMessage){
            cardExtractor = true;
            if (gameMaster){
                Platform.runLater(() -> numberOfPlayersSceneController.setDeckChoiceScene());
            }
            else
                Platform.runLater(() -> logInSceneController.setDeckChoiceScene());
        }
        if (message instanceof OpponentMessage){
            numberOfPlayers = ((OpponentMessage) message).getNumberOfPlayers();
            System.out.println("Number of Players: " + numberOfPlayers);
            System.out.println("My name is: " + myName);
        }
        if (message instanceof PlayersMessage){
            players = ((PlayersMessage) message).getPlayers();
            Platform.runLater(() -> deckChoiceSceneController.setFirstPlayerChoiceScene());
        }
        if (message instanceof WinMessage){
            System.out.println("WE HAVE A WINNER !");
            System.out.println("MY NAME: " + myName);
            System.out.println("WINNER NAME: " + ((WinMessage) message).getPlayer().getPlayerName());
            if (((WinMessage) message).getPlayer().getPlayerName().equals(myName)) {
                winner = true;
            }
            else
                loser = true;
            Platform.runLater(() -> boardSceneController.setEndScene(((WinMessage) message).getPlayer().getPlayerName()));
        }
        if (message instanceof LoseMessage){
            System.out.println("WE HAVE A LOSER !");
            System.out.println("LOSER NAME: " + ((LoseMessage) message).getPlayer().getPlayerName());
            if (((LoseMessage) message).getPlayer().getPlayerName().equals(myName)) {
                loser = true;
                Platform.runLater(() -> boardSceneController.setEndScene(null));
            }
        }
        if (message instanceof LobbyAccessMessage) {
            client.ping(client.getSocketOut());
            try {
                client.getSocket().setSoTimeout(5000);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * Send an object to server
     * @param message
     */
    public void sendObject(Object message){
        instance.client.asyncSend(message);
    }

    //Setter and getter
    public AvailableCardsMessage getCardsToDisplay() {
        return cardsToDisplay;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isMoveChoice() {
        return moveChoice;
    }

    public void setMoveChoice(boolean moveChoice) {
        this.moveChoice = moveChoice;
    }

    public boolean isBuildChoice() {
        return buildChoice;
    }

    public void setBuildChoice(boolean buildChoice) {
        this.buildChoice = buildChoice;
    }

    public boolean isFirstWorkerSet() {
        return firstWorkerSet;
    }

    public void setFirstWorkerSet(boolean firstWorkerSet) {
        this.firstWorkerSet = firstWorkerSet;
    }

    public boolean isSecondWorkerSet() {
        return secondWorkerSet;
    }

    public void setSecondWorkerSet(boolean secondWorkerSet) {
        this.secondWorkerSet = secondWorkerSet;
    }

    public Vector<String> getNames() {
        return names;
    }

    public Vector<Integer> getCardValues() {
        return cardValues;
    }

    public boolean isBoxChoice() {
        return boxChoice;
    }

    public void setBoxChoice(boolean boxChoice) {
        this.boxChoice = boxChoice;
    }

    public boolean isBooleanChoice() {
        return booleanChoice;
    }

    public void setBooleanChoice(boolean booleanChoice) {
        this.booleanChoice = booleanChoice;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Vector<Player> getPlayers() {
        return players;
    }

    public boolean isWinner() {
        return winner;
    }

    public boolean isLoser() {
        return loser;
    }

    public Client getClient() {
        return client;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}