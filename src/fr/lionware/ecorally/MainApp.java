package fr.lionware.ecorally;

import java.io.IOException;

import fr.lionware.ecorally.controllers.Controller;
import fr.lionware.ecorally.controllers.RootLayout;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.naming.ldap.Control;


public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage primaryStage;
    private AnchorPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Eco Rally");

        setNewScene("RootLayout");
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Initializes the root layout.
     */
    public void setNewScene(String sceneName) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/" + sceneName + ".fxml"));
            rootLayout = loader.load();
            // Give the controller access to the main app.
            Controller controller = loader.getController();
            controller.setMainApp(this);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
