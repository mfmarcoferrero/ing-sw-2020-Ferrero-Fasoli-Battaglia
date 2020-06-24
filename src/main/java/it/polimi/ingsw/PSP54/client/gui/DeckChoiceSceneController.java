package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.server.model.Game;
import it.polimi.ingsw.PSP54.utils.choices.ExtractedCardsChoice;
import javafx.fxml.FXML;
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
    private MouseEvent event;
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

    public void setDeckChoiceScene(){
        if (guiManager.getNumberOfPlayers() == 2) {
            choiceLabel.setText("CHOOSE 2 POWER CARDS");
            choiceLabel.setFont(Font.font("papyrus",30));
        }
        if (guiManager.getNumberOfPlayers() == 3){
            choiceLabel.setText("CHOOSE 3 POWER CARDS");
            choiceLabel.setFont(Font.font("papyrus",30));
        }
    }

    public void setHandCursor(MouseEvent event){
        ((Node)event.getSource()).getScene().setCursor(Cursor.HAND);
    }
    public void setDefaultCursor(MouseEvent event){
        ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
    }
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
    public void apolloImageClicked(MouseEvent event){
        this.event = event;
        extractedCards.put(Game.APOLLO, "Apollo");
        cardImageClicked(apolloImageView);
    }
    public void artemisImageClicked(MouseEvent event){
        this.event = event;
        extractedCards.put(Game.ARTEMIS, "Artemis");
        cardImageClicked(artemisImageView);
    }
    public void athenaImageClicked(MouseEvent event){
        this.event = event;
        extractedCards.put(Game.ATHENA, "Athena");
        cardImageClicked(athenaImageView);
    }
    public void atlasImageClicked(MouseEvent event){
        this.event = event;
        extractedCards.put(Game.ATLAS, "Atlas");
        cardImageClicked(atlasImageView);
    }
    public void demeterImageClicked(MouseEvent event){
        this.event = event;
        extractedCards.put(Game.DEMETER, "Demeter");
        cardImageClicked(demeterImageView);
    }
    public void hephaestusImageClicked(MouseEvent event){
        this.event = event;
        extractedCards.put(Game.HEPHAESTUS, "Hephaestus");
        cardImageClicked(hephaestusImageView);
    }
    public void minotaurImageClicked(MouseEvent event){
        this.event = event;
        extractedCards.put(Game.MINOTAUR, "Minotaur");
        cardImageClicked(minotaurImageView);
    }
    public void panImageClicked(MouseEvent event){
        this.event = event;
        extractedCards.put(Game.PAN, "Pan");
        cardImageClicked(panImageView);
    }
    public void prometheusImageClicked(MouseEvent event){
        this.event = event;
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
