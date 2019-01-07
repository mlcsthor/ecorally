package fr.lionware.ecorally.controllers;

import fr.lionware.ecorally.MainApp;

import java.io.Serializable;

public abstract class Controller implements Serializable {
    protected MainApp mainApp;

    /**
     * Reference the main app in each controller
     * @param _mainApp JavaFX Application
     */
    public void setMainApp(MainApp _mainApp) {
        mainApp = _mainApp;
    }

    /**
     * Get reference to the main app
     * @return The main app
     */
    public MainApp getMainApp() { return mainApp; }

    /**
     * Make something using mainApp
     * Used because setMainApp() is called after initialize()
     */
    public abstract void configure();
}
