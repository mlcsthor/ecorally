package fr.lionware.ecorally.controllers;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Circle;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.util.Duration;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.BodyType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.awt.*;
import java.io.IOException;

import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;

public class PlaygroundLayout extends Controller{

    private World world;
    private Node node;

    @FXML
    private Group car;

    @FXML
    private ImageView frontWheel;

    @FXML
    private ImageView backWheel;


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
        // Static Body
        Vec2  gravity = new Vec2(0.0f, -10.0f);
        world = new World(gravity);

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(600,10);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;

        BodyDef bd = new BodyDef();
        bd.position= new Vec2(0.0f,10f);

        world.createBody(bd).createFixture(fd);

        node = create();
    }

    //This method adds a ground to the screen.
    public void addGround(float width, float height){
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width,height);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;

        BodyDef bd = new BodyDef();
        bd.position= new Vec2(0.0f,0);


        world.createBody(bd).createFixture(fd);
    }

    private Node create(){
        //Create an UI for ball - JavaFX code
        Circle ball = new Circle();
        ball.setRadius(20);
        LinearGradient gradient = new LinearGradient(0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0, Color.WHITE), new Stop(1, Color.RED)});
        ball.setFill(gradient); //set look and feel

        /**
         * Set ball position on JavaFX scene. We need to convert JBox2D coordinates
         * to JavaFX coordinates which are in pixels.
         */
        ball.setLayoutX(toPixelPosX(50));
        ball.setLayoutY(toPixelPosY(50));

        ball.setCache(true); //Cache this object for better performance

        //Create an JBox2D body defination for ball.
        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(50, 50);
        CircleShape cs = new CircleShape();
        cs.m_radius = 10 * 0.1f;  //We need to convert radius to JBox2D equivalent

        // Create a fixture for ball
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 0.9f;
        fd.friction = 0.3f;
        fd.restitution = 0.6f;

        /**
         * Virtual invisible JBox2D body of ball. Bodies have velocity and position.
         * Forces, torques, and impulses can be applied to these bodies.
         */

        Body body = world.createBody(bd);
        body.createFixture(fd);
        ball.setUserData(body);
        return ball;
    }

    public void configure(){

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        Duration duration = Duration.seconds(1.0/60.0); // Set duration for frame.

        //Create an ActionEvent, on trigger it executes a world time step and moves the balls to new position
        EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //Create time step. Set Iteration count 8 for velocity and 3 for positions
                world.step(1.0f / 60.f, 8, 3);

                //Move balls to the new position computed by JBox2D

                Body body = (Body) node.getUserData();
                //float xpos = toPixelPosX(body.getPosition().x);
                float ypos = toPixelPosY(body.getPosition().y);
                //node.setLayoutX(xpos);
                node.setLayoutY(ypos);
            }
        };
        /**
         * Set ActionEvent and duration to the KeyFrame.
         * The ActionEvent is trigged when KeyFrame execution is over.
         */
        KeyFrame frame = new KeyFrame(duration, ae, null,null);
        timeline.getKeyFrames().add(frame);
        mainApp.currentPane.getChildren().add(node);
        timeline.playFromStart();
    }


    //Convert a JBox2D x coordinate to a JavaFX pixel x coordinate
    public static float toPixelPosX(float posX) {
        float x = 600*posX / 100.0f;
        return x;
    }


    //Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
    public static float toPixelPosY(float posY) {
        float y = 400 - (1.0f*400) * posY / 100.0f;
        return y;
    }

    @FXML
    void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                moveBackward();
                break;
            case RIGHT:
                moveForward();
                break;
            case UP:
                jump();
                break;
            default:
                break;
        }
    }

    public void moveForward(){
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);
        final KeyValue kv= new KeyValue(node.layoutXProperty(), node.getLayoutX() + 10);
        final KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public void moveBackward(){
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);
        final KeyValue kv= new KeyValue(node.layoutXProperty(), node.getLayoutX() - 10);
        final KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public  void jump(){
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(node.layoutYProperty(), node.getLayoutY() - 30);
        final KeyFrame kf = new KeyFrame(Duration.millis(200), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
}
