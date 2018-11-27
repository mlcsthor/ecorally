package fr.lionware.ecorally.Car.Components;

public class Engine extends Component {
    private double power;

    public Engine(String _name, int _price, int _weight, double _power) {
        super(_name, _price, _weight);

        power = _power;
    }

    public double getCoefficient() {
        return 0;
    }
}
