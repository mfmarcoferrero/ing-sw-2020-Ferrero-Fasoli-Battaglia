package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.server.model.Box;
import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.server.model.Worker;
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

import java.util.Vector;

public class BoardSceneController {


    private GuiManager guiManager;
    private ImageView[][] imageBoxes = new ImageView[5][5];
    private ImageView[][] imageWorkerMap = new ImageView[5][5];
    @FXML ImageView cardImage_1;
    @FXML ImageView cardImage_2;
    @FXML ImageView cardImage_3;
    @FXML Label labelPlayer_1;
    @FXML Label labelPlayer_2;
    @FXML Label labelPlayer_3;
    @FXML Label messageLabel;
    @FXML GridPane gridPane;
    @FXML Label panelMessageLabel;
    @FXML Button maleButton;
    @FXML Button femaleButton;


    /**
     * Called when board.fxml is load
     */
    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setBoardSceneController(this);
    }

    public void setBoardScene(){
        int i,j;
        for(Node node: gridPane.getChildren()){
            if(node instanceof ImageView) {
                imageBoxes[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = (ImageView) node;
            }
            if (node instanceof AnchorPane){
                imageWorkerMap[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = (ImageView)((AnchorPane) node).getChildren().get(0);
            }
        }
        setImageCards(guiManager.getCardValues());
        setLabelNames(guiManager.getNames());

    }

    public void setBoardImage(Box[][] board){
        for (int i = 0; i < Game.BOARD_SIZE; i++){
            for (int j = 0; j < Game.BOARD_SIZE; j++){
                if (board[i][j].getLevel() != 0 || board[i][j].isDome())
                    setBuildingImage(imageBoxes[i][j],board[i][j]);
                if (board[i][j].getWorker() != null){
                    setWorkerImage(imageWorkerMap[i][j], board[i][j]);
                }
            }
        }
    }

    private void setBuildingImage(ImageView boxImageView, Box box){
        switch (box.getLevel()) {
            case 0:
                if (box.isDome()) {
                    boxImageView.setImage(new Image("file:./resources/icons/Dome.png"));
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
                    boxImageView.setImage(new Image("file:./resources/icons/Second+Dome_Building.png"));
                }
                if (!box.isDome()){
                    boxImageView.setImage(new Image("file:./resources/icons/First+Second_Building.png"));
                }
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
                    boxImageView.setImage(new Image("file:./resources/icons/MaleWorker_Blue.png"));
                    break;
                case "red":
                    boxImageView.setImage(new Image("file:./resources/icons/MaleWorker_Red.png"));
                    break;
                case "yellow":
                    boxImageView.setImage(new Image("file:./resources/icons/MaleWorker_Yellow.png"));
                    break;
            }
        }
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
    }

    public void showPanelMessage(){
        panelMessageLabel.setVisible(true);
        maleButton.setDisable(false);
        maleButton.setVisible(true);
        femaleButton.setDisable(false);
        femaleButton.setVisible(true);
    }

    public void maleButtonClicked(ActionEvent event){
        guiManager.sendObject(new WorkerChoice(true));
        panelMessageLabel.setVisible(false);
        maleButton.setVisible(false);
        femaleButton.setVisible(false);
    }

    public void femaleButtonClicked(ActionEvent event){
        guiManager.sendObject(new WorkerChoice(false));
        panelMessageLabel.setVisible(false);
        maleButton.setVisible(false);
        femaleButton.setVisible(false);
    }

    public void boxClicked(MouseEvent event){
        Node source = (Node)event.getSource();
        //System.out.printf("Mouse clicked cell in [%d, %d]\n", GridPane.getRowIndex(source), GridPane.getColumnIndex(source));
        if (guiManager.isBoxChoice()){
            guiManager.sendObject(new MoveChoice(GridPane.getRowIndex(source),GridPane.getColumnIndex(source)));
            guiManager.setBoxChoice(false);
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


}
