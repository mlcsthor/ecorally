package fr.lionware.ecorally.controllers;

import fr.lionware.ecorally.components.EngineBlock;
import fr.lionware.ecorally.models.Car.Components.ComponentType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class Garage extends Controller {
    @FXML
    TabPane tabs;

    public Garage() {
        tabs = new TabPane();
    }

    @FXML
    private void initialize() {
        Arrays.stream(ComponentType.values()).forEach((type) -> {
            Tab tab = new Tab(type.getLabel());
            tabs.getTabs().add(tab);

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setFitToWidth(true);

            TilePane tiles = new TilePane();
            tiles.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            tiles.setTileAlignment(Pos.CENTER);
            tiles.getChildren().clear();
            tiles.setHgap(10);
            tiles.setVgap(10);
            tiles.setPrefColumns(4);

            for (int i = 0; i < 9; i++) {
                EngineBlock block = new EngineBlock();
                block.setBackground(Color.DARKGREEN);

                tiles.getChildren().add(block);
            }

            tab.setContent(scrollPane);

            scrollPane.setContent(tiles);
        });
    }

    /**
     * Add elements in the grid
     */
    private void populateGrid() {

    }

    /**
     * Add every component in the table
     */
    public void configure() {}
}
