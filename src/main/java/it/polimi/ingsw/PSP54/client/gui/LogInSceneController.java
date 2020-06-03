package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.utils.choices.PlayerCredentials;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class LogInSceneController {

    private String nickname;
    private int age;
    private GuiManager guiManager;
    private ActionEvent event;
    @FXML private TextField nicknameField;
    @FXML private TextField ageField;
    @FXML private Button startButton;
    @FXML private Label waitingLabel;
    @FXML private Label joinAGameLabel;
    @FXML private Label ageLabel;
    @FXML private Label nameLabel;

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
            joinAGameLabel.setFont(Font.loadFont("file:./resources/PapyrusCondensed.ttf",41));
            ageLabel.setFont(Font.loadFont("file:./resources/PapyrusCondensed.ttf",23));
            nameLabel.setFont(Font.loadFont("file:./resources/PapyrusCondensed.ttf",23));
            startButton.setFont(Font.loadFont("file:./resources/PapyrusCondensed.ttf",17));
            waitingLabel.setFont(Font.loadFont("file:./resources/PapyrusCondensed.ttf",18));
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
            saveNickname();
            saveAge();
            PlayerCredentials playerMessage = new PlayerCredentials(nickname,age);
            guiManager.sendObject(playerMessage);
        }
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
        NumberOfPlayersSceneController numberOfPlayersSceneController = GuiManager.setLayout(((Node)event.getSource()).getScene(),"file:./resources/FXML/number_of_players.fxml");
        if (numberOfPlayersSceneController != null){
            numberOfPlayersSceneController.setFont();
        }
    }

    /**
     * Load cards_choice.fxml on current stage
     */
    public void setCardsChoiceScene() {
        CardsChoiceSceneController cardsChoiceSceneController = GuiManager.setLayout(((Node)event.getSource()).getScene(),"file:./resources/FXML/cards_choice.fxml");
        if (cardsChoiceSceneController != null){
            cardsChoiceSceneController.setFont();
        }
    }

    /**
     * Load first_player_choice.fxml on current stage
     */
    public void setDeckChoiceScene(){
        DeckChoiceSceneController deckChoiceSceneController = GuiManager.setLayout(((Node)event.getSource()).getScene(),"file:./resources/FXML/deck_choice.fxml");
        if (deckChoiceSceneController != null){
            deckChoiceSceneController.setDeckChoiceScene();
        }
    }

}

