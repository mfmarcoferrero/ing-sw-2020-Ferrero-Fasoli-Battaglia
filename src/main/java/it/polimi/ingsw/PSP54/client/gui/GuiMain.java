package it.polimi.ingsw.PSP54.client.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GuiMain extends Application implements Runnable{

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(new Pane(),800,450));
        GuiManager.setLayout(stage.getScene(),"file:./resources/FXML/log_in.fxml");
        stage.show();
    }

    @Override
    public void run() {
        launch();
    }
}


