package fr.lionware.ecorally.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static int DEFAULT_MONEY = 0;

    private List<Score> scores;
    private String username;
    private int money;

    public User(String _username) {
        username = _username;
        scores = new ArrayList<>();
        money = DEFAULT_MONEY;
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

    /**
     * Get the amount of money that the player possess
     * @return The money the player actually possess
     */
    public int getMoney() {
        return money;
    }
}
