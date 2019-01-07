package fr.lionware.ecorally.models.Car.Components;

import fr.lionware.ecorally.utils.Rarity;

import java.io.Serializable;

public class Engine extends Component {
    private static double DEFAULT_POWER = 0;

    private double power;

    public Engine(String _name, int _price, int _weight, double _power, Rarity _rarity) {
        super(_name, _price, _weight, _rarity);

        power = _power;
    }

    public Engine() {
        super();

        power = DEFAULT_POWER;
    }

    public double getCoefficient() {
        return 0;
    }

    public double getPower() {
        return power;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "power=" + power +
                "} " + super.toString();
    }
}
