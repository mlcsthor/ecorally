package fr.lionware.ecorally;

import java.io.IOException;
import java.util.*;

import fr.lionware.ecorally.controllers.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class MainApp extends Application {
    private Stage primaryStage;
    private Map<String, Pane> panes;

    @Override
    public void start(Stage primaryStage) {
        this.panes = new HashMap<>();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Eco Rally");

        switchToPane("RootLayout");
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Switch to another pane
     * @param paneName The pane name
     */
    public void switchToPane(String paneName) {
        try {
            Pane pane;

            if (panes.containsKey(paneName)) {
                pane = panes.get(paneName);
            } else {
                // Load root layout from fxml file.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("views/" + paneName + ".fxml"));
                pane = loader.load();

                Controller controller = loader.getController();
                controller.setMainApp(this);

                panes.put(paneName, pane);
            }

            if (primaryStage.getScene() == null) {
                Scene scene = new Scene(pane);
                primaryStage.setScene(scene);
            } else {
                primaryStage.getScene().setRoot(pane);
            }

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
