package fr.lionware.ecorally.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lionware.ecorally.MainApp;
import fr.lionware.ecorally.components.ComponentBlock;
import fr.lionware.ecorally.models.Car.Components.Component;
import fr.lionware.ecorally.utils.ComponentType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Store extends Controller {
    private static int numberOfColumns = 4;
    private static Map<ComponentType, List<Component>> components;

    @FXML
    TabPane tabs;

    @FXML
    Label moneyLabel;

    private Map<ComponentType, GridPane> gridMap;

    public Store() {
        tabs = new TabPane();
        moneyLabel = new Label();
        components = new HashMap<>();

        gridMap = new HashMap<>();

        Arrays.stream(ComponentType.values()).forEach((type) -> {
            components.put(type, Arrays.asList(loadComponents(type)));
        });
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
                gridPane.getColumnConstraints().add(constraints);

                constraints.setFillWidth(true);
            }

            gridMap.put(type, gridPane);
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
                ((ComponentBlock) block).setListener(getMainApp(), "buy");

                gridPane.add((ComponentBlock) block, i%numberOfColumns, i/numberOfColumns);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeComponents(Component component, ComponentType type) {
        List<Component> items = new ArrayList<>(components.get(type));

        items.remove(component);

        components.replace(type, items);

        Component[] itemsTab = new Component[items.size()];

        for (int i = 0; i < items.size(); i++) {
            itemsTab[i] = items.get(i);
        }

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
