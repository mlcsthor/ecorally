package fr.lionware.ecorally.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.IOException;

public class RootLayout extends Controller {

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    @FXML
    private Button startButton;

    public RootLayout() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        ImageView playImage = new ImageView(new Image(getClass().getResourceAsStream("../views/assets/play.png")));
        playImage.setFitWidth(200);
        playImage.setFitHeight(80);
        startButton.setGraphic(playImage);
    }

    public void openGameMenu() throws IOException {
        mainApp.switchToPane("MainMenu");
        //mainApp.getPrimaryStage().setFullScreen(true);
    }

    @Override
    public void configure() {

    }
}
