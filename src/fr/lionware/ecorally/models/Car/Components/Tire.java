package fr.lionware.ecorally.models.Car.Components;

public class Tire extends Component {
    private static int DEFAULT_USURY = 0;

    private int usury;

    public Tire(String _name, int _price, int _weight) {
        super(_name, _price, _weight);

        usury = DEFAULT_USURY;
    }

    public double getCoefficient() {
        return 0;
    }
}
