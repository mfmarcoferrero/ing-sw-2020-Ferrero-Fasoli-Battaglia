package it.polimi.ingsw.PSP54.client.gui;

import it.polimi.ingsw.PSP54.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class GuiMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:./resources/start_game.fxml"));
        Parent logInRoot = loader.<Parent>load();
        Scene logIn = new Scene(logInRoot);
        primaryStage.setTitle("Santorini");
        primaryStage.setScene(logIn);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
