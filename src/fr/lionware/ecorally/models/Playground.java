package fr.lionware.ecorally.models;

import java.util.ArrayList;
import java.util.List;

public class Playground {
    private List<Item> items;
    private boolean day; // True if day, false if night

    public Playground(boolean _day) {
        items = new ArrayList<>();
        day = _day;
    }

    /**
     * Add item on playground
     * @param item The item to add
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Remove item on playground
     * @param item The item to remove
     */
    public void removeItem(Item item) {
        items.remove(item);
    }
}


