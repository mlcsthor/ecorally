package fr.lionware.ecorally.controllers;

import fr.lionware.ecorally.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;

public class RootLayout {

    // Reference to the main application.
    private MainApp mainApp;

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

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void openGameMenu(){

    }
}
