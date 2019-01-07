package fr.lionware.ecorally.models.Car.Components;

import fr.lionware.ecorally.utils.Rarity;

public class Tire extends Component {
    private static int DEFAULT_USURY = 0;

    private int usury;

    public Tire(String _name, int _price, int _weight, Rarity _rarity) {
        super(_name, _price, _weight, _rarity);

        usury = DEFAULT_USURY;
    }

    public Tire() {
        super();

        usury = DEFAULT_USURY;
    }

    public double getCoefficient() {
        return 0;
    }
}
