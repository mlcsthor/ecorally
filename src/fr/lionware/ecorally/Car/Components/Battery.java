package fr.lionware.ecorally.Car.Components;

public class Battery extends Component {
    private static int DEFAULT_LEVEL = 100;

    private double capacity;
    private int level;

    public Battery(String _name, int _price, int _weight, double _capacity) {
        super(_name, _price, _weight);

        capacity = _capacity;
        level = DEFAULT_LEVEL;
    }

    @Override
    public double getCoefficient() {
        return 0;
    }
}
