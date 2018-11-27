package fr.lionware.ecorally.Car.Components;

public class FuelCell extends Component {
    public FuelCell(String _name, int _price, int _weight) {
        super(_name, _price, _weight);
    }

    public double getCoefficient() {
        return 0;
    }
}
