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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Garage extends Controller {
    private static int NUMBER_OF_COLUMNS = 4;

    @FXML
    TabPane tabs;

    private Map<ComponentType, GridPane> gridMap;

    public Garage() {
        tabs = new TabPane();
        gridMap = new HashMap<>();
    }

    @FXML
    private void initialize() {
        Arrays.stream(ComponentType.values()).forEach((type) -> {
            generateTabs(type, tabs, NUMBER_OF_COLUMNS, gridMap);
        });
    }

    static void generateTabs(ComponentType type, TabPane tabs, int numberOfColumns, Map<ComponentType, GridPane> gridMap) {
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
            gridPane.getColumnConstraints().add(constraints);
            constraints.setFillWidth(true);
        }

        gridMap.put(type, gridPane);
    }

    @Override
    public void configure() {
        gridMap.forEach((type, grid) -> populateGrid(grid, type));
    }

    private void populateGrid(GridPane gridPane, ComponentType type) {
        gridPane.getChildren().clear();

        List<Component> owned = mainApp.getUser().getComponentsOwned().get(type);

        Constructor<?> constructor = type.getBlockClass().getConstructors()[0];

        System.out.println("///////" + type.getLabel() + "///////");

        for (int i = 0; i < owned.size(); i++) {
            try {
                Object block = constructor.newInstance(owned.get(i), null, type);
                ((ComponentBlock) block).setListener(getMainApp(), "equip", type);

                gridPane.add((ComponentBlock) block, i%NUMBER_OF_COLUMNS, i/NUMBER_OF_COLUMNS);
            } catch(InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        System.out.println(gridPane.getChildren());
    }

    public void openStore() {
        mainApp.switchToPane("Store");
    }

    public void openMainMenu() { mainApp.switchToPane("MainMenu"); }

    public void afterSwitch() {
        configure();
    }
}
