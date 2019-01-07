package fr.lionware.ecorally.utils;

import javafx.scene.paint.Color;

public enum Rarity {
    COMMON("Commun", "common"),
    RARE("Rare", "rare"),
    EPIC("Épique", "epic"),
    EXOTIC("Exotique", "exotic");

    private String label;
    private String className;

    Rarity(String _label, String _className) {
        label = _label;
        className = _className;
    }

    public String getLabel() {
        return label;
    }

    public String getClassName() { return className; }
}
