package fr.lionware.ecorally.models.Car.Components;

public abstract class Component {
    private static int DEFAULT_PRICE = 0;
    private static int DEFAULT_WEIGHT = 0;

    private String name;
    private int price;
    private double weight;

    public Component(String _name, int _price, int _weight) {
        name = _name;
        price = _price;
        weight = _weight;
    }

    public Component() {
        this("Component", DEFAULT_PRICE, DEFAULT_WEIGHT);
    }

    public abstract double getCoefficient();
}
