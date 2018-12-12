package fr.lionware.ecorally.utils;

import fr.lionware.ecorally.models.Car.Components.Component;

import java.io.*;
import java.util.List;

public class IO {
    private static ObjectOutputStream oos = null;
    private static ObjectInputStream ois = null;

    public static Object readFromFile(String fileName) {
        Object o = null;

        try {
            FileInputStream file = new FileInputStream(fileName);

            ois = new ObjectInputStream(file);
            o = ois.readObject();
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        return o;
    }

    public static void writeOnFile(String fileName, Object o) {
        try {
            FileOutputStream file = new FileOutputStream(fileName);

            oos = new ObjectOutputStream(file);
            oos.writeObject(o);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.flush();
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
