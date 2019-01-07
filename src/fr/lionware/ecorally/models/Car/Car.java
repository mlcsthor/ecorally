package fr.lionware.ecorally.models.Car;

import fr.lionware.ecorally.controllers.PlaygroundLayout;
import fr.lionware.ecorally.models.Car.Components.Battery;
import fr.lionware.ecorally.models.Car.Components.Component;
import fr.lionware.ecorally.models.Car.Components.Engine;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.DistanceJointDef;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private static int DEFAULT_VALUE = 0;

    private List<Component> components;
    private double coefficient;
    private double power;
    private double capacity;
    private double weight;

    //JavaFX UI for ball
    public Node rightWheel;
    public Node leftWheel;
    public Node bodywork;

    //X and Y position of the ball in JBox2D world
    private float posXJavaFx;
    private float posYJavaFx;
    private float posXJbox2d;
    private float posYJbox2d;

    private float widthJavaFx;
    private float heightJavaFx;
    private float widthJbox2d;
    private float heightJbox2d;

    private Color color;

    private int radius;

    public Car() {
        components = new ArrayList<>();

        coefficient = DEFAULT_VALUE;
        power = DEFAULT_VALUE;
        capacity = DEFAULT_VALUE;
        weight = DEFAULT_VALUE;
    }

    public void moveForward() {
    }

    public void moveBackward() {
    }

    /**
     * Add a component on the car
     * @param component The component to add
     */
    public void addComponent(Component component) {
        if (!components.contains(component)) {
            components.add(component);

            updateCharacteristics(component, 1);
        }
    }

    /**
     * Remove a component on the car
     * @param component The component to remove
     */
    public void removeComponent(Component component) {
        if (components.remove(component)) {
            updateCharacteristics(component, -1);
        }
    }

    /**
     * Update the total stats of the car
     * @param component The component added or removed
     * @param multiplier -1 if component removed, 1 if component added
     */
    private void updateCharacteristics(Component component, int multiplier) {
        if (component instanceof Battery) {
            capacity += ((Battery) component).getCurrentCapacity() * multiplier;
        } else if (component instanceof Engine) {
            power += ((Engine) component).getPower() * multiplier;
        } else {
            coefficient += component.getCoefficient() * multiplier;
        }

        weight += component.getWeight() * multiplier;
    }

    public Car(float posX, float posY, float width, float height, int radius, Color color){
        this.posXJavaFx = posX;
        this.posYJavaFx = posY;
        this.posXJbox2d = PlaygroundLayout.toPosX(posX);
        this.posYJbox2d = PlaygroundLayout.toPosY(posY);
        this.widthJavaFx = width;
        this.heightJavaFx = height;
        this.widthJbox2d = PlaygroundLayout.toJbox2dWidth(width);
        this.heightJbox2d = PlaygroundLayout.toJbox2dHeight(height);
        this.radius = radius;
        this.color = color;
        create();
    }

    /**
     * This method creates a ball by using Circle object from JavaFX and CircleShape from JBox2D
     */
    private void create(){
        ImagePattern wheelImage = new ImagePattern(new Image(getClass().getResource("../../views/assets/wheel.png").toString()));
        Rectangle top = new Rectangle(posXJavaFx, posYJavaFx, widthJavaFx, heightJavaFx);
        top.setFill(Color.YELLOW);

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(widthJbox2d/2,heightJbox2d/2);

        /*
        for (int i = 0; i < ps.m_vertices.length; i++){
            System.out.println(PlaygroundLayout.toPixelPosX(ps.m_vertices[i].x) + " | " + PlaygroundLayout.toPixelPosY(ps.m_vertices[i].y));
        }
        */

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(posXJbox2d,posYJbox2d);


        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = 100f;
        fd.friction = 0.3f;
        fd.restitution = 0f;

        Body bodyT = PlaygroundLayout.world.createBody(bd);
        bodyT.createFixture(fd);
        top.setUserData(bodyT);
        System.out.println(top.getX() + " | " + top.getY());
        bodywork = top;

        //#####################################
        Stop[] stops = new Stop[] { new Stop(0, Color.BLACK), new Stop(1, Color.RED)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);

        Circle right = new Circle(radius);
        right.setLayoutX(posXJavaFx+widthJavaFx*5/6);
        right.setLayoutY(posYJavaFx+heightJavaFx);
        right.setFill(lg1);

        CircleShape cs = new CircleShape();
        cs.m_radius = radius * 0.1f;

        BodyDef bd2 = new BodyDef();
        bd2.type = BodyType.DYNAMIC;
        bd2.position.set(posXJbox2d+widthJbox2d*5/6,posYJbox2d-heightJbox2d);

        FixtureDef fd2 = new FixtureDef();
        fd2.shape = cs;
        fd2.density = 100f;
        fd2.friction = 0.3f;
        fd2.restitution = 0f;

        Body bodyR = PlaygroundLayout.world.createBody(bd2);
        bodyR.createFixture(fd2);
        right.setUserData(bodyR);

        rightWheel = right;
        //##################################

        Circle left = new Circle(radius);
        left.setLayoutX(posXJavaFx+widthJavaFx*1/6);
        left.setLayoutY(posYJavaFx+heightJavaFx);
        left.setFill(Color.BLUE);

        BodyDef bd3 = new BodyDef();
        bd3.type = BodyType.DYNAMIC;
        bd3.position.set(posXJbox2d+widthJbox2d*1/6,posYJbox2d-heightJbox2d);

        Body bodyL = PlaygroundLayout.world.createBody(bd3);
        bodyL.createFixture(fd2);
        left.setUserData(bodyL);

        leftWheel = left;
        //##################################



        DistanceJointDef djd = new DistanceJointDef();
        djd.initialize(bodyL, bodyR, bodyL.getPosition(), bodyR.getPosition());
        djd.length = bodyR.getPosition().x - bodyL.getPosition().x;
        PlaygroundLayout.world.createJoint(djd);
        /*
        RevoluteJointDef rjd1 = new RevoluteJointDef();
        rjd1.bodyA = bodyR;
        rjd1.bodyB = bodyT;
        rjd1.localAnchorA = bodyR.getWorldCenter();
        PlaygroundLayout.world.createJoint(rjd1);
        */
        /*
        RevoluteJointDef rjd2 = new RevoluteJointDef();
        rjd2.bodyA = bodyL;
        rjd2.bodyB = bodyT;
        rjd2.localAnchorA = bodyL.getWorldCenter();
        rjd2.localAnchorB = new Vec2(posXJbox2d+widthJbox2d*1/6,posYJbox2d-heightJbox2d);
        PlaygroundLayout.world.createJoint(rjd2);
        */




    }
}
