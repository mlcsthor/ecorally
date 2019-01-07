package fr.lionware.ecorally.components;

import fr.lionware.ecorally.controllers.Store;
import fr.lionware.ecorally.models.Car.Components.Body;
import fr.lionware.ecorally.models.Car.Components.Component;
import fr.lionware.ecorally.models.Car.Components.Engine;
import fr.lionware.ecorally.utils.ComponentType;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class BodyBlock extends ComponentBlock {
    public BodyBlock(Component _body, Store _store, ComponentType _type) {
        super(_body, _store, _type);

        draw("body.png", "" + ((Body)component).getAerodynamism());
    }

    protected void redraw() {
        draw("body.png", "" + ((Body)component).getAerodynamism());
    }
}
