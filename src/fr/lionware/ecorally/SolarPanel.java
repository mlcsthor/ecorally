package fr.lionware.ecorally;

public class SolarPanel extends Component {
    public SolarPanel(String _name, int _price, int _weight) {
        super(_name, _price, _weight);
    }

    public double getCoefficient() {
        return 0;
    }
}
