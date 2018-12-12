package fr.lionware.ecorally;

import java.io.IOException;
import java.util.*;

import fr.lionware.ecorally.controllers.Controller;
import fr.lionware.ecorally.models.Car.Components.Component;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class MainApp extends Application {
    private Stage primaryStage;
    private Map<String, Pane> panes;

    private List<Component> componentList;

    @Override
    public void start(Stage _primaryStage) {
        panes = new HashMap<>();
        primaryStage = _primaryStage;
        primaryStage.setTitle("Eco Rally");

        componentList = Component.loadComponents();

        switchToPane("RootLayout");
    }

    /**
     * Switch to another pane
     * @param paneName The pane name
     */
    public void switchToPane(String paneName) {
        try {
            Pane pane;
            Controller controller = null;

            if (panes.containsKey(paneName)) {
                pane = panes.get(paneName);
            } else {
                // Load root layout from fxml file.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("views/" + paneName + ".fxml"));
                pane = loader.load();

                controller = loader.getController();
                controller.setMainApp(this);

                panes.put(paneName, pane);
            }

            if (primaryStage.getScene() == null) {
                Scene scene = new Scene(pane);
                primaryStage.setScene(scene);
            } else {
                primaryStage.getScene().setRoot(pane);
            }

            if (controller != null) controller.configure();

            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public List<Component> getComponentList() {
        return componentList;
    }
}
