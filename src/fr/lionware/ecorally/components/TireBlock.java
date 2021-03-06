package fr.lionware.ecorally.components;

import fr.lionware.ecorally.controllers.Store;
import fr.lionware.ecorally.models.Car.Components.Component;
import fr.lionware.ecorally.models.Car.Components.Engine;
import fr.lionware.ecorally.utils.ComponentType;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class TireBlock extends ComponentBlock {
    public TireBlock(Component _engine, Store _store, ComponentType _type) {
        super(_engine, _store, _type);

        draw("tire.png", null);
    }

    protected void redraw() {
        draw("engine.png", null);
    }
}
