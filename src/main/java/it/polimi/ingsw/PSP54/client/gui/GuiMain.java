package it.polimi.ingsw.PSP54.client.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Run as a thread, it creates the stage for our gui
 */
public class GuiMain extends Application implements Runnable{

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new Pane(),1065,600));
        LogInSceneController logInSceneController = GuiManager.setLayout(stage.getScene(), "FXML/log_in.fxml");
        logInSceneController.setFont();
        logInSceneController.getGuiManager().setStage(stage);
        stage.setTitle("Santorini");
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.show();
    }

    @Override
    public void run() {
        launch();
    }
}


