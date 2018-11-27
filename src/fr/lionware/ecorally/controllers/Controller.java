package fr.lionware.ecorally.controllers;

import fr.lionware.ecorally.MainApp;

public abstract class Controller {
    // Reference to the main application.
    protected MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
