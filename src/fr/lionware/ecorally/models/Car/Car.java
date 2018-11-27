package fr.lionware.ecorally.models.Car;

import fr.lionware.ecorally.models.Car.Components.Component;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private List<Component> components;
    private double coefficient;

    public Car() {
        components = new ArrayList<>();
        coefficient = 0;
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
        }

        calculateCoefficient();
    }

    /**
     * Remove a component on the car
     * @param component The component to remove
     */
    public void removeComponent(Component component) {
        components.remove(component);

        calculateCoefficient();
    }

    /**
     * Calculate the total consumption coefficient
     */
    public void calculateCoefficient() {
        coefficient = 0;

        for (Component component: components) {
            coefficient += component.getCoefficient();
        }
    }
}
