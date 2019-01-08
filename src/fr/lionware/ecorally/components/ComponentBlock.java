package fr.lionware.ecorally.components;

import fr.lionware.ecorally.MainApp;
import fr.lionware.ecorally.controllers.Store;
import fr.lionware.ecorally.models.Car.Components.Component;
import fr.lionware.ecorally.utils.ComponentType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.Optional;

public abstract class ComponentBlock extends VBox {
    protected Component component;
    protected Store store;
    protected ComponentType type;

    public ComponentBlock(Component _component, Store _store, ComponentType _type) {
        store = _store;
        component = _component;
        type = _type;
    }

    /**
     * Generate the header of the block with price and weight
     * @return The header of the block
     */
    private HBox generateHeader() {
        Label price = new Label("" + component.getPrice());
        price.setTextFill(Color.WHITE);

        Label weight = new Label("" + component.getWeight());
        weight.setTextFill(Color.WHITE);

        HBox header = new HBox();
        header.setPadding(new Insets(0, 0, 5, 0));
        Region headerRegion = new Region();
        HBox.setHgrow(headerRegion, Priority.ALWAYS);
        header.getChildren().addAll(price, headerRegion, weight);

        return header;
    }

    /**
     * Generate the title of the block with the name of the component
     * @return The title of the block
     */
    private HBox generateTitle() {
        Label name = new Label(component.getName());
        name.setTextFill(Color.WHITE);

        HBox title = new HBox();
        title.setAlignment(Pos.CENTER);
        title.getChildren().add(name);

        return title;
    }

    /**
     * Generate the body of the block with icon
     * @param imageName name of the icon in folder components/res
     * @return The body of the block
     */
    protected HBox generateBody(String imageName) {
        ImageView imageView = new ImageView();
        Image image = new Image(MainApp.class.getResourceAsStream("components/res/" + imageName));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(100);
        imageView.setImage(image);

        HBox main = new HBox();
        main.setAlignment(Pos.CENTER);
        main.getChildren().add(imageView);

        return main;
    }

    /**
     * Generate the footer of the block
     * @return The footer of the block
     */
    protected HBox generateFooter(String footerInformation) {
        Label aerodynamism = new Label(footerInformation);
        aerodynamism.setTextFill(Color.WHITE);

        HBox footer = new HBox();
        footer.setPadding(new Insets(5, 0, 0, 0));
        Region footerRegion = new Region();
        HBox.setHgrow(footerRegion, Priority.ALWAYS);
        footer.getChildren().addAll(footerRegion, aerodynamism);

        return footer;
    }

    /**
     * Draw the block
     * @param imageName Name of the icon used in the body
     * @param footerInformation Information displayed in footer
     */
    protected void draw(String imageName, String footerInformation) {
        if (footerInformation != null) {
            getChildren().addAll(generateHeader(), generateTitle(), generateBody(imageName), generateFooter(footerInformation));
        } else {
            getChildren().addAll(generateHeader(), generateTitle(), generateBody(imageName));
        }

        getStyleClass().addAll("component-block", component.getRarity().getClassName());
    }

    /**
     * Set the mouse listener
     * @param mainApp Reference to the main app
     */
    public void setListener(MainApp mainApp, String type, ComponentType componentType) {
        setOnMouseClicked((e) -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (type.equals("equip")) {
                    System.out.println("Player want to equip");
                    mainApp.getUser().getCar().addComponent(component);
                } else if (type.equals("buy")) {
                    if (mainApp.getUser().hasEnoughMoney(component.getPrice())) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Achat d'un composant");
                        alert.setContentText("Voulez-vous vraiment acheter : " + component.getName() + " ?");

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.get() == ButtonType.OK) {
                            mainApp.getUser().buyComponent(component, componentType);
                            store.removeComponents(component, this.type);
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Achat d'un composant");
                        alert.setContentText("Vous n'avez pas assez d'argent pour acheter : " + component.getName());
                        alert.showAndWait();
                    }
                }
            }
        });
    }
}
