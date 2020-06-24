package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.utils.choices.BooleanChoice;
import it.polimi.ingsw.PSP54.utils.choices.BuildChoice;
import it.polimi.ingsw.PSP54.utils.choices.MoveChoice;
import it.polimi.ingsw.PSP54.utils.choices.WorkerChoice;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.util.Vector;

public class BoardSceneController {

    private GuiManager guiManager;
    private ImageView[][] imageBoxes = new ImageView[5][5];
    private ImageView[][] imageWorkerMap = new ImageView[5][5];
    private ImageView myCardImage;
    private MouseEvent event;
    @FXML private GridPane gridPane;
    @FXML private ImageView cardImage_1;
    @FXML private ImageView cardImage_2;
    @FXML private ImageView cardImage_3;
    @FXML private Label labelPlayer_1;
    @FXML private Label labelPlayer_2;
    @FXML private Label labelPlayer_3;
    @FXML private Label messageLabel;
    @FXML private Label panelMessageLabel;
    @FXML private Button firstButton;
    @FXML private Button secondButton;


    /**
     * Called when board.fxml is load
     */
    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setBoardSceneController(this);
    }


    /**
     * Set font and text alignment of labels and buttons
     * Set cards images and player names on main game scene
     * Save ImageView references of board GridPane
     */
    public void setBoardScene(){
        for(Node node: gridPane.getChildren()){
            if(node instanceof ImageView) {
                imageBoxes[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = (ImageView) node;
            }
            if (node instanceof AnchorPane){
                imageWorkerMap[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = (ImageView)((AnchorPane) node).getChildren().get(0);
            }
        }
        labelPlayer_1.setFont(Font.font("papyrus",24));
        labelPlayer_1.setAlignment(Pos.CENTER);
        labelPlayer_2.setFont(Font.font("papyrus",24));
        labelPlayer_2.setAlignment(Pos.CENTER);
        labelPlayer_3.setFont(Font.font("papyrus",24));
        labelPlayer_3.setAlignment(Pos.CENTER);
        messageLabel.setFont(Font.font("papyrus",35));
        messageLabel.setAlignment(Pos.CENTER);
        panelMessageLabel.setFont(Font.font("papyrus",26));
        panelMessageLabel.setAlignment(Pos.CENTER);
        firstButton.setFont(Font.font("papyrus",15));
        firstButton.setAlignment(Pos.CENTER);
        secondButton.setFont(Font.font("papyrus",15));
        secondButton.setAlignment(Pos.CENTER);
        setImageCards(guiManager.getCardValues());
        setLabelNames(guiManager.getNames());
    }

    /**
     * Set worker and building images on every ImageView in GridPane
     * @param board is an instance of a board that server send to client
     */
    public void setBoardImage(Box[][] board){
        for (int i = 0; i < Game.BOARD_SIZE; i++){
            for (int j = 0; j < Game.BOARD_SIZE; j++){
                setBuildingImage(imageBoxes[i][j],board[i][j]);
                setWorkerImage(imageWorkerMap[i][j],board[i][j]);
            }
        }
    }

    /**
     * Set the image of a building on an ImageView, using an instance of Box
     * @param boxImageView ImageView that represent a box in our GridPane
     * @param box contains variables for levels and dome
     */
    private void setBuildingImage(ImageView boxImageView, Box box){
        switch (box.getLevel()) {
            case 0:
                if (box.isDome()) {
                    boxImageView.setImage(new Image("icons/Dome_2.png"));
                }
                if (!box.isDome()){
                    boxImageView.setImage(null);
                }
                break;
            case 1:
                if (box.isDome()){
                    boxImageView.setImage(new Image("icons/First+Dome_Building.png"));
                }
                if (!box.isDome()){
                    boxImageView.setImage(new Image("icons/FirstLevel.png"));
                }
                break;
            case 2:
                if (box.isDome()){
                    boxImageView.setImage(new Image("icons/First+Second+Dome_Building.png"));
                }
                if (!box.isDome()){
                    boxImageView.setImage(new Image("icons/First+Second_Building.png"));
                }
                break;
            case 3:
                if (box.isDome()){
                    boxImageView.setImage(new Image("icons/First+Second+Third+Dome_Building.png"));
                }
                if (!box.isDome()){
                    boxImageView.setImage(new Image("icons/First+Second+Third_Building.png"));
                }
                break;
        }
    }

    /**
     * Set the image of a worker on an ImageView, using an instance of Box
     * @param boxImageView ImageView that represent a box in our GridPane
     * @param box contains variables for levels and dome
     */
    public void setWorkerImage(ImageView boxImageView, Box box){
        if (box.getWorker()!=null) {
            switch (box.getWorker().getOwner().getColor()) {
                case "blue":
                    if (box.getWorker().getMale()) {
                        boxImageView.setImage(new Image("icons/MaleWorker_Blue.png"));
                    }
                    else {
                        boxImageView.setImage(new Image("icons/FemaleWorker_Blue.png"));
                    }
                    break;
                case "red":
                    if (box.getWorker().getMale()) {
                        boxImageView.setImage(new Image("icons/MaleWorker_Red.png"));
                    }
                    else {
                        boxImageView.setImage(new Image("icons/FemaleWorker_Red.png"));
                    }                    break;
                case "yellow":
                    if (box.getWorker().getMale()) {
                        boxImageView.setImage(new Image("icons/MaleWorker_Yellow.png"));
                    }
                    else {
                        boxImageView.setImage(new Image("icons/FemaleWorker_Yellow.png"));
                    }                    break;
            }
        }
        else
            boxImageView.setImage(null);
    }

    private ImageView getMyCardImage(){
        int myCardImageIndex = 0;
        for(int i = 0; i < guiManager.getNames().size(); i++){
            if (guiManager.getNames().get(i).equals(guiManager.getMyName())){
                myCardImageIndex = i;
            }
        }
        switch (myCardImageIndex){
            case 1:
                return cardImage_1;
            case 2:
                return cardImage_2;
            case 3:
                return cardImage_3;
        }
        return null;
    }

    /**
     * Set main label text used for messages that reference to player's action
     * @param message
     */
    public void setMessageLabel(String message){
        messageLabel.setText(message);
        messageLabel.setAlignment(Pos.CENTER);
    }

    /**
     * Set ImageView with the image every player's card
     * @param cardValues
     */
    public void setImageCards(Vector<Integer> cardValues){
        if (cardValues.size() == 3) {
            guiManager.setCardImage(cardValues.get(0), cardImage_1);
            guiManager.setCardImage(cardValues.get(1), cardImage_2);
            guiManager.setCardImage(cardValues.get(2), cardImage_3);
        }
        if (cardValues.size() == 2){
            guiManager.setCardImage(cardValues.get(0), cardImage_1);
            guiManager.setCardImage(cardValues.get(1), cardImage_2);
            cardImage_3.setVisible(false);
        }

    }

    /**
     * Set label texts that represent players names
     * @param playerNames
     */
    public void setLabelNames(Vector<String> playerNames){
        if (playerNames.size() == 3) {
            labelPlayer_1.setText(playerNames.get(0).toUpperCase());
            labelPlayer_2.setText(playerNames.get(1).toUpperCase());
            labelPlayer_3.setText(playerNames.get(2).toUpperCase());
        }
        if (playerNames.size() == 2){
            labelPlayer_1.setText(playerNames.get(0).toUpperCase());
            labelPlayer_2.setText(playerNames.get(1).toUpperCase());
            labelPlayer_3.setVisible(false);
        }
        underlineMyName();
        myCardImage = getMyCardImage();
    }

    /**
     * Underline label text that is the name of the player using this GUI
     */
    private void underlineMyName(){
        if (guiManager.getMyName().toUpperCase().equals(labelPlayer_1.getText())){
            labelPlayer_1.setUnderline(true);
        }
        if (guiManager.getMyName().toUpperCase().equals(labelPlayer_2.getText())){
            labelPlayer_2.setUnderline(true);
        }
        if (guiManager.getMyName().toUpperCase().equals(labelPlayer_3.getText())){
            labelPlayer_3.setUnderline(true);
        }
    }

    /**
     * Set label text and choice buttons text for male or female worker choice
     * Set choice buttons visible
     */
    public void showMaleOrFemaleMessage(){
        panelMessageLabel.setText("MALE or FEMALE");
        panelMessageLabel.setAlignment(Pos.CENTER);
        firstButton.setText("Male");
        secondButton.setText("Female");
        panelMessageLabel.setVisible(true);
        firstButton.setVisible(true);
        secondButton.setVisible(true);
    }

    /**
     * Set label text and choice buttons text for double build choice
     * Set choice buttons visible
     */
    public void showMoveAgainMessage(){
        panelMessageLabel.setText("MOVE AGAIN?");
        panelMessageLabel.setAlignment(Pos.CENTER);
        firstButton.setText("Yes");
        secondButton.setText("No");
        firstButton.setVisible(true);
        secondButton.setVisible(true);
        panelMessageLabel.setVisible(true);
    }

    /**
     * Set label text and choice buttons text for double build choice
     * Set choice buttons visible
     */
    public void showBuildAgainMessage(){
        panelMessageLabel.setText("BUILD AGAIN?");
        panelMessageLabel.setAlignment(Pos.CENTER);
        firstButton.setText("Yes");
        secondButton.setText("No");
        firstButton.setVisible(true);
        secondButton.setVisible(true);
        panelMessageLabel.setVisible(true);
    }

    /**
     * Set label text and choice buttons text for build or dome choice
     * Set choice button visible
     */
    public void showBuildOrDomeMessage(){
        panelMessageLabel.setText("BUILD A DOME?");
        panelMessageLabel.setAlignment(Pos.CENTER);
        firstButton.setText("Yes");
        secondButton.setText("No");
        firstButton.setVisible(true);
        secondButton.setVisible(true);
        panelMessageLabel.setVisible(true);
    }

    /**
     * Set label text and choice buttons text for build before move choice
     * Set choice buttons visible
     */
    public void showBuildFirstMessage(){
        panelMessageLabel.setText("BUILD BEFORE MOVE?");
        panelMessageLabel.setAlignment(Pos.CENTER);
        firstButton.setText("Yes");
        secondButton.setText("No");
        firstButton.setVisible(true);
        secondButton.setVisible(true);
        panelMessageLabel.setVisible(true);
    }

    /**
     * On first choice button clicked a boolean choice with true flag is sent to server
     * If is a male or female worker choice, a WorkerChoice message is sent with male flag set true
     */
    public void maleButtonClicked(){
        if (guiManager.isBooleanChoice()){
            guiManager.sendObject(new BooleanChoice(true));
            guiManager.setBooleanChoice(false);
            panelMessageLabel.setVisible(false);
            firstButton.setVisible(false);
            secondButton.setVisible(false);
        }
        else {
            guiManager.sendObject(new WorkerChoice(true));
            panelMessageLabel.setVisible(false);
            firstButton.setVisible(false);
            secondButton.setVisible(false);
        }
    }

    /**
     * On second choice button clicked a boolean choice with false flag is sent to server
     * If is a male or female worker choice, a WorkerChoice message is sent with male flag set false
     */
    public void femaleButtonClicked(){
        if (guiManager.isBooleanChoice()){
            guiManager.sendObject(new BooleanChoice(false));
            guiManager.setBooleanChoice(false);
            panelMessageLabel.setVisible(false);
            firstButton.setVisible(false);
            secondButton.setVisible(false);
        }
        else {
            guiManager.sendObject(new WorkerChoice(false));
            panelMessageLabel.setVisible(false);
            firstButton.setVisible(false);
            secondButton.setVisible(false);
        }
    }

    /**
     * If a GridPane element is clicked, a new Move or Build message is sent with GridPane coordinates
     * @param event
     */
    public void boxClicked(MouseEvent event){
        Node source = (Node)event.getSource();
        this.event = event;
        mouseExitFromBox(event);
        if (guiManager.isBoxChoice()) {
            if (guiManager.isFirstWorkerSet() || guiManager.isSecondWorkerSet() || guiManager.isMoveChoice()) {
                guiManager.sendObject(new MoveChoice(GridPane.getRowIndex(source), GridPane.getColumnIndex(source)));
                guiManager.setBoxChoice(false);
                if (guiManager.isSecondWorkerSet()) {
                    setMessageLabel(null);
                    guiManager.setSecondWorkerSet(false);
                }
                if (guiManager.isMoveChoice()){
                    setMessageLabel(null);
                    guiManager.setMoveChoice(false);
                }
            }
            if (guiManager.isBuildChoice()){
                messageLabel.setText(null);
                guiManager.sendObject(new BuildChoice(GridPane.getRowIndex(source), GridPane.getColumnIndex(source)));
                guiManager.setBoxChoice(false);
                guiManager.setBuildChoice(false);
            }
        }
    }

    /**
     * If a box can be clicked, when cursor enter on a GridPane element hand cursor and
     * red background is set on that box
     * @param event
     */
    public void mouseEnterOnBox(MouseEvent event){
        if (guiManager.isBoxChoice()) {
            ((Node) event.getSource()).setStyle("-fx-background-color: #E4001F;");
            ((Node) event.getSource()).setOpacity(0.6);
            ((Node) event.getSource()).getScene().setCursor(Cursor.HAND);
        }
    }

    /**
     * When cursor exit from GridPane element default cursor and no background colour is set
     * @param event
     */
    public void mouseExitFromBox(MouseEvent event){
        if (guiManager.isBoxChoice()) {
            try {
                ((Node) event.getSource()).setStyle("-fx-background-color: null;");
                ((Node) event.getSource()).setOpacity(1);
                ((Node) event.getSource()).getScene().setCursor(Cursor.DEFAULT);
            } catch (Exception e ){
                System.out.println("Problemi con mouse event");
            }
        }
    }

    /**
     * When mouse enter on a button hand cursor is set
     * @param event
     */
    public void mouseEnterOnButton(MouseEvent event){
        ((Node) event.getSource()).getScene().setCursor(Cursor.HAND);
    }

    /**
     * When mouse exit from a button default cursor is set
     * @param event
     */
    public void mouseExitFromButton(MouseEvent event){
        ((Node) event.getSource()).getScene().setCursor(Cursor.DEFAULT);
    }

    /**
     * Load end_scene.fxml on current stage and set end_scene font
     * @param winnerName
     */
    public void setEndScene(String winnerName){
        guiManager.getStage().setWidth(600);
        guiManager.getStage().setHeight(350);
        guiManager.getStage().centerOnScreen();
        EndSceneController endSceneController = GuiManager.setLayout(guiManager.getStage().getScene(),"FXML/end_scene.fxml");
        if (endSceneController != null){
            endSceneController.setFont(winnerName);
        }
    }

}

