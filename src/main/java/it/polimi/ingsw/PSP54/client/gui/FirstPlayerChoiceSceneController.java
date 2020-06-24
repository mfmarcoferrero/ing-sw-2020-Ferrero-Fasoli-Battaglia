package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.utils.choices.StartPlayerChoice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.util.HashMap;

public class FirstPlayerChoiceSceneController {

    private GuiManager guiManager;
    private int cardSelected = 0;
    private ActionEvent event;
    private HashMap<Integer,String> extractedCards = new HashMap<>();
    @FXML private Label chooseNumberOfPlayersLabel;
    @FXML private Button firstPlayerButton;
    @FXML private Button secondPlayerButton;
    @FXML private Button thirdPlayerButton;

    /**
     * Called when deck_choice.fxml is load
     */
    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setFirstPlayerChoiceSceneController(this);
    }

    public void setFont(){
        chooseNumberOfPlayersLabel.setFont(Font.font("papyrus",35));
        if (guiManager.getPlayers().size() == 2){
            firstPlayerButton.setText(guiManager.getPlayers().get(0).getPlayerName().toUpperCase());
            firstPlayerButton.setFont(Font.font("papyrus",16));
            thirdPlayerButton.setText(guiManager.getPlayers().get(1).getPlayerName().toUpperCase());
            thirdPlayerButton.setFont(Font.font("papyrus",16));
            secondPlayerButton.setVisible(false);
        }
        if (guiManager.getPlayers().size() == 3){
            firstPlayerButton.setText(guiManager.getPlayers().get(0).getPlayerName().toUpperCase());
            firstPlayerButton.setFont(Font.font("papyrus",16));
            secondPlayerButton.setText(guiManager.getPlayers().get(1).getPlayerName().toUpperCase());
            secondPlayerButton.setFont(Font.font("papyrus",16));
            thirdPlayerButton.setText(guiManager.getPlayers().get(2).getPlayerName().toUpperCase());
            thirdPlayerButton.setFont(Font.font("papyrus",16));
        }
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

    public void firstPlayerButtonPressed(ActionEvent event) {
        this.event = event;
        guiManager.sendObject(new StartPlayerChoice(0));
        firstPlayerButton.setDisable(true);
        secondPlayerButton.setDisable(true);
        thirdPlayerButton.setDisable(true);
    }

    public void secondPlayerButtonPressed(ActionEvent event) {
        this.event = event;
        guiManager.sendObject(new StartPlayerChoice(1));
        firstPlayerButton.setDisable(true);
        secondPlayerButton.setDisable(true);
        thirdPlayerButton.setDisable(true);
    }

    public void thirdPlayerButtonPressed(ActionEvent event) {
        this.event = event;
        if (guiManager.getPlayers().size() == 2) {
            guiManager.sendObject(new StartPlayerChoice(1));
        }
        if (guiManager.getPlayers().size() == 3){
            guiManager.sendObject(new StartPlayerChoice(2));
        }
        firstPlayerButton.setDisable(true);
        secondPlayerButton.setDisable(true);
        thirdPlayerButton.setDisable(true);
    }

    /**
     * Load board.fxml on current stage
     */
    public void setBoardScene() {
        guiManager.setBoardSceneSize();
        BoardSceneController boardSceneController = GuiManager.setLayout(guiManager.getStage().getScene(),"FXML/board.fxml");
        if (boardSceneController != null){
            boardSceneController.setBoardScene();
        }
    }
}

