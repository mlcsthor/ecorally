package fr.lionware.ecorally;

import java.io.IOException;
import java.util.*;

import fr.lionware.ecorally.controllers.Controller;
import fr.lionware.ecorally.models.Car.Components.Component;
import fr.lionware.ecorally.models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import javax.naming.ldap.Control;


public class MainApp extends Application {
    private User user;
    private Stage primaryStage;
    public Pane currentPane;
    private Map<String, Pane> panes;

    @Override
    public void start(Stage _primaryStage) {
        user = new User("Invite");

        panes = new HashMap<>();
        primaryStage = _primaryStage;
        primaryStage.setTitle("Eco Rally");

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

                scene.getStylesheets().add(MainApp.class.getResource("views/style.css").toString());
            } else {
                primaryStage.getScene().setRoot(pane);
                currentPane = pane;
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

    /**
     * Get reference to the stage
     * @return The stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Get reference to the user
     * @return The user
     */
    public User getUser() { return user; }
}
