package fr.lionware.ecorally.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ground {
    String name;
    String generatedDate;
    double[][] coordinates;

    public Ground(String _name, double[][] _coordinates){
        name = _name;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        generatedDate = dateFormat.format(new Date());
        coordinates = _coordinates;
        saveTerrainInJsonFile();
    }

    public void saveTerrainInJsonFile(){

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        File file = new File("terrain.json");
        try {
            // Serialize Java object info JSON file.
            mapper.writeValue(file, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
