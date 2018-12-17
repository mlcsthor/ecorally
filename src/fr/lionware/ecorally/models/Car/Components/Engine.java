package fr.lionware.ecorally.models.Car.Components;

import java.io.Serializable;

public class Engine extends Component implements Serializable {
    private double power;

    public Engine(String _name, int _price, int _weight, double _power) {
        super(_name, _price, _weight);

        power = _power;
    }

    public double getCoefficient() {
        return 0;
    }

    @Override
    public String getCharacteristics() {
        return "Puissance :" + power;
    }

    public double getPower() {
        return power;
    }
}
