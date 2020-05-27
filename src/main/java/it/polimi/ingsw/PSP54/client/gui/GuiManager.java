package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.client.Client;
import it.polimi.ingsw.PSP54.observer.Observer;
import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.utils.choices.CardChoice;
import it.polimi.ingsw.PSP54.utils.messages.BoardMessage;
import it.polimi.ingsw.PSP54.utils.messages.CardsMessage;
import it.polimi.ingsw.PSP54.utils.messages.GameMessage;
import it.polimi.ingsw.PSP54.utils.messages.StringMessage;
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
    private int myCard;
    private boolean cardExtractor = true;
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
        if (val == Game.APOLLO){
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
                    Platform.runLater(() -> {
                        logInSceneController.setNumberOfPlayersScene();
                    });
                    break;
                case StringMessage.setFirstWorkerMessage:
                case StringMessage.choseWorker:

                    break;
                case StringMessage.setSecondWorkerMessage:
                case StringMessage.moveMessage:
                case StringMessage.invalidMoveMessage: { //insert coordinates

                    break;
                }
                case StringMessage.buildMessage:
                case StringMessage.invalidBuildingMessage: {

                }
                case StringMessage.moveAgain:
                case StringMessage.buildAgain:
                case StringMessage.buildOrDome: {

                }

            }
        }
        if (message instanceof CardsMessage){
            this.cardsToDisplay = (CardsMessage) message;
            Vector<Integer> extractedCards = new Vector<>(cardsToDisplay.getCards().keySet());
            if (extractedCards.size() == 1) {
                myCard = extractedCards.get(0).intValue();
                cardExtractor = false;
                sendObject(new CardChoice(myCard));
                if(gameMaster){
                    Platform.runLater(() -> {
                        numberOfPlayersSceneController.setBoardScene();
                    });
                }
            }
            else {
                if (gameMaster) {
                    Platform.runLater(() -> {
                        numberOfPlayersSceneController.setCardsChoiceScene();
                    });
                } else {
                    Platform.runLater(() -> {
                        logInSceneController.setCardsChoiceScene();
                    });
                }
            }
        }
        if (message instanceof BoardMessage){
            System.out.println("Ho ricevuto la board");
            if (board == null){
                board = (BoardMessage)message;
                if (!cardExtractor){
                    Platform.runLater(() -> {
                        logInSceneController.setBoardScene();
                    });
                }
                else {
                    Platform.runLater(() -> {
                        cardsChoiceSceneController.setBoardScene();
                    });
                }
            }
            else {
                board = (BoardMessage)message;
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
    public CardsMessage getCardsToDisplay() {
        return cardsToDisplay;
    }

    public void setClient(Client client) {
        this.client = client;
    }


}