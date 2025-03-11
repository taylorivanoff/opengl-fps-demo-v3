package com.example.tools.serialization;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

public class LevelSerializer {
    public void saveLevel(String filePath, JSONObject levelData) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(levelData.toString(4)); // Pretty-print with 4-space indent
            System.out.println("Level saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject loadLevel(String filePath) {
        StringBuilder jsonData = new StringBuilder();
        try (FileReader reader = new FileReader(filePath)) {
            int i;
            while ((i = reader.read()) != -1) {
                jsonData.append((char) i);
            }
            System.out.println("Level loaded from " + filePath);
            return new JSONObject(jsonData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}