package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.utils.choices.NewGameChoice;
import it.polimi.ingsw.PSP54.utils.choices.PlayerChoice;
import it.polimi.ingsw.PSP54.utils.choices.StopPlayingChoice;
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
        guiManager.setBoard(null);
    }

    /**
     * Set font for buttons and labels
     * @param winnerName name of winner player
     * @param endForDisconnection true if a player lose connection
     */
    public void setFont(String winnerName, boolean endForDisconnection){
        if (endForDisconnection){
            winOrLoseLabel.setText("ONE OF THE PLAYERS LOST CONNECTION !");
            winOrLoseLabel.setFont(Font.font("papyrus", 40));
        }
        else{
            if (guiManager.isWinner()) {
                winOrLoseLabel.setText("YOU WIN !");
                winOrLoseLabel.setFont(Font.font("papyrus", 50));
            }
            else {
                winOrLoseLabel.setText("YOU LOSE !");
                winOrLoseLabel.setFont(Font.font("papyrus", 50));
                if (winnerName != null) {
                    winnerNameLabel.setText("THE WINNER IS: " + winnerName.toUpperCase());
                    winnerNameLabel.setFont(Font.font("papyrus", 28));
                    winnerNameLabel.setAlignment(Pos.CENTER);
                    winnerNameLabel.setVisible(true);
                }
            }
            winOrLoseLabel.setAlignment(Pos.CENTER);
            joinANewGameLabel.setFont(Font.font("papyrus", 25));
            yesButton.setFont(Font.font("papyrus", 23));
            noButton.setFont(Font.font("papyrus", 23));
        }
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
     * @param event for action on this stage
     */
    public void setHandCursor(MouseEvent event){
        ((Node) event.getSource()).getScene().setCursor(Cursor.HAND);
    }

    /**
     * Set default cursor on mouse exit on button
     * @param event for action on this stage
     */
    public void setDefaultCursor(MouseEvent event){
        ((Node) event.getSource()).getScene().setCursor(Cursor.DEFAULT);
    }

}
