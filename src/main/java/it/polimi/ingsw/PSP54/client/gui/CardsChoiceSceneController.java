package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.utils.choices.PowerChoice;
import it.polimi.ingsw.PSP54.utils.messages.AvailableCardsMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.util.Vector;

public class CardsChoiceSceneController {

    private GuiManager guiManager;
    private Vector<Integer> extractedCards;
    @FXML private ImageView firstCardImage;
    @FXML private ImageView secondCardImage;
    @FXML private ImageView thirdCardImage;
    @FXML private Button firstCardButton;
    @FXML private Button secondCardButton;
    @FXML private Button thirdCardButton;
    @FXML private Label chooseYourPowerLabel;

    /**
     * Called when cards_choice.fxml is load
     */
    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setCardsChoiceSceneController(this);
        setCardsToDisplay(guiManager.getCardsToDisplay());
    }

    public void setFont(){
        chooseYourPowerLabel.setFont(Font.font("papyrus",40));
        chooseYourPowerLabel.setAlignment(Pos.CENTER);
    }

    /**
     * Set card images for the scene, changing layout when cards to display are 2 or 3
     * @param cardsToDisplay message for cards to display
     */
    public void setCardsToDisplay (AvailableCardsMessage cardsToDisplay){
        extractedCards = new Vector<>(cardsToDisplay.getCards().keySet());
        if (extractedCards.size() == 2){
            guiManager.setCardImage(extractedCards.get(0),firstCardImage);
            guiManager.setCardImage(extractedCards.get(1),thirdCardImage);
            secondCardImage.setVisible(false);
            secondCardButton.setVisible(false);
        }
        if (extractedCards.size() == 3){
            guiManager.setCardImage(extractedCards.get(0),firstCardImage);
            guiManager.setCardImage(extractedCards.get(1),secondCardImage);
            guiManager.setCardImage(extractedCards.get(2),thirdCardImage);
        }
    }

    /**
     * Called when first button is pressed
     * Send a new CardChoice with the card value of this image
     * @param event for action on this stage
     */
    public void firstCardButtonPressed(ActionEvent event){
        firstCardButton.setDisable(true);
        secondCardButton.setDisable(true);
        thirdCardButton.setDisable(true);
        firstCardImage.setOpacity(0.5);
        ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
        guiManager.sendObject(new PowerChoice(extractedCards.get(0)));
    }

    /**
     * Called when second button is pressed
     * Send a new CardChoice with the card value of this image
     * @param event for action on this stage
     */
    public void secondCardButtonPressed(ActionEvent event){
        firstCardButton.setDisable(true);
        secondCardButton.setDisable(true);
        thirdCardButton.setDisable(true);
        secondCardImage.setOpacity(0.5);
        ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
        guiManager.sendObject(new PowerChoice(extractedCards.get(1)));
    }

    /**
     * Called when third button is pressed
     * Send a new CardChoice with the card value of this image
     * @param event for action on this stage
     */
    public void thirdCardButtonPressed(ActionEvent event){
        firstCardButton.setDisable(true);
        secondCardButton.setDisable(true);
        thirdCardButton.setDisable(true);
        thirdCardImage.setOpacity(0.5);
        ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
        if(extractedCards.size() == 2){
            guiManager.sendObject(new PowerChoice(extractedCards.get(1)));
        }
        if(extractedCards.size() == 3){
            guiManager.sendObject(new PowerChoice(extractedCards.get(2)));
        }
    }

    /**
     * Set hand cursor when mouse enter on a button
     * @param event for action on this stage
     */
    public void setHandCursor(MouseEvent event){
        ((Node)event.getSource()).getScene().setCursor(Cursor.HAND);
    }

    /**
     * Set default cursor when mouse exit from a button
     * @param event for action on this stage
     */
    public void setDefaultCursor(MouseEvent event){
        try {
            ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
        } catch (Exception e){
            System.out.println("ERRORE: MouseEvent");
        }
    }

    /**
     * Load board.fxml on current stage
     */
    public void setBoardScene() {
        BoardSceneController boardSceneController = GuiManager.setLayout(guiManager.getStage().getScene(),"FXML/board.fxml");
        if (boardSceneController != null){
            boardSceneController.setBoardScene();
        }
    }

}
