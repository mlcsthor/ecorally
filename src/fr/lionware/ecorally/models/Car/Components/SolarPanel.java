package fr.lionware.ecorally.models.Car.Components;

public class SolarPanel extends Component {
    public SolarPanel(String _name, int _price, int _weight) {
        super(_name, _price, _weight);
    }

    public double getCoefficient() {
        return 0;
    }

    @Override
    public String getCharacteristics() {
        return null;
    }
}
