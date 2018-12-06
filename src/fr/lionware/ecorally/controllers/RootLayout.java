package fr.lionware.ecorally.controllers;

import javafx.fxml.FXML;

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
        mainApp.switchToPane("MainMenu");
        //mainApp.getPrimaryStage().setFullScreen(true);
    }
}
