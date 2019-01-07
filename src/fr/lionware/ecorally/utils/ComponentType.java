package fr.lionware.ecorally.utils;

import fr.lionware.ecorally.components.*;

import java.io.Serializable;

public enum ComponentType implements Serializable {
    ENGINE("Moteur", "engine.json", EngineBlock.class),
    BATTERY("Batterie", "battery.json", BatteryBlock.class),
    BODY("Carrosserie", "body.json", BodyBlock.class),
    TIRE("Pneu", "tire.json", TireBlock.class),
    SOLARPANEL("Panneau solaire", "solar_panel.json", SolarPanelBlock.class),
    FUELCELL("Pile à combustible", "fuel_cell.json", FuelCellBlock.class);

    private String label;
    private String fileName;
    private Class<? extends ComponentBlock> blockClass;

    ComponentType(String _label, String _fileName, Class<? extends ComponentBlock> _blockClass) {
        label = _label;
        fileName = _fileName;
        blockClass = _blockClass;
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

    public Class<? extends ComponentBlock> getBlockClass() { return blockClass; }
}
