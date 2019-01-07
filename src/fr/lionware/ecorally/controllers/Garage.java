package fr.lionware.ecorally.controllers;

import fr.lionware.ecorally.components.ComponentBlock;
import fr.lionware.ecorally.components.EngineBlock;
import fr.lionware.ecorally.models.Car.Components.Component;
import fr.lionware.ecorally.models.Car.Components.Engine;
import fr.lionware.ecorally.utils.ComponentType;
import fr.lionware.ecorally.utils.Rarity;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.*;

public class Garage extends Controller {
    private static int numberOfColumns = 4;
    private static List<ComponentBlock> items;

    @FXML
    TabPane tabs;

    public Garage() {
        tabs = new TabPane();
        items = new ArrayList<>();
    }

    @FXML
    private void initialize() {
        Arrays.stream(ComponentType.values()).forEach((type) -> {
            Tab tab = new Tab(type.getLabel());
            tabs.getTabs().add(tab);

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setFitToWidth(true);

            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(5));
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            scrollPane.setContent(gridPane);
            tab.setContent(scrollPane);

            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setPercentWidth(25);

            for (int i = 0; i < numberOfColumns; i++) {
                gridPane.getColumnConstraints().addAll(constraints);

                constraints.setFillWidth(true);

                for (int j = 0; j < numberOfColumns; j++) {
                    Engine engine = new Engine("Moteur " + (i * numberOfColumns + j), 0, 0, 0, Rarity.COMMON);

                    EngineBlock block = new EngineBlock(engine, null, null);
                    items.add(block);

                    gridPane.add(block, i, j);
                }
            }
        });
    }

    @Override
    public void configure() {
        items.forEach((item) -> item.setListener(getMainApp(), "equip"));
    }

    public void openStore() {
        mainApp.switchToPane("Store");
    }
}
