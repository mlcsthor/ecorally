package fr.lionware.ecorally.controllers;

import fr.lionware.ecorally.models.Car.Car;
import fr.lionware.ecorally.models.Ground;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jbox2d.collision.shapes.ChainShape;
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
import org.jbox2d.dynamics.joints.WheelJoint;
import org.jbox2d.dynamics.joints.WheelJointDef;

import java.awt.*;
import java.io.IOException;

import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;

public class PlaygroundLayout extends Controller {

    public static final World world = new World(new Vec2(0.0f, -10.0f));

    //Screen width and height
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int WORLD_SIZE = 12000;
    //Ball radius in pixel
    public static final int BALL_RADIUS = 10;
    public static double[][] coordinatesX = new double[2][503];
    public static double[][] coordinatesY = new double[2][503];

    public PlaygroundLayout(){
    }
    //This method adds a ground to the screen.
    public static void addGround(float width, float height) {

        Vec2[] vectors = new Vec2[1001];
        double[][] coordinates = new double[1001][2];

        coordinates[0][0] = 0.;
        coordinates[0][1] = HEIGHT / 2.0;
        int rand;
        for (int i = 1; i < 1001; i++) {
            coordinates[i][0] = i * 12;
            if (i < 40 || i > 940) {
                do {
                    rand = (int) coordinates[i - 1][1] - 2 + (int) (Math.random() * (4 + 1));
                } while (rand > 550 || rand < 100);
                coordinates[i][1] = rand;
            } else {
                do {
                    rand = (int) coordinates[i - 1][1] - 10 + (int) (Math.random() * (20 + 1));
                } while (rand > 550 || rand < 150);
                if (i > 900) {
                    if (rand > 400) {
                        coordinates[i][1] = (rand - 5);
                    } else {
                        coordinates[i][1] = (rand + 5);
                    }
                } else {
                    coordinates[i][1] = rand;
                }
            }
        }

        for (int i = 0; i < 1001; i++) {
            vectors[i] = new Vec2(toPosX((float) coordinates[i][0]), toPosY((float) coordinates[i][1]));
        }

        for (int j = 0; j < 501; j++) {
            coordinatesX[0][j] = coordinates[j][0];
            coordinatesY[0][j] = coordinates[j][1];
            coordinatesX[1][j] = coordinates[j + 500][0] - 6000;
            coordinatesY[1][j] = coordinates[j + 500][1];
        }

        coordinatesX[0][501] = 6000;
        coordinatesY[0][501] = HEIGHT;
        coordinatesX[0][502] = 0;
        coordinatesY[0][502] = HEIGHT;
        coordinatesX[1][501] = 6000 + 2;
        coordinatesY[1][501] = HEIGHT;
        coordinatesX[1][502] = 0;
        coordinatesY[1][502] = HEIGHT;

        ChainShape cs = new ChainShape();
        cs.createChain(vectors, 1001);

        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 1f;
        fd.friction = 0.3f;
        fd.restitution = 0f;

        BodyDef bd = new BodyDef();
        bd.position = new Vec2(0f, 0f);
        bd.type = BodyType.STATIC;

        Body body = world.createBody(bd);
        body.createFixture(fd);

        Ground ground = new Ground("Test", coordinates);
    }

    //This method creates a walls.
    public static void addWall(float posX, float posY, float width, float height) {
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width, height);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = 1.0f;
        fd.friction = 0.3f;

        BodyDef bd = new BodyDef();
        bd.position.set(posX, posY);

