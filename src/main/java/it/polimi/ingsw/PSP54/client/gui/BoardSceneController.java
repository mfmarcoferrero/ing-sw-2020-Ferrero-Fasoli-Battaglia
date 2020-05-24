package it.polimi.ingsw.PSP54.client.gui;

import javafx.fxml.FXML;

public class BoardSceneController {

    private GuiManager guiManager;

    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setBoardSceneController(this);
    }

}
