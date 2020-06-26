package it.polimi.ingsw.PSP54.client.gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class NumberOfPlayersSceneController {

    private GuiManager guiManager;
    private ActionEvent event;

    @FXML private Button twoButton;
    @FXML private Button threeButton;
    @FXML private Label waitingLabel;
    @FXML private Label chooseNumberOfPlayersLabel;

    /**
     * Called when number_of_players.fxml is load
     */
    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setNumberOfPlayersSceneController(this);
    }

    public void setFont(){
        twoButton.setFont(Font.font("papyrus",18));
        threeButton.setFont(Font.font("papyrus",18));
        waitingLabel.setFont(Font.font("papyrus",21));
        chooseNumberOfPlayersLabel.setFont(Font.font("papyrus",29));
    }

    /**
     * Send 2 as the number of players when button is pressed
     * @param event
     */
    public void twoButtonPressed (ActionEvent event) {
        this.event = event;
        twoButton.setVisible(false);
        threeButton.setVisible(false);
        waitingLabel.setVisible(true);
        guiManager.sendObject(2);
        System.out.println("Ho scelto due giocatori");
    }

    /**
     * Send 3 as the number of players when button is pressed
     * @param event
     */
    public void threeButtonPressed (ActionEvent event) {
        this.event = event;
        twoButton.setVisible(false);
        threeButton.setVisible(false);
        waitingLabel.setVisible(true);
        guiManager.sendObject(3);
        System.out.println("Ho scelto tre giocatori");
    }

    /**
     * Set hand cursor when mouse enter on a button
     * @param event
     */
    public void setHandCursor(MouseEvent event){
        ((Node)event.getSource()).getScene().setCursor(Cursor.HAND);
    }

    /**
     * Set default cursor when mouse exit from a button
     * @param event
     */
    public void setDefaultCursor(MouseEvent event){
        ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
    }

    /**
     * Load cards_choice.fxml on current stage
     */
    public void setCardsChoiceScene() {
        CardsChoiceSceneController cardsChoiceSceneController = GuiManager.setLayout(guiManager.getStage().getScene(),"FXML/cards_choice.fxml");
        if (cardsChoiceSceneController != null){
            cardsChoiceSceneController.setFont();
        }
    }

    public void setDeckChoiceScene(){
        DeckChoiceSceneController deckChoiceSceneController = GuiManager.setLayout(guiManager.getStage().getScene(),"FXML/deck_choice.fxml");
        if (deckChoiceSceneController != null){
            deckChoiceSceneController.setDeckChoiceScene();
        }
    }

}

