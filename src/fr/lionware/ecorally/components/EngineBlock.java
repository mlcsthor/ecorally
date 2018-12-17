package fr.lionware.ecorally.components;

import fr.lionware.ecorally.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class EngineBlock extends AnchorPane {
    @FXML
    public AnchorPane anchorPane;

    @FXML
    public Rectangle rectangle;

    public EngineBlock() {
        anchorPane = new AnchorPane();
        rectangle = new Rectangle();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("components/views/engine.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setBackground(Color color) {
        super.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
