package fr.lionware.ecorally.models.Car.Components;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import fr.lionware.ecorally.utils.Rarity;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "@class")
public abstract class Component {
    private static int DEFAULT_PRICE = 0;
    private static int DEFAULT_WEIGHT = 0;
    private static Rarity DEFAULT_RARITY = Rarity.COMMON;

    private String name;
    private int price;
    private double weight;
    private Rarity rarity;

    public Component(String _name, int _price, double _weight, Rarity _rarity) {
        name = _name;
        price = _price;
        weight = _weight;
        rarity = _rarity;
    }

    public Component() {
        this("Component", DEFAULT_PRICE, DEFAULT_WEIGHT, DEFAULT_RARITY);
    }

    public double getWeight() {
        return weight;
    }

    @JsonIgnore
    public abstract double getCoefficient();

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Rarity getRarity() { return rarity; }

    @Override
    public String toString() {
        return "Component{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", rarity=" + rarity +
                '}';
    }
}
