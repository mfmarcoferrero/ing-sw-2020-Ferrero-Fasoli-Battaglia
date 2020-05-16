package it.polimi.ingsw.PSP54.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LogInController {

    public void startButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:./resources/start_game.fxml"));
        Parent startGameRoot = loader.<Parent>load();
        Scene startGameScene = new Scene(startGameRoot);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(startGameScene);
        window.show();
        //System.out.println("ciao");
    }

}
