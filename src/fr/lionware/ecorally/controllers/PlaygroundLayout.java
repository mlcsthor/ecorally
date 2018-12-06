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
import java.io.IOException;

import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;


public class PlaygroundLayout extends Controller{

    @FXML
    private Group car;

    @FXML
    private ImageView frontWheel;

    @FXML
    private ImageView backWheel;

    @FXML
    private Group background;

    @FXML
    private Group ground;

    private double speed = 20;
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PlaygroundLayout() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    public void moveForward() {
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);
        //final KeyValue kv = new KeyValue(car.layoutXProperty(), car.getLayoutX() + speed);
        //final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        //timeline.getKeyFrames().add(kf);

        final KeyValue kv2 = new KeyValue(frontWheel.rotateProperty(), frontWheel.getRotate() + 90);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(500), kv2);
        timeline.getKeyFrames().add(kf2);

        final KeyValue kv3 = new KeyValue(backWheel.rotateProperty(), backWheel.getRotate() + 90);
        final KeyFrame kf3 = new KeyFrame(Duration.millis(500), kv3);
        timeline.getKeyFrames().add(kf3);

        final KeyValue kv4 = new KeyValue(background.layoutXProperty(), background.getLayoutX() - speed);
        final KeyFrame kf4 = new KeyFrame(Duration.millis(500), kv4);
        timeline.getKeyFrames().add(kf4);

        final KeyValue kv5 = new KeyValue(ground.layoutXProperty(), ground.getLayoutX() - speed);
        final KeyFrame kf5 = new KeyFrame(Duration.millis(500), kv5);
        timeline.getKeyFrames().add(kf5);

        timeline.play();
    }


    public void moveBackward() {
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);
        //final KeyValue kv = new KeyValue(car.layoutXProperty(), car.getLayoutX() - speed);
        //final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        //timeline.getKeyFrames().add(kf);

        final KeyValue kv2 = new KeyValue(frontWheel.rotateProperty(), frontWheel.getRotate() - 90);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(500), kv2);
        timeline.getKeyFrames().add(kf2);

        final KeyValue kv3 = new KeyValue(backWheel.rotateProperty(), backWheel.getRotate() - 90);
        final KeyFrame kf3 = new KeyFrame(Duration.millis(500), kv3);
        timeline.getKeyFrames().add(kf3);

        final KeyValue kv4 = new KeyValue(background.layoutXProperty(), background.getLayoutX() + speed);
        final KeyFrame kf4 = new KeyFrame(Duration.millis(500), kv4);
        timeline.getKeyFrames().add(kf4);

        final KeyValue kv5 = new KeyValue(ground.layoutXProperty(), ground.getLayoutX() + speed);
        final KeyFrame kf5 = new KeyFrame(Duration.millis(500), kv5);
        timeline.getKeyFrames().add(kf5);

        timeline.play();
    }

    @FXML
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

    @FXML
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
