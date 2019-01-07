package fr.lionware.ecorally.models.Car.Components;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.lionware.ecorally.utils.Rarity;

import java.io.Serializable;

public class Battery extends Component implements Serializable {
    private static double DEFAULT_LEVEL = 100;
    private static double DEFAULT_CAPACITY = 50;

    private double capacity;
    private double level;

    public Battery(String _name, int _price, int _weight, double _capacity, Rarity _rarity) {
        super(_name, _price, _weight, _rarity);

        capacity = _capacity;
        level = DEFAULT_LEVEL;
    }

    public Battery() {
        super();

        capacity = DEFAULT_CAPACITY;
        level = DEFAULT_LEVEL;
    }

    @Override
    public double getCoefficient() {
        return 0;
    }

    public double getCapacity() { return capacity; }

    @JsonIgnore
    public double getCurrentCapacity() {
        return capacity * (level/100);
    }
}
