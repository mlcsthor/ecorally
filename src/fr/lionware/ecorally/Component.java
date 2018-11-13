package fr.lionware.ecorally;

public abstract class Component {
    private String name;
    private int price;
    private double weight;

    public Component(String _name, double _coefficient) {
    }

    public Component() {
    }

    public abstract void getCoefficient();
}
