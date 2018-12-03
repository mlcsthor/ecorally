package fr.lionware.ecorally.controllers;

import javafx.fxml.FXML;

import java.io.IOException;

public class MainMenu extends Controller{

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MainMenu() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    public void openPlayground() throws IOException {
        mainApp.switchToPane("PlaygroundLayout");
        mainApp.getPrimaryStage().setMaximized(true);
    }

}
