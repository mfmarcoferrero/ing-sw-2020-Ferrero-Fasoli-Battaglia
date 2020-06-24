package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.utils.choices.NewGameChoice;
import it.polimi.ingsw.PSP54.utils.choices.PlayerChoice;
import it.polimi.ingsw.PSP54.utils.choices.StopPlayingChoice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class EndSceneController {
    private GuiManager guiManager;
    private ActionEvent event;
    @FXML Label winOrLoseLabel;
    @FXML Label joinANewGameLabel;
    @FXML Label winnerNameLabel;
    @FXML Button yesButton;
    @FXML Button noButton;


    /**
     * Called when deck_choice.fxml is load
     */
    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setEndSceneController(this);
    }

    /**
     * Set font for buttons and labels
     * @param winnerName
     */
    public void setFont(String winnerName){
        if (guiManager.isWinner()) {
            if (winnerName.equals(guiManager.getMyName())){
                winOrLoseLabel.setText("YOU WIN !");
                winOrLoseLabel.setFont(Font.font("papyrus", 50));
                winnerNameLabel.setVisible(false);
            }
            else
                winOrLoseLabel.setText("YOU LOSE !  ");
                winOrLoseLabel.setFont(Font.font("papyrus", 50));
                winnerNameLabel.setText("THE WINNER IS: " + winnerName.toUpperCase());
                winnerNameLabel.setFont(Font.font("papyrus", 28));
                winnerNameLabel.setAlignment(Pos.CENTER);
        }
        if (guiManager.isLoser()){
            winOrLoseLabel.setText(winnerName.toUpperCase() + " LOSE !");
        }
        winOrLoseLabel.setAlignment(Pos.CENTER);
        joinANewGameLabel.setFont(Font.font("papyrus", 25));
        yesButton.setFont(Font.font("papyrus", 23));
        noButton.setFont(Font.font("papyrus", 23));
    }

    /**
     * On yes button clicked a RestartChoice message with game ended set false is send to Server
     */
    public void yesButtonPressed(){
        yesButton.setDisable(true);
        PlayerChoice restartChoice = new NewGameChoice();
        guiManager.sendObject(restartChoice);
    }

    /**
     * On no button clicked a RestartChoice message with game ended flag set true is send to Server
     */
    public void noButtonPressed(){
        noButton.setDisable(true);
        PlayerChoice restartChoice = new StopPlayingChoice();
        guiManager.sendObject(restartChoice);
    }

    /**
     * Set hand cursor on mouse entered on button
     * @param event
     */
    public void setHandCursor(MouseEvent event){
        ((Node) event.getSource()).getScene().setCursor(Cursor.HAND);
    }

    /**
     * Set default cursor on mouse exit on button
     * @param event
     */
    public void setDefaultCursor(MouseEvent event){
        ((Node) event.getSource()).getScene().setCursor(Cursor.DEFAULT);
    }

    /**
     * Load log_in.fxml on current stage
     */
    public void setLogInScene(){
        LogInSceneController logInSceneController = GuiManager.setLayout(guiManager.getStage().getScene(),"FXML/log_in.fxml");
        if (logInSceneController != null){
            logInSceneController.setFont();
        }
    }
}
