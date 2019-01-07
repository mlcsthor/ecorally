package fr.lionware.ecorally.models.Car.Components;

import fr.lionware.ecorally.utils.Rarity;

public class FuelCell extends Component {
    public FuelCell(String _name, int _price, int _weight, Rarity _rarity) {
        super(_name, _price, _weight, _rarity);
    }

    public FuelCell() {
        super();
    }

    public double getCoefficient() {
        return 0;
    }
}
