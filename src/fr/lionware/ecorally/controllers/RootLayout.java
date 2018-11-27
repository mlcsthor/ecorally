package fr.lionware.ecorally.controllers;

import fr.lionware.ecorally.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;

public class RootLayout extends Controller {

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public RootLayout() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    public void openGameMenu() throws IOException {
        mainApp.setNewScene("MainMenu");
        mainApp.getPrimaryStage().setFullScreen(true);
    }
}
