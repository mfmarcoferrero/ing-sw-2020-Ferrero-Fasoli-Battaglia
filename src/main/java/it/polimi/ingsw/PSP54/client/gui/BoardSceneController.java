package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.utils.choices.BooleanChoice;
import it.polimi.ingsw.PSP54.utils.choices.BuildChoice;
import it.polimi.ingsw.PSP54.utils.choices.MoveChoice;
import it.polimi.ingsw.PSP54.utils.choices.WorkerChoice;
import it.polimi.ingsw.PSP54.utils.messages.CardsPlayersMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.scene.text.TextAlignment;

import java.util.Vector;

public class BoardSceneController {

    private GuiManager guiManager;
    private ImageView[][] imageBoxes = new ImageView[5][5];
    private ImageView[][] imageWorkerMap = new ImageView[5][5];
    private ImageView myCardImage;
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

    public void setBoardScene(){
        for(Node node: gridPane.getChildren()){
            if(node instanceof ImageView) {
                imageBoxes[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = (ImageView) node;
            }
            if (node instanceof AnchorPane){
                imageWorkerMap[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = (ImageView)((AnchorPane) node).getChildren().get(0);
            }
        }
        labelPlayer_1.setFont(Font.loadFont("file:./resources/PapyrusCondensed.ttf",24));
        labelPlayer_1.setTextAlignment(TextAlignment.CENTER);
        labelPlayer_2.setFont(Font.loadFont("file:./resources/PapyrusCondensed.ttf",24));
        labelPlayer_2.setTextAlignment(TextAlignment.CENTER);
        labelPlayer_3.setFont(Font.loadFont("file:./resources/PapyrusCondensed.ttf",24));
        labelPlayer_3.setTextAlignment(TextAlignment.CENTER);
        messageLabel.setFont(Font.loadFont("file:./resources/PapyrusCondensed.ttf",35));
        messageLabel.setTextAlignment(TextAlignment.CENTER);
        panelMessageLabel.setFont(Font.loadFont("file:./resources/PapyrusCondensed.ttf",26));
        panelMessageLabel.setTextAlignment(TextAlignment.CENTER);
        firstButton.setFont(Font.loadFont("file:./resources/PapyrusCondensed.ttf",15));
        firstButton.setTextAlignment(TextAlignment.CENTER);
        secondButton.setFont(Font.loadFont("file:./resources/PapyrusCondensed.ttf",15));
        secondButton.setTextAlignment(TextAlignment.CENTER);
        setImageCards(guiManager.getCardValues());
        setLabelNames(guiManager.getNames());
    }

    public void setBoardImage(Box[][] board){
        for (int i = 0; i < Game.BOARD_SIZE; i++){
            for (int j = 0; j < Game.BOARD_SIZE; j++){
                setBuildingImage(imageBoxes[i][j],board[i][j]);
                setWorkerImage(imageWorkerMap[i][j],board[i][j]);
            }
        }
    }

    private void setBuildingImage(ImageView boxImageView, Box box){
        switch (box.getLevel()) {
            case 0:
                if (box.isDome()) {
                    boxImageView.setImage(new Image("file:./resources/icons/Dome_2.png"));
                }
                if (!box.isDome()){
                    boxImageView.setImage(null);
                }
                break;
            case 1:
                if (box.isDome()){
                    boxImageView.setImage(new Image("file:./resources/icons/First+Dome_Building.png"));
                }
                if (!box.isDome()){
                    boxImageView.setImage(new Image("file:./resources/icons/FirstLevel.png"));
                }
                break;
            case 2:
                if (box.isDome()){
                    boxImageView.setImage(new Image("file:./resources/icons/First+Second+Dome_Building.png"));
                }
                if (!box.isDome()){
                    boxImageView.setImage(new Image("file:./resources/icons/First+Second_Building.png"));
                }
                break;
            case 3:
                if (box.isDome()){
                    boxImageView.setImage(new Image("file:./resources/icons/First+Second+Third+Dome_Building.png"));
                }
                if (!box.isDome()){
                    boxImageView.setImage(new Image("file:./resources/icons/First+Second+Third_Building.png"));
                }
                break;
        }
    }

    public void setWorkerImage(ImageView boxImageView, Box box){
        if (box.getWorker()!=null) {
            switch (box.getWorker().getOwner().getColor()) {
                case "blue":
                    if (box.getWorker().getMale()) {
                        boxImageView.setImage(new Image("file:./resources/icons/MaleWorker_Blue.png"));
                    }
                    else {
                        boxImageView.setImage(new Image("file:./resources/icons/FemaleWorker_Blue.png"));
                    }
                    break;
                case "red":
                    if (box.getWorker().getMale()) {
                        boxImageView.setImage(new Image("file:./resources/icons/MaleWorker_Red.png"));
                    }
                    else {
                        boxImageView.setImage(new Image("file:./resources/icons/FemaleWorker_Red.png"));
                    }                    break;
                case "yellow":
                    if (box.getWorker().getMale()) {
                        boxImageView.setImage(new Image("file:./resources/icons/MaleWorker_Yellow.png"));
                    }
                    else {
                        boxImageView.setImage(new Image("file:./resources/icons/FemaleWorker_Yellow.png"));
                    }                    break;
            }
        }
        else
            boxImageView.setImage(null);
    }

    public void setCardImageTurn(int playerIndex){
        switch (playerIndex){
            case 0:
                cardImage_1.setOpacity(1);
                cardImage_2.setOpacity(0.5);
                if (cardImage_3.isVisible()) {
                    cardImage_3.setOpacity(0.5);
                }
                break;
            case 1:
                cardImage_1.setOpacity(0.5);
                cardImage_2.setOpacity(1);
                if (cardImage_3.isVisible()) {
                    cardImage_3.setOpacity(0.5);
                }
                break;
            case 2:
                cardImage_1.setOpacity(0.5);
                cardImage_2.setOpacity(0.5);
                if (cardImage_3.isVisible()) {
                    cardImage_3.setOpacity(1);
                }
                break;
        }
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



    public void setMessageLabel(String message){
        messageLabel.setText(message);
    }

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

    public void showPanelMessage(){
        panelMessageLabel.setText("MALE or FEMALE");
        firstButton.setText("Male");
        secondButton.setText("Female");
        panelMessageLabel.setVisible(true);
        firstButton.setVisible(true);
        secondButton.setVisible(true);
    }

    public void showMoveAgainMessage(){
        panelMessageLabel.setText("MOVE AGAIN?");
        firstButton.setText("Yes");
        secondButton.setText("No");
        firstButton.setVisible(true);
        secondButton.setVisible(true);
        panelMessageLabel.setVisible(true);
    }

    public void showBuildAgainMessage(){
        panelMessageLabel.setText("BUILD AGAIN?");
        firstButton.setText("Yes");
        secondButton.setText("No");
        firstButton.setVisible(true);
        secondButton.setVisible(true);
        panelMessageLabel.setVisible(true);
    }

    public void showBuildOrDomeMessage(){
        panelMessageLabel.setText("BUILD A DOME?");
        firstButton.setText("Yes");
        secondButton.setText("No");
        firstButton.setVisible(true);
        secondButton.setVisible(true);
        panelMessageLabel.setVisible(true);
    }

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

    public void boxClicked(MouseEvent event){
        Node source = (Node)event.getSource();
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

    public void mouseEnterOnBox(MouseEvent event){
        if (guiManager.isBoxChoice()) {
            ((Node) event.getSource()).setStyle("-fx-background-color: #E4001F;");
            ((Node) event.getSource()).setOpacity(0.6);
            ((Node) event.getSource()).getScene().setCursor(Cursor.HAND);
        }
    }

    public void mouseExitFromBox(MouseEvent event){
        ((Node) event.getSource()).setStyle("-fx-background-color: null;");
        ((Node) event.getSource()).setOpacity(1);
        ((Node) event.getSource()).getScene().setCursor(Cursor.DEFAULT);
    }

    public void mouseEnterOnButton(MouseEvent event){
        ((Node) event.getSource()).getScene().setCursor(Cursor.HAND);
    }

    public void mouseExitFromButton(MouseEvent event){
        ((Node) event.getSource()).getScene().setCursor(Cursor.DEFAULT);
    }


}
