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

public class LogInSceneController {

    private String nickname;
    private int age;
    private GuiManager guiManager;
    private ActionEvent event;
    @FXML private TextField nicknameField;
    @FXML private TextField ageField;
    @FXML private Button startButton;
    @FXML private Label waitingLabel;

    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setLogInSceneController(this);
    }

    public void startButtonPressed(ActionEvent event) {
        this.event = event;
        if (!nicknameField.getText().trim().isEmpty() && !ageField.getText().trim().isEmpty()) {
            startButton.setVisible(false);
            waitingLabel.setVisible(true);
            saveNickname();
            saveAge();
            PlayerCredentials playerMessage = new PlayerCredentials(nickname,age);
            guiManager.sendObject(playerMessage);
            System.out.println("Ho inviato il PlayerMessage di: " + nickname + "\nAge: " + age);
        }
    }

    @FXML
    private void saveNickname(){
        nickname = nicknameField.getText();
    }

    @FXML
    private void saveAge(){
        String ageString = ageField.getText();
        age = Integer.parseInt(ageString);
    }

    public void setHandCursor(MouseEvent event){
        ((Node)event.getSource()).getScene().setCursor(Cursor.HAND);
    }

    public void setDefaultCursor(MouseEvent event){
        ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
    }

    public void setNumberOfPlayersScene() {
        GuiManager.setLayout(((Node)event.getSource()).getScene(),"file:./resources/FXML/number_of_players.fxml");
    }

    public void setCardsChoiceScene() {
        GuiManager.setLayout(((Node)event.getSource()).getScene(),"file:./resources/FXML/cards_choice.fxml");
    }

    public void setBoardScene() {
        GuiManager.setLayout(((Node)event.getSource()).getScene(),"file:./resources/FXML/board.fxml");
    }

}
