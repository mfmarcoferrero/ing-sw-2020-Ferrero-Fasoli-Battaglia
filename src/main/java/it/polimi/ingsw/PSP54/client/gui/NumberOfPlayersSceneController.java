package it.polimi.ingsw.PSP54.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class NumberOfPlayersSceneController {

    private GuiManager guiManager;
    private ActionEvent event;

    @FXML private Button twoButton;
    @FXML private Button threeButton;
    @FXML private Label waitingLabel;

    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setNumberOfPlayersSceneController(this);
    }

    public void twoButtonPressed (ActionEvent event) {
        this.event = event;
        twoButton.setVisible(false);
        threeButton.setVisible(false);
        waitingLabel.setVisible(true);
        guiManager.sendObject(2);
        System.out.println("Ho scelto due giocatori");
    }

    public void threeButtonPressed (ActionEvent event) {
        this.event = event;
        twoButton.setVisible(false);
        threeButton.setVisible(false);
        waitingLabel.setVisible(true);
        guiManager.sendObject(3);
        System.out.println("Ho scelto tre giocatori");
    }

    public void setHandCursor(MouseEvent event){
        ((Node)event.getSource()).getScene().setCursor(Cursor.HAND);
    }

    public void setDefaultCursor(MouseEvent event){
        ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
    }

    public void setCardsChoiceScene() {
        GuiManager.setLayout(((Node)event.getSource()).getScene(),"file:./resources/FXML/cards_choice.fxml");
    }
}
