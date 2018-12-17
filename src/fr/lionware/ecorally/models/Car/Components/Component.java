package fr.lionware.ecorally.models.Car.Components;

import java.io.*;
import java.util.List;

public abstract class Component implements Serializable {
    private static int DEFAULT_PRICE = 0;
    private static int DEFAULT_WEIGHT = 0;

    private String name;
    private int price;
    private double weight;

    public Component(String _name, int _price, double _weight) {
        name = _name;
        price = _price;
        weight = _weight;
    }

    public Component() {
        this("Component", DEFAULT_PRICE, DEFAULT_WEIGHT);
    }

    public double getWeight() {
        return weight;
    }

    public abstract double getCoefficient();

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public abstract String getCharacteristics();

    public static List<Component> loadComponents() {
        //return (List<Component>) IO.readFromFile("res/components.ser");
        return null;
    }
}