        world.createBody(bd).createFixture(fd);
    }

    //Convert a JBox2D x coordinate to a JavaFX pixel x coordinate
    public static float toPixelPosX(float posX) {
        float x = WIDTH * posX / 100.0f;
        return x;
    }

    //Convert a JavaFX pixel x coordinate to a JBox2D x coordinate
    public static float toPosX(float posX) {
        float x = (posX * 100.0f * 1.0f) / WIDTH;
        return x;
    }

    //Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
    public static float toPixelPosY(float posY) {
        float y = HEIGHT - (1.0f * HEIGHT) * posY / 100.0f;
        return y;
    }

    //Convert a JavaFX pixel y coordinate to a JBox2D y coordinate
    public static float toPosY(float posY) {
        float y = 100.0f - ((posY * 100 * 1.0f) / HEIGHT);
        return y;
    }

    //Convert a JBox2D width to pixel width
    public static float toPixelWidth(float width) {
        return WIDTH * width / 100.0f;
    }

    public static float toJbox2dWidth(float width) {
        return width / WIDTH * 100.f;
    }

    //Convert a JBox2D height to pixel height
    public static float toPixelHeight(float height) {
        return HEIGHT * height / 100.0f;
    }

    public static float toJbox2dHeight(float height) {
        return height / HEIGHT * 100.f;
    }

    @Override
    public void configure() {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        //scene.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("background.jpg").toString())));

        PerspectiveCamera camera = new PerspectiveCamera();
        scene.setCamera(camera);

        //Add ground to the application, this is where cars will land
        addGround(12000, 10);

        Car car = new Car(240, 300, 85, 30,
                BALL_RADIUS, Color.BLACK);

        //Add left and right walls so cars will not move outside the viewing area.
        addWall(0,100,1,100); //Left wall
        addWall(toPosX(WORLD_SIZE),100,1,100); //Right wall

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        Duration duration = Duration.seconds(1.0/60.0); // Set duration for frame.

        //Create an ActionEvent, on trigger it executes a world time step and moves the cars to new position
        EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //Create time step. Set Iteration count 8 for velocity and 3 for positions
                world.step(1.0f/60.f, 8, 3);

                //Move cars to the new position computed by JBox2D
                Body bodyR = (Body)car.rightWheel.getUserData();
                float xposR = toPixelPosX(bodyR.getPosition().x);
                float yposR = toPixelPosY(bodyR.getPosition().y);
                car.rightWheel.setLayoutX(xposR);
                car.rightWheel.setLayoutY(yposR);

                Body bodyL = (Body)car.leftWheel.getUserData();
                float xposL = toPixelPosX(bodyL.getPosition().x);
                float yposL = toPixelPosY(bodyL.getPosition().y);
                car.leftWheel.setLayoutX(xposL);
                car.leftWheel.setLayoutY(yposL);

                Body bodyT = (Body)car.bodywork.getUserData();
                float xposT = toPixelPosX(bodyT.getPosition().x);
                float yposT = toPixelPosY(bodyT.getPosition().y);
                car.bodywork.setLayoutX(xposT-240);
                car.bodywork.setLayoutY(yposT-300);

                if(xposR-toPixelPosX(20) > 0 && xposR-toPixelPosX(20) < WORLD_SIZE - WIDTH){
                    camera.setLayoutX(xposR-toPixelPosX(20));
                }
            }
        };

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Body body = (Body)car.rightWheel.getUserData();
                Vec2 force;
                Vec2 point = body.getWorldPoint(body.getWorldCenter());
                switch (event.getCode()) {
                    case RIGHT:
                        force  = new Vec2(500, 0);
                        body.applyLinearImpulse(force, point);
                        break;
                    case LEFT:
                        force  = new Vec2(-500, 0);
                        body.applyLinearImpulse(force, point);
                        break;
                    case SPACE:
                        force  = new Vec2(0, 500);
                        body.applyLinearImpulse(force, point);
                        break;
                }
            }
        });

        /**
         * Set ActionEvent and duration to the KeyFrame.
         * The ActionEvent is trigged when KeyFrame execution is over.
         */
        KeyFrame frame = new KeyFrame(duration, ae, null,null);
        timeline.getKeyFrames().add(frame);

        javafx.scene.canvas.Canvas canvas = new javafx.scene.canvas.Canvas(6000, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawGround(gc, 0);
        root.getChildren().add(canvas);

        javafx.scene.canvas.Canvas canvas2 = new Canvas(6000, 800);
        GraphicsContext gc2 = canvas2.getGraphicsContext2D();
        canvas2.setLayoutX(5999.5);
        drawGround(gc2, 1);
        root.getChildren().add(canvas2);

        //Create button to start simulation.
        final javafx.scene.control.Button btn = new Button();
        btn.setLayoutX(WIDTH/2-100);
        btn.setLayoutY(HEIGHT/2-50);
        btn.setText("Start");
        btn.setPrefWidth(200);
        btn.setPrefHeight(100);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                timeline.playFromStart();
                btn.setVisible(false);
                canvas.setEffect(null);
                canvas2.setEffect(null);
                car.rightWheel.setEffect(null);
                car.leftWheel.setEffect(null);
                car.bodywork.setEffect(null);
            }
        });

        // Create the Effect
        BoxBlur effect = new BoxBlur();
        effect.setHeight(8);
        effect.setWidth(8);
        effect.setIterations(3);
        //canvas.setEffect(effect);
        //canvas2.setEffect(effect);
        //car.rightWheel.setEffect(effect);
        //car.leftWheel.setEffect(effect);
        //car.bodywork.setEffect(effect);

        root.getChildren().add(btn);
        //root.getChildren().add(car.bodywork);
        root.getChildren().add(car.rightWheel);
        root.getChildren().add(car.leftWheel);
        mainApp.getPrimaryStage().setScene(scene);
        mainApp.getPrimaryStage().show();
    }

    private void drawGround(GraphicsContext gc, int n) {
        if(n == 1){
            //gc.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("flag.png").toString())));
            gc.setFill(Color.YELLOW);
            gc.fillRect(5760,  coordinatesY[n][480]-80, 40, 80);
        }

        gc.setFill(Color.rgb(78, 91, 104));
        gc.fillPolygon(coordinatesX[n],
                coordinatesY[n], 503);
        gc.setStroke(Color.rgb(144, 249, 132, 1));
        gc.setLineWidth(2);
        gc.strokePolyline(coordinatesX[n],
                coordinatesY[n], 501);
    }
}