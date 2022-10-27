package com.exdrill.guarding.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public record Config(float parryKnockback, float parryExhaustion, int barbedDamage, boolean shieldHuggingPunishment, boolean enableExperimentalNetheriteShield) {

    public static Config run() {

        // Define variables
        float parryKnockback = 0.5F;
        int parryExhaustion = 5;
        int barbedDamage = 3;
        boolean enableShieldHuggingPunishment = true;
        boolean enableExperimentalNetheriteShield = false;

        // Config File + Properties
        File file = new File(FabricLoader.getInstance().getConfigDir().toFile(), "guarding.properties");
        properties.setProperty("parry_knockback", "0.5");
        properties.setProperty("parry_exhaustion", "3");
        properties.setProperty("barbed_damage", "3");
        properties.setProperty("shield_hugging_punishment", "true");
        properties.setProperty("enabled_experimental_netherite_shield", "false");

        // Delete the JSON file if it exists, just to avoid any confusion.
        File jsonFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "guarding.json");
        if (jsonFile.exists()) {
            jsonFile.delete();
        }

        // Load file
        try {
            if (file.isFile()) {
                Properties loaded = new Properties();

                try (FileReader reader = new FileReader(file)) {
                    loaded.load(reader);
                }

                for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                    loaded.putIfAbsent(entry.getKey(), entry.getValue());
                }

                properties = loaded;
            }

            file.createNewFile();

            try (FileWriter writer = new FileWriter(file)) {
                properties.store(writer, "Guarding Configurations");
            }

        } catch (IOException error) {
            error.printStackTrace();
        }

        try {
            parryKnockback = Float.parseFloat(properties.getProperty("parry_knockback"));
            parryExhaustion = Integer.parseInt(properties.getProperty("parry_exhaustion"));
            barbedDamage = Integer.parseInt(properties.getProperty("barbed_damage"));
            enableShieldHuggingPunishment = Boolean.parseBoolean(properties.getProperty("shield_hugging_punishment"));
            enableExperimentalNetheriteShield = Boolean.parseBoolean(properties.getProperty("enable_experimental_netherite_shield"));
        } catch (Exception error) {
            error.printStackTrace();
        }

        return new Config(parryKnockback, parryExhaustion, barbedDamage, enableShieldHuggingPunishment, enableExperimentalNetheriteShield);
    }

    private static Properties properties = new Properties();
}
