package fr.lionware.ecorally;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lionware.ecorally.models.Car.Components.*;
import fr.lionware.ecorally.utils.Rarity;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Generator {
    public static void main(String[] args) {
        generateComponents();
    }

    public static void generateComponents() {
        ObjectMapper mapper = new ObjectMapper();
        String fileName;

        Map<String, Component[]> componentsMap = new HashMap<>();

        /* engine generation */
        Component[] engines = new Component[Rarity.values().length];
        for (int i = 1; i <= Rarity.values().length; i++) {
            Engine engine = new Engine("Moteur n°" + i, 5*i, 5*i, 10*i, Rarity.values()[i-1]);
            engines[i-1] = engine;
        }
        fileName = MainApp.class.getResource("res/components/engine.json").getPath();
        componentsMap.put(fileName, engines);

        /* battery generation */
        Component[] batteries = new Component[Rarity.values().length];
        for (int i = 1; i <= Rarity.values().length; i++) {
            Battery battery = new Battery("Battery n°" + i, 5*i, 5*i, 10*i, Rarity.values()[i-1]);
            batteries[i-1] = battery;
        }
        fileName = MainApp.class.getResource("res/components/battery.json").getPath();
        componentsMap.put(fileName, batteries);

        /* body generation */
        Component[] bodies = new Component[Rarity.values().length];
        for (int i = 1; i <= Rarity.values().length; i++) {
            Body body = new Body("Carroserrie n°" + i, 5*i, 5*i, 10*i, Rarity.values()[i-1]);
            bodies[i-1] = body;
        }
        fileName = MainApp.class.getResource("res/components/body.json").getPath();
        componentsMap.put(fileName, bodies);

        /* fuel cell generation */
        Component[] fuelCells = new Component[Rarity.values().length];
        for (int i = 1; i <= Rarity.values().length; i++) {
            FuelCell fuelCell = new FuelCell("Pile n°" + i, 5*i, 5*i, Rarity.values()[i-1]);
            fuelCells[i-1] = fuelCell;
        }
        fileName = MainApp.class.getResource("res/components/fuel_cell.json").getPath();
        componentsMap.put(fileName, fuelCells);

        /* solar pane generation */
        Component[] solarPanels = new Component[Rarity.values().length];
        for (int i = 1; i <= Rarity.values().length; i++) {
            SolarPanel solarPanel = new SolarPanel("Panneau n°" + i, 5*i, 5*i, Rarity.values()[i-1]);
            solarPanels[i-1] = solarPanel;
        }
        fileName = MainApp.class.getResource("res/components/solar_panel.json").getPath();
        componentsMap.put(fileName, solarPanels);

        /* tire generation */
        Component[] tires = new Component[Rarity.values().length];
        for (int i = 1; i <= Rarity.values().length; i++) {
            Tire tire = new Tire("Pneu n°" + i, 5*i, 5*i, Rarity.values()[i-1]);
            tires[i-1] = tire;
        }
        fileName = MainApp.class.getResource("res/components/tire.json").getPath();
        componentsMap.put(fileName, tires);

        componentsMap.forEach((file, components) -> {
            try {
                mapper.writerFor(Component[].class).writeValue(new File(file), components);
                System.out.println(mapper.writerFor(Component[].class).writeValueAsString(components));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
