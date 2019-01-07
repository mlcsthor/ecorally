package fr.lionware.ecorally.models;

import fr.lionware.ecorally.models.Car.Car;
import fr.lionware.ecorally.models.Car.Components.Component;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static int DEFAULT_MONEY = 50;

    private List<Score> scores;
    private List<Component> componentsOwned;
    private String username;
    private int money;
    private Car car;

    public User(String _username) {
        username = _username;
        scores = new ArrayList<>();
        componentsOwned = new ArrayList<>();
        money = DEFAULT_MONEY;
        car = new Car();
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

    public void buyComponent(Component component) {
        if (!componentsOwned.contains(component)) {
            componentsOwned.add(component);
            payMoney(component.getPrice());
            car.addComponent(component);
        }
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

    public boolean hasEnoughMoney(int price) {
        return money >= price;
    }
}
