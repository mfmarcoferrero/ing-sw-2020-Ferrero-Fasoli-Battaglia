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
        chooseNumberOfPlayersLabel.setFont(Font.loadFont("file:./resources/PapyrusCondensed.ttf",35));
        if (guiManager.getPlayers().size() == 2){
            firstPlayerButton.setText(guiManager.getPlayers().get(0).getPlayerName());
            thirdPlayerButton.setText(guiManager.getPlayers().get(1).getPlayerName());
            secondPlayerButton.setVisible(false);
        }
        if (guiManager.getPlayers().size() == 3){
            firstPlayerButton.setText(guiManager.getPlayers().get(0).getPlayerName());
            secondPlayerButton.setText(guiManager.getPlayers().get(1).getPlayerName());
            thirdPlayerButton.setText(guiManager.getPlayers().get(2).getPlayerName());
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
        System.out.println("Ho scelto: " + guiManager.getPlayers().get(0).getPlayerName());
        guiManager.sendObject(new StartPlayerChoice(0));
        firstPlayerButton.setDisable(true);
        secondPlayerButton.setDisable(true);
        thirdPlayerButton.setDisable(true);
    }

    public void secondPlayerButtonPressed(ActionEvent event) {
        this.event = event;
        System.out.println("Ho scelto: " + guiManager.getPlayers().get(1).getPlayerName());
        guiManager.sendObject(new StartPlayerChoice(1));
        firstPlayerButton.setDisable(true);
        secondPlayerButton.setDisable(true);
        thirdPlayerButton.setDisable(true);
    }

    public void thirdPlayerButtonPressed(ActionEvent event) {
        this.event = event;
        if (guiManager.getPlayers().size() == 2) {
            System.out.println("Ho scelto: " + guiManager.getPlayers().get(1).getPlayerName());
            guiManager.sendObject(new StartPlayerChoice(1));
        }
        if (guiManager.getPlayers().size() == 3){
            System.out.println("Ho scelto: " + guiManager.getPlayers().get(2).getPlayerName());
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
        ((Node)event.getSource()).getScene().getWindow().setWidth(1065);
        ((Node)event.getSource()).getScene().getWindow().setHeight(620);
        ((Node)event.getSource()).getScene().getWindow().centerOnScreen();
        BoardSceneController boardSceneController = GuiManager.setLayout(((Node)event.getSource()).getScene(),"file:./resources/FXML/board.fxml");
        if (boardSceneController != null){
            boardSceneController.setBoardScene();
        }
    }
}
