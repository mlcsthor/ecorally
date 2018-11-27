package fr.lionware.ecorally.models.Car.Components;

public class Body extends Component {
    private double aerodynamism;

    public Body(String _name, int _price, int _weight, double _aerodynamism) {
        super(_name, _price, _weight);

        aerodynamism = _aerodynamism;
    }

    @Override
    public double getCoefficient() {
        return 0;
    }
}
