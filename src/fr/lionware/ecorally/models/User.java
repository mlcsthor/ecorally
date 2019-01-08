package fr.lionware.ecorally.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.corba.se.spi.ior.ObjectKey;
import fr.lionware.ecorally.MainApp;
import fr.lionware.ecorally.models.Car.Car;
import fr.lionware.ecorally.models.Car.Components.Component;
import fr.lionware.ecorally.utils.ComponentType;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class User {
    private static int DEFAULT_MONEY = 50;

    @JsonIgnore
    private List<Score> scores;
    @JsonIgnore
    private Map<ComponentType, List<Component>> componentsOwned;
    private String username;
    private int money;
    @JsonIgnore
    private Car car;

    public User(String _username) {
        username = _username;
        scores = new ArrayList<>();
        componentsOwned = new HashMap<>();
        money = DEFAULT_MONEY;
        car = new Car();

        loadComponents();
    }

    public User() {
        this("Invite");
    }

    /**
     * The player earn money
     * @param amount The amount to add
     */
    public void earnMoney(int amount) {
        money += (amount > 0) ? amount : 0;
    }

    /**
     * The player pay for something
     * @param amount The amount to remove
     */
    public void payMoney(int amount) {
        money -= (amount > 0) ? amount : 0;
    }

    public void buyComponent(Component component, ComponentType type) {
        List<Component> owned = new ArrayList<>(componentsOwned.get(type));

        if (!owned.contains(component)) {
            owned.add(component);
            payMoney(component.getPrice());
            car.addComponent(component);

            saveComponents(type, Component.generateTabFromList(owned));
        }

        componentsOwned.replace(type, owned);
    }

    /**
     * Get the amount of money that the player possess
     * @return The money the player actually possess
     */
    public int getMoney() {
        return money;
    }

    public Car getCar() { return car; }

    public String getUsername() { return username; }

    public Map<ComponentType, List<Component>> getComponentsOwned() {
        return componentsOwned;
    }

    public boolean hasEnoughMoney(int price) {
        return money >= price;
    }

    public void loadComponents() {
        Arrays.stream(ComponentType.values()).forEach(this::loadComponents);
    }

    public void loadComponents(ComponentType type) {
        String fileName = MainApp.class.getResource("res/user/components/" + type.getFileName()).getPath();

        Component[] items = Component.readFromFile(fileName);

        componentsOwned.put(type, Arrays.asList(items));
    }

    public void saveComponents() {
        Arrays.stream(ComponentType.values()).forEach((type) -> {
            List<Component> items = componentsOwned.get(type);

            Component[] itemsTab = Component.generateTabFromList(items);

            saveComponents(type, itemsTab);
        });
    }

    public void saveComponents(ComponentType type, Component[] components) {
        ObjectMapper mapper = new ObjectMapper();

        String fileName = MainApp.class.getResource("res/user/components/" + type.getFileName()).getPath();

        try {
            mapper.writerFor(Component[].class).writeValue(new File(fileName), components);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User load() {
        ObjectMapper mapper = new ObjectMapper();

        String fileName = MainApp.class.getResource("res/user/save.json").getPath();

        User user = new User();

        try {
            user = mapper.readValue(new File(fileName), User.class);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void save() {
        ObjectMapper mapper = new ObjectMapper();

        String fileName = MainApp.class.getResource("res/user/save.json").getPath();

        try {
            mapper.writeValue(new File(fileName), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
