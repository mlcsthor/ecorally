package fr.lionware.ecorally.controllers;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;

import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;


public class PlaygroundLayout extends Controller{

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView carImage;

    @FXML
    private Group car;

    @FXML
    private ImageView frontWheel;
    @FXML
    private ImageView backWheel;

    @FXML
    private Rectangle ground;

    double speed = 20;
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PlaygroundLayout() {
        System.out.println("Playground");
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        System.out.println("Init");
    }

    public void moveForward() {
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(car.layoutXProperty(), car.getLayoutX() + speed);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);

        final KeyValue kv2 = new KeyValue(frontWheel.rotateProperty(), frontWheel.getRotate() + 90);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(500), kv2);
        timeline.getKeyFrames().add(kf2);

        final KeyValue kv3 = new KeyValue(backWheel.rotateProperty(), backWheel.getRotate() + 90);
        final KeyFrame kf3 = new KeyFrame(Duration.millis(500), kv3);
        timeline.getKeyFrames().add(kf3);

        timeline.play();
    }

    public void moveBackward() {
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(car.layoutXProperty(), car.getLayoutX() - speed);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);

        final KeyValue kv2 = new KeyValue(frontWheel.rotateProperty(), frontWheel.getRotate() - speed);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(500), kv2);
        timeline.getKeyFrames().add(kf2);

        final KeyValue kv3 = new KeyValue(backWheel.rotateProperty(), backWheel.getRotate() - speed);
        final KeyFrame kf3 = new KeyFrame(Duration.millis(500), kv3);
        timeline.getKeyFrames().add(kf3);

        timeline.play();
    }

    @FXML  // <== perhaps you had this missing??
    void keyPressed(KeyEvent event) {
        if(speed < 200){
            speed = speed + speed * 0.5;
        }
        switch (event.getCode()) {
            case LEFT:
                moveBackward();
                break;
            case RIGHT:
                moveForward();
                break;
            default:
                break;
        }
    }

    @FXML  // <== perhaps you had this missing??
    void keyReleased(KeyEvent event) {
        speed = 20;
        switch (event.getCode()) {
            case RIGHT:
                break;
            default:
                break;
        }
    }
}
