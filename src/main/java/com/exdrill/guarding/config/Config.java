package com.exdrill.guarding.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.terraformersmc.modmenu.ModMenu;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class Config {

    private static File file;
    public static int barbedDamage;
    public static double baseKnockbackValue;

    public static void run() {
        if (file != null) {
            return;
        }
        file = new File(FabricLoader.getInstance().getConfigDir().toFile(),"guarding.json");

        try {
            if (!file.exists()) {
                write();
            }
            if (file.exists()) {
                read();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void read() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        JsonObject json = new JsonParser().parse(reader).getAsJsonObject();

        barbedDamage = json.get("barbed_damage").getAsInt();
        baseKnockbackValue = json.get("base_knockback_value").getAsDouble();

    }

    private static void write() throws FileNotFoundException {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("barbed_damage", 4);
        jsonObject.addProperty("base_knockback_value", 0.5);
        String json = ModMenu.GSON.toJson(jsonObject);


        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
