package fr.lionware.ecorally.models;

public class Score {
    private int value;
    private String date;
    private Playground playground;

    public Score(int _value, String _date, Playground _playground) {
        value = _value;
        date = _date;
        playground = _playground;
    }
}
