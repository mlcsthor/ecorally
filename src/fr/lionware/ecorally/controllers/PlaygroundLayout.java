package fr.lionware.ecorally.controllers;

import fr.lionware.ecorally.MainApp;
import fr.lionware.ecorally.models.Car.Car;
import fr.lionware.ecorally.models.Ground;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.BodyType;
import javafx.event.ActionEvent;

import java.awt.*;
import java.io.IOException;
import java.util.Date;

import static javafx.scene.input.KeyCode.*;

public class PlaygroundLayout extends Controller {
    public static final Color whiteColor = new Color(1,1,1, 0.5);
    public static World world;

    //Screen width and height
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int WORLD_SIZE = 12000;
    //Ball radius in pixel
    public static final int BALL_RADIUS = 10;
    public static double[][] coordinatesX = new double[2][503];
    public static double[][] coordinatesY = new double[2][503];

    public PlaygroundLayout(){
        world = new World(new Vec2(0.0f, -10.0f));
    }
    //This method adds a ground to the screen.
    public static void addGround() {

        Vec2[] vectors = new Vec2[1001];
        double[][] coordinates = new double[1001][2];

        coordinates[0][0] = 0.;
        coordinates[0][1] = HEIGHT / 2.0;
        int rand;
        for (int i = 1; i < 1001; i++) {
            coordinates[i][0] = i * 12;
            if (i < 40 || i > 900) {
                do {
                    rand = (int) coordinates[i - 1][1] - 2 + (int) (Math.random() * (4 + 1));
                } while (rand > 450 || rand < 200);
                coordinates[i][1] = 400;
            } else {
                do {
                    rand = (int) coordinates[i - 1][1] - 10 + (int) (Math.random() * (20 + 1));
                } while (rand > 450 || rand < 200);
                if (i > 870) {
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
        fd.filter.categoryBits = 0x0004;
        fd.filter.maskBits = -1;

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
        fd.filter.categoryBits = 0x0004;

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

    //Convert a pixel width to JBox2D width
    public static float toJbox2dWidth(float width) {
        return width / WIDTH * 100.f;
    }

    //Convert a JBox2D height to pixel height
    public static float toPixelHeight(float height) {
        return HEIGHT * height / 100.0f;
    }

    //Convert a pixel height to JBox2D height
    public static float toJbox2dHeight(float height) {
        return height / HEIGHT * 100.f;
    }

    @Override
    public void configure() {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().addAll(this.getClass().getResource("../views/style.css").toExternalForm());
        scene.setFill(new ImagePattern(new Image(getClass().getResource("../views/assets/background1.png").toString())));

        StackPane pane = new StackPane();
        pane.setPrefWidth(WIDTH);
        pane.setPrefHeight(HEIGHT);
        pane.setBackground(new Background(new BackgroundFill(whiteColor, CornerRadii.EMPTY, Insets.EMPTY)));

        PerspectiveCamera camera = new PerspectiveCamera();
        scene.setCamera(camera);

        Car car = new Car(240, 300, 85, 30,
                BALL_RADIUS, Color.BLACK);

        addGround();
        addWall(0,100,1,100); //Left wall
        addWall(toPosX(WORLD_SIZE),100,1,100); //Right wall

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        Duration duration = Duration.seconds(1.0/60.0); // Set duration for frame.

        Date timer;

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

        Canvas canvas = new javafx.scene.canvas.Canvas(6000, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawGround(gc, 0);
        root.getChildren().add(canvas);

        Canvas canvas2 = new Canvas(6000, 800);
        GraphicsContext gc2 = canvas2.getGraphicsContext2D();
        canvas2.setLayoutX(5999.5);
        drawGround(gc2, 1);
        root.getChildren().add(canvas2);

        //Create button to start simulation.
        final Button startBtn = new Button();
        final Button resumeBtn = new Button();
        final Button quitBtn = new Button();
        final Button replayBtn = new Button();
        final Text timeTxt = new Text();

        ImageView startImage = new ImageView(new Image(getClass().getResourceAsStream("../views/assets/start.png")));
        ImageView resumeImage = new ImageView(new Image(getClass().getResourceAsStream("../views/assets/resume.png")));
        ImageView quitImage = new ImageView(new Image(getClass().getResourceAsStream("../views/assets/quit.png")));
        ImageView replayImage = new ImageView(new Image(getClass().getResourceAsStream("../views/assets/replay.png")));


        startImage.setPreserveRatio(false);
        startImage.setFitWidth(200);
        startImage.setFitHeight(80);
        startBtn.setGraphic(startImage);
        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                timeline.play();
                resumeBtn.setVisible(true);
                startBtn.setVisible(false);
                quitBtn.setVisible(false);
                pane.setBackground(null);
            }
        });

        resumeImage.setPreserveRatio(false);
        resumeImage.setFitWidth(100);
        resumeImage.setFitHeight(40);
        resumeBtn.setGraphic(resumeImage);
        resumeBtn.setVisible(false);
        resumeBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Create the Effect
                timeline.stop();
                resumeBtn.setVisible(false);
                startBtn.setVisible(true);
                quitBtn.setVisible(true);
                pane.setBackground(new Background(new BackgroundFill(whiteColor, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });

        quitImage.setPreserveRatio(false);
        quitImage.setFitWidth(200);
        quitImage.setFitHeight(80);
        quitBtn.setVisible(false);
        quitBtn.setGraphic(quitImage);
        quitBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                mainApp.getPrimaryStage().close();
                Platform.runLater( () -> new MainApp().start(
                        new Stage(), 1)
                );
            }
        });

        replayImage.setPreserveRatio(false);
        replayImage.setFitWidth(200);
        replayImage.setFitHeight(80);
        replayBtn.setVisible(false);
        replayBtn.setGraphic(replayImage);
        replayBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                mainApp.getPrimaryStage().close();
                Platform.runLater( () -> new MainApp().start(
                        new Stage(), 2)
                );
            }
        });


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
                    pane.setLayoutX(camera.getLayoutX());
                }
                if(xposR >= 11028){
                    timeline.stop();
                    pane.setBackground(new Background(new BackgroundFill(whiteColor, CornerRadii.EMPTY, Insets.EMPTY)));
                    quitBtn.setVisible(true);
                    replayBtn.setVisible(true);
                    resumeBtn.setVisible(false);
                }
            }
        };

        KeyFrame frame = new KeyFrame(duration, ae, null,null);
        timeline.getKeyFrames().add(frame);

        root.getChildren().add(car.bodywork);
        root.getChildren().add(car.rightWheel);
        root.getChildren().add(car.leftWheel);
        pane.getChildren().add(resumeBtn);
        pane.getChildren().add(startBtn);
        pane.getChildren().add(quitBtn);
        pane.getChildren().add(replayBtn);
        root.getChildren().add(pane);
        mainApp.getPrimaryStage().setScene(scene);
        mainApp.getPrimaryStage().show();

        startBtn.setTranslateY(-50);
        replayBtn.setTranslateY(-50);
        quitBtn.setTranslateY(50);
        resumeBtn.setTranslateX(500);
        resumeBtn.setTranslateY(-350);
        timeTxt.setTranslateX(-500);
        timeTxt.setTranslateY(-350);
    }

    private void drawGround(GraphicsContext gc, int n) {
        //n = numéro du canvas (0 ou 1)
        if(n == 1){
            gc.setFill(new ImagePattern(new Image(getClass().getResource("../views/assets/flag.png").toString())));
            gc.fillRect(5028,  coordinatesY[n][419]-80, 40, 80);
        }
        gc.setFill(Color.rgb(90, 70, 85));
        gc.fillPolygon(coordinatesX[n], coordinatesY[n], 503);
        gc.setStroke(Color.rgb(142, 210, 211, 1));
        gc.setLineWidth(2);
        gc.strokePolyline(coordinatesX[n], coordinatesY[n], 501);
    }
}