package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.utils.choices.PlayerCredentials;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import javax.imageio.ImageIO;
import java.net.URL;

public class LogInSceneController {

    private String nickname;
    private int age;
    private GuiManager guiManager;
    private ActionEvent event;
    @FXML private ImageView islandImageView;
    @FXML private TextField nicknameField;
    @FXML private TextField ageField;
    @FXML private Button startButton;
    @FXML private Label waitingLabel;
    @FXML private Label joinAGameLabel;
    @FXML private Label ageLabel;
    @FXML private Label nameLabel;
    @FXML private Label invalidNameLabel;

    /**
     * Called when log_in.fxml is load
     */
    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setLogInSceneController(this);
    }

    public void setFont(){

        try {
            joinAGameLabel.setFont(Font.font("papyrus",41));
            ageLabel.setFont(Font.font("papyrus", 23));
            nameLabel.setFont(Font.font("papyrus",23));
            startButton.setFont(Font.font("papyrus",17));
            waitingLabel.setFont(Font.font("papyrus",18));
            invalidNameLabel.setFont(Font.font("papyrus",16));
            islandImageView.setImage(new Image("icons/title_island.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when start button is pressed
     * If both texfields are not empty, player credentials are send
     * @param event
     */
    public void startButtonPressed(ActionEvent event) {
        this.event = event;
        if (!nicknameField.getText().trim().isEmpty() && !ageField.getText().trim().isEmpty()) {
            startButton.setVisible(false);
            waitingLabel.setVisible(true);
            invalidNameLabel.setVisible(false);
            saveNickname();
            saveAge();
            PlayerCredentials playerMessage = new PlayerCredentials(nickname,age);
            guiManager.sendObject(playerMessage);
        }
    }

    /**
     * Called when a nickname already chosen is in your game
     */
    public void setInvalidNameLabel(){
        startButton.setVisible(true);
        waitingLabel.setVisible(false);
        invalidNameLabel.setVisible(true);
    }

    /**
     * Get nickname from textfield
     */
    @FXML
    private void saveNickname(){
        nickname = nicknameField.getText();
        guiManager.setMyName(nickname);
    }

    /**
     * Get age from textfield
     */
    @FXML
    private void saveAge(){
        String ageString = ageField.getText();
        age = Integer.parseInt(ageString);
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
     * Load number_of_players.fxml on current stage
     */
    public void setNumberOfPlayersScene() {
        NumberOfPlayersSceneController numberOfPlayersSceneController = GuiManager.setLayout(((Node)event.getSource()).getScene(),"FXML/number_of_players.fxml");
        if (numberOfPlayersSceneController != null){
            numberOfPlayersSceneController.setFont();
        }
    }

    /**
     * Load cards_choice.fxml on current stage
     */
    public void setCardsChoiceScene() {
        CardsChoiceSceneController cardsChoiceSceneController = GuiManager.setLayout(((Node)event.getSource()).getScene(),"FXML/cards_choice.fxml");
        if (cardsChoiceSceneController != null){
            cardsChoiceSceneController.setFont();
        }
    }

    /**
     * Load first_player_choice.fxml on current stage
     */
    public void setDeckChoiceScene(){
        DeckChoiceSceneController deckChoiceSceneController = GuiManager.setLayout(((Node)event.getSource()).getScene(),"FXML/deck_choice.fxml");
        if (deckChoiceSceneController != null){
            deckChoiceSceneController.setDeckChoiceScene();
        }
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}

