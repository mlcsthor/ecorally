package fr.lionware.ecorally.models.Car;

import fr.lionware.ecorally.models.Car.Components.Battery;
import fr.lionware.ecorally.models.Car.Components.Component;
import fr.lionware.ecorally.models.Car.Components.Engine;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private static int DEFAULT_VALUE = 0;

    private List<Component> components;
    private double coefficient;
    private double power;
    private double capacity;
    private double weight;

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
}
