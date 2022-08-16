package com.exdrill.guarding.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class Config {

    private static File file;
    public static int barbedDamage;
    public static double baseKnockbackValue;
    public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();


    public static void run() {
        if (file != null) {
            return;
        }
        file = new File(FabricLoader.getInstance().getConfigDir().toFile(),"guarding.json");

        if (!file.exists()) {
            write();
        }
        if (file.exists()) {
            read();
        }
    }

    public static void read() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
            barbedDamage = json.get("barbed_damage").getAsInt();
            baseKnockbackValue = json.get("base_knockback_value").getAsDouble();
        } catch (FileNotFoundException error) {
            error.printStackTrace();
        }
    }

    private static void write() {

        JsonObject jsonObject = new JsonObject();

        integerProperty(jsonObject, "barbed_damage", 4);
        floatProperty(jsonObject, "base_knockback_value", 0.5F);

        String json = GSON.toJson(jsonObject);

        try (FileWriter fileWriter = new FileWriter(file))  {
            fileWriter.write(json);
        } catch (IOException error) {
            error.printStackTrace();
        }

    }

    private static void integerProperty(JsonObject json, String key, int value) {
        json.addProperty(key, value);
    }

    private static void floatProperty(JsonObject json, String key, float value) {
        json.addProperty(key, value);
    }

    private static void booleanProperty(JsonObject json, String key, boolean value) {
        json.addProperty(key, value);
    }
}
