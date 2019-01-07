package fr.lionware.ecorally.components;

import fr.lionware.ecorally.controllers.Store;
import fr.lionware.ecorally.models.Car.Components.Component;
import fr.lionware.ecorally.models.Car.Components.Engine;
import fr.lionware.ecorally.utils.ComponentType;

public class EngineBlock extends ComponentBlock {
    public EngineBlock(Component _engine, Store _store, ComponentType _type) {
        super(_engine, _store, _type);

        draw("engine.png", "" + ((Engine)component).getPower());
    }

    protected void redraw() {
        draw("engine.png", "" + ((Engine)component).getPower());
    }
}
