package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.client.Client;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.utils.choices.CardChoice;
import it.polimi.ingsw.PSP54.utils.messages.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Vector;

public class GuiManager implements Observer<GameMessage> {

    private static GuiManager instance = null;
    private LogInSceneController logInSceneController;
    private NumberOfPlayersSceneController numberOfPlayersSceneController;
    private BoardSceneController boardSceneController;
    private CardsChoiceSceneController cardsChoiceSceneController;
    private CardsMessage cardsToDisplay;
    private Vector<String> names;
    private Vector<Integer> cardValues;
    private String myName;
    private boolean cardExtractor = true, moveChoice = false, buildChoice = false,
            firstWorkerSet = false, secondWorkerSet = false, boxChoice = false, booleanChoice = false;
    private Client client;
    private BoardMessage board = null;
    private boolean gameMaster = false;

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
     * Load a fxml file in a scene
     * @param scene
     * @param path of fxml file
     * @param <T>
     * @return
     */
    static <T> T setLayout(Scene scene, String path) {

        FXMLLoader loader = new FXMLLoader();
        Pane pane;
        try {
            loader.setLocation(new URL(path));
            pane = loader.load();
            scene.setRoot(pane);
        } catch (Exception e) {
            System.out.println("ERRORE: NON RIESCO A CARICARE IL FILE FXML");
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
            imageView.setImage(new Image("file:./resources/icons/01.png"));
        }
        if (val == Game.ARTEMIS){
            imageView.setImage(new Image("file:./resources/icons/02.png"));
        }
        if (val == Game.ATHENA){
            imageView.setImage(new Image("file:./resources/icons/03.png"));
        }
        if (val == Game.ATLAS){
            imageView.setImage(new Image("file:./resources/icons/04.png"));
        }
        if (val == Game.DEMETER){
            imageView.setImage(new Image("file:./resources/icons/05.png"));
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
                    Thread guiThread = new Thread(new GuiMain());
                    guiThread.start();
                    break;
                case StringMessage.setNumberOfPlayersMessage:
                    gameMaster = true;
                    Platform.runLater(() -> logInSceneController.setNumberOfPlayersScene());
                    break;
                case StringMessage.setFirstWorkerMessage:
                    firstWorkerSet = true;
                    Platform.runLater(() -> {
                        boardSceneController.setMessageLabel("SET YOUR FIRST WORKER");
                        boardSceneController.showPanelMessage();
                    });
                    break;
                case StringMessage.choseWorker:
                    setBoxChoice(true);
                    Platform.runLater(() -> {
                        boardSceneController.setMessageLabel("CHOOSE YOUR WORKER");
                        boardSceneController.showPanelMessage();
                    });
                    break;
                case StringMessage.setSecondWorkerMessage:
                    firstWorkerSet = false;
                    secondWorkerSet = true;
                case StringMessage.moveMessage:
                    Platform.runLater(() -> {
                        if (firstWorkerSet){
                            setBoxChoice(true);
                        }
                        else if (secondWorkerSet){
                            setBoxChoice(true);
                            boardSceneController.setMessageLabel("SET YOUR SECOND WORKER");
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
            }
        }
        if (message instanceof CardsMessage){
            this.cardsToDisplay = (CardsMessage) message;
            Vector<Integer> extractedCards = new Vector<>(cardsToDisplay.getCards().keySet());
            if (extractedCards.size() == 1) {
                int myCard = extractedCards.get(0);
                cardExtractor = false;
                sendObject(new CardChoice(myCard));
                if(gameMaster){
                    Platform.runLater(() -> numberOfPlayersSceneController.setBoardScene());
                }
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
                if (!cardExtractor) {
                    Platform.runLater(() -> logInSceneController.setBoardScene());
                } else {
                    Platform.runLater(() -> cardsChoiceSceneController.setBoardScene());
                }
            } else {
                board = (BoardMessage) message;
                Platform.runLater(() -> boardSceneController.setBoardImage(board.getBoard()));
            }
        }
        if (message instanceof CardsPlayersMessage){
            names = new Vector<>(((CardsPlayersMessage) message).getCardsPlayersMap().keySet());
            cardValues = new Vector<> (((CardsPlayersMessage) message).getCardsPlayersMap().values());
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
    public CardsMessage getCardsToDisplay() {
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
}
