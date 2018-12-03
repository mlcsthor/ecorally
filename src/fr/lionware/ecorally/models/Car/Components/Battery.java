package fr.lionware.ecorally.models.Car.Components;

public class Battery extends Component {
    private static double DEFAULT_LEVEL = 100;

    private double capacity;
    private double level;

    public Battery(String _name, int _price, int _weight, double _capacity) {
        super(_name, _price, _weight);

        capacity = _capacity;
        level = DEFAULT_LEVEL;
    }

    @Override
    public double getCoefficient() {
        return 0;
    }

    public double getCurrentCapacity() {
        return capacity * (level/100);
    }
}
