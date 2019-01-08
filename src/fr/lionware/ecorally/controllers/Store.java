package fr.lionware.ecorally.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lionware.ecorally.MainApp;
import fr.lionware.ecorally.components.ComponentBlock;
import fr.lionware.ecorally.models.Car.Components.Component;
import fr.lionware.ecorally.utils.ComponentType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Store extends Controller {
    private static int NUMBER_OF_COLUMNS = 4;

    @FXML
    TabPane tabs;

    @FXML
    Label moneyLabel;

    private Map<ComponentType, GridPane> gridMap;
    private Map<ComponentType, List<Component>> components;

    public Store() {
        tabs = new TabPane();
        moneyLabel = new Label();
        components = new HashMap<>();
        gridMap = new HashMap<>();
    }

    @FXML
    private void initialize() {
        Arrays.stream(ComponentType.values()).forEach((type) -> {
            components.put(type, Arrays.asList(loadComponents(type)));

            Garage.generateTabs(type, tabs, NUMBER_OF_COLUMNS, gridMap);
        });
    }

    @Override
    public void configure() {
        gridMap.forEach((key, grid) -> populateGrid(grid, key));

        updateMoney();
    }

    private void updateMoney() {
        moneyLabel.setText("" + mainApp.getUser().getMoney());
    }

    private void populateGrid(GridPane gridPane, ComponentType key) {
        gridPane.getChildren().clear();

        List<Component> items = components.get(key);

        Constructor<?> constructor = key.getBlockClass().getConstructors()[0];

        for (int i = 0; i < items.size(); i++) {
            try {
                Object block = constructor.newInstance(items.get(i), this, key);
                ((ComponentBlock) block).setListener(getMainApp(), "buy", key);

                gridPane.add((ComponentBlock) block, i% NUMBER_OF_COLUMNS, i/ NUMBER_OF_COLUMNS);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeComponents(Component component, ComponentType type) {
        List<Component> items = new ArrayList<>(components.get(type));

        items.remove(component);

        components.replace(type, items);

        Component[] itemsTab = Component.generateTabFromList(items);

        try {
            removeFromFile(itemsTab, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GridPane grid = gridMap.get(type);
        updateMoney();
        populateGrid(grid, type);
    }

    private Component[] loadComponents(ComponentType type) {
        String fileName = MainApp.class.getResource("res/components/" + type.getFileName()).getPath();
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();

        Component[] items = new Component[0];

        try {
            items = mapper.readerFor(Component[].class).readValue(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    private void removeFromFile(Component[] items, ComponentType type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String fileName = MainApp.class.getResource("res/components/" + type.getFileName()).getPath();

        mapper.writerFor(Component[].class).writeValue(new File(fileName), items);
    }

    public void openGarage() { mainApp.switchToPane("Garage"); }
}
