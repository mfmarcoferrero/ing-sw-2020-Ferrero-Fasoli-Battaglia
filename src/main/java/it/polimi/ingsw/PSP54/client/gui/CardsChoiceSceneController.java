package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.utils.choices.CardChoice;
import it.polimi.ingsw.PSP54.utils.messages.CardsMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Vector;

public class CardsChoiceSceneController {

    private GuiManager guiManager;
    private ActionEvent event;
    private Vector<Integer> extractedCards;
    @FXML private ImageView firstCardImage;
    @FXML private ImageView secondCardImage;
    @FXML private ImageView thirdCardImage;
    @FXML private Button firstCardButton;
    @FXML private Button secondCardButton;
    @FXML private Button thirdCardButton;


    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setCardsChoiceSceneController(this);
        setCardsToDisplay(guiManager.getCardsToDisplay());
    }

    public void setCardsToDisplay (CardsMessage cardsToDisplay){
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


    public void firstCardButtonPressed(ActionEvent event){
        this.event = event;
        System.out.println("First button pressed");
        guiManager.sendObject(new CardChoice(extractedCards.get(0)));
    }

    public void secondCardButtonPressed(ActionEvent event){
        this.event = event;
        System.out.println("Second button pressed");
        guiManager.sendObject(new CardChoice(extractedCards.get(1)));
    }

    public void thirdCardButtonPressed(ActionEvent event){
        this.event = event;
        System.out.println("Third button pressed");
        if(extractedCards.size() == 2){
            guiManager.sendObject(new CardChoice(extractedCards.get(1)));
        }
        if(extractedCards.size() == 3){
            guiManager.sendObject(new CardChoice(extractedCards.get(2)));
        }
    }

    public void setHandCursor(MouseEvent event){
        ((Node)event.getSource()).getScene().setCursor(Cursor.HAND);
    }

    public void setDefaultCursor(MouseEvent event){
        ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
    }

    public void setBoardScene() {
        GuiManager.setLayout(((Node)event.getSource()).getScene(),"file:./resources/FXML/board.fxml");
    }

}
