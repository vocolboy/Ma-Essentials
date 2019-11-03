package com.maciej916.maessentials.libs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maciej916.maessentials.config.ConfigValues;

import java.io.*;

public class Json {

    public static void save(Object saveClass, String fileName) {
        try (Writer writer = new FileWriter(ConfigValues.worldCatalog + fileName + ".json")) {
            Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
            gson.toJson(saveClass, writer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static Object load(String fileName, Object object) {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(ConfigValues.worldCatalog + fileName + ".json")) {
            return gson.fromJson(reader, object.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}