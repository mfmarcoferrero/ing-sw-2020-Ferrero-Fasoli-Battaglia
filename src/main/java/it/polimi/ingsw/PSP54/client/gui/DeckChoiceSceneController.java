package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.utils.choices.ExtractedCardsChoice;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.util.HashMap;

public class DeckChoiceSceneController {

    private GuiManager guiManager;
    private int cardSelected = 0;
    private HashMap<Integer,String> extractedCards = new HashMap<>();
    @FXML private Label choiceLabel;
    @FXML private ImageView apolloImageView;
    @FXML private ImageView artemisImageView;
    @FXML private ImageView athenaImageView;
    @FXML private ImageView atlasImageView;
    @FXML private ImageView demeterImageView;
    @FXML private ImageView hephaestusImageView;
    @FXML private ImageView panImageView;
    @FXML private ImageView prometheusImageView;
    @FXML private ImageView minotaurImageView;


    /**
     * Called when deck_choice.fxml is load
     */
    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setDeckChoiceSceneController(this);
    }

    /**
     * Set main label text changing on the number of players
     */
    public void setDeckChoiceScene(){
        guiManager.setCardExtractor(true);
        if (guiManager.getNumberOfPlayers() == 2) {
            choiceLabel.setText("CHOOSE 2 POWER CARDS");
            choiceLabel.setFont(Font.font("papyrus",35));
        }
        if (guiManager.getNumberOfPlayers() == 3){
            choiceLabel.setText("CHOOSE 3 POWER CARDS");
            choiceLabel.setFont(Font.font("papyrus",35));
        }
        choiceLabel.setAlignment(Pos.CENTER);
    }

    public void setHandCursor(MouseEvent event){
        ((Node)event.getSource()).getScene().setCursor(Cursor.HAND);
    }
    public void setDefaultCursor(MouseEvent event){
        ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
    }

    /**
     * When the number of cards selected is equal to the number of players,
     * every imageView is set disable and a new ExctractedCardsChoice instance sent to server
     * @param imageViewClicked
     */
    public void cardImageClicked(ImageView imageViewClicked) {
        cardSelected++;
        imageViewClicked.setOpacity(0.5);
        if (cardSelected == guiManager.getNumberOfPlayers()){
            apolloImageView.setDisable(true);
            artemisImageView.setDisable(true);
            athenaImageView.setDisable(true);
            atlasImageView.setDisable(true);
            demeterImageView.setDisable(true);
            hephaestusImageView.setDisable(true);
            minotaurImageView.setDisable(true);
            panImageView.setDisable(true);
            prometheusImageView.setDisable(true);
            guiManager.sendObject(new ExtractedCardsChoice(extractedCards));
        }
    }
    public void apolloImageClicked(){
        extractedCards.put(Game.APOLLO, "Apollo");
        cardImageClicked(apolloImageView);
    }
    public void artemisImageClicked(){
        extractedCards.put(Game.ARTEMIS, "Artemis");
        cardImageClicked(artemisImageView);
    }
    public void athenaImageClicked(){
        extractedCards.put(Game.ATHENA, "Athena");
        cardImageClicked(athenaImageView);
    }
    public void atlasImageClicked(){
        extractedCards.put(Game.ATLAS, "Atlas");
        cardImageClicked(atlasImageView);
    }
    public void demeterImageClicked(){
        extractedCards.put(Game.DEMETER, "Demeter");
        cardImageClicked(demeterImageView);
    }
    public void hephaestusImageClicked(){
        extractedCards.put(Game.HEPHAESTUS, "Hephaestus");
        cardImageClicked(hephaestusImageView);
    }
    public void minotaurImageClicked(){
        extractedCards.put(Game.MINOTAUR, "Minotaur");
        cardImageClicked(minotaurImageView);
    }
    public void panImageClicked(){
        extractedCards.put(Game.PAN, "Pan");
        cardImageClicked(panImageView);
    }
    public void prometheusImageClicked(){
        extractedCards.put(Game.PROMETHEUS, "Prometheus");
        cardImageClicked(prometheusImageView);
    }

    public void setFirstPlayerChoiceScene(){
        FirstPlayerChoiceSceneController firstPlayerChoiceSceneController = GuiManager.setLayout(guiManager.getStage().getScene(),"FXML/first_player_choice.fxml");
        if (firstPlayerChoiceSceneController != null){
            firstPlayerChoiceSceneController.setFont();
        }
    }
}
