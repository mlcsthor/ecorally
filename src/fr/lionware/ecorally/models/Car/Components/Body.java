package fr.lionware.ecorally.models.Car.Components;

import fr.lionware.ecorally.utils.Rarity;

public class Body extends Component {
    private double aerodynamism;

    public Body(String _name, int _price, int _weight, double _aerodynamism, Rarity _rarity) {
        super(_name, _price, _weight, _rarity);

        aerodynamism = _aerodynamism;
    }

    public Body() {
        super();
    }

    @Override
    public double getCoefficient() {
        return 0;
    }

    public double getAerodynamism() { return aerodynamism; }
}
