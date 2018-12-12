package fr.lionware.ecorally.controllers;

import fr.lionware.ecorally.models.Car.Components.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Garage extends Controller {
    @FXML
    TableView<Component> tableView;

    private ObservableList<Component> componentList;

    public Garage() {
        tableView = new TableView<>();
        componentList = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {
        ObservableList columns = tableView.getColumns();

        TableColumn<Component, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Component, String> priceColumn = new TableColumn<>("Prix");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Component, String> caracColumn = new TableColumn<>("Caractéristiques");
        caracColumn.setCellValueFactory(new PropertyValueFactory<>("caracteristics"));

        columns.addAll(nameColumn, priceColumn, caracColumn);
    }

    /**
     * Add every component in the table
     */
    public void configure() {
        componentList.addAll(mainApp.getComponentList());
        tableView.setItems(componentList);

        System.out.println(componentList);
    }
}
