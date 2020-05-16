package it.polimi.ingsw.PSP54.client.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class StartGameController {

    public ImageView play_button;
    private boolean play_button_pressed = false;

    Image startButtonReleased = new Image("file:./resources/icons/button-play-normal.png");
    Image startButtonPressed = new Image("file:./resources/icons/button-play-down.png");

    public void startButtonPressed(MouseEvent event) throws IOException {
        play_button.setImage(startButtonPressed);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:./resources/log_in.fxml"));
        Parent logInRoot = loader.<Parent>load();
        Scene logInScene = new Scene(logInRoot);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(logInScene);
        window.show();
    }

    public void startButtonReleased(){
        play_button.setImage(startButtonReleased);
    }

}
