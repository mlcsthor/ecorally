package fr.lionware.ecorally.components;

import fr.lionware.ecorally.controllers.Store;
import fr.lionware.ecorally.models.Car.Components.Battery;
import fr.lionware.ecorally.models.Car.Components.Component;
import fr.lionware.ecorally.models.Car.Components.Engine;
import fr.lionware.ecorally.utils.ComponentType;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class BatteryBlock extends ComponentBlock {
    public BatteryBlock(Component _battery, Store _store, ComponentType _type) {
        super(_battery, _store, _type);

        draw("battery.png", "" + ((Battery)component).getCapacity());
    }

    protected void redraw() {
        draw("engine.png", "" + ((Battery)component).getCapacity());
    }
}
