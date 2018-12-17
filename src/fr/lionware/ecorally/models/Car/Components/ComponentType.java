package fr.lionware.ecorally.models.Car.Components;

public enum ComponentType {
    ENGINE("Moteur", "engine.json"),
    BATTERY("Batterie", "battery.json"),
    BODY("Carrosserie", "body.json"),
    TIRE("Pneu", "tire.json"),
    SOLARPANEL("Panneau solaire", "solar_panel.json"),
    FUELCELL("Pile à combustible", "fuel_cell.json");

    private String label;
    private String fileName;

    ComponentType(String _label, String _fileName) {
        label = _label;
    }

    public String toString() {
        return "{ label: " + label + ", file: " + fileName + "}";
    }

    public String getLabel() {
        return label;
    }

    public String getFileName() {
        return fileName;
    }
}
