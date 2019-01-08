package fr.lionware.ecorally.models.Car.Components;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lionware.ecorally.utils.Rarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    public static Component[] generateTabFromList(List<Component> list) {
        Component[] itemsTab = new Component[list.size()];

        for (int i = 0; i < list.size(); i++) {
            itemsTab[i] = list.get(i);
        }

        return itemsTab;
    }

    public static Component[] readFromFile(String fileName) {
        Component[] items = new Component[0];

        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();

        try {
            items = mapper.readerFor(Component[].class).readValue(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }
}
