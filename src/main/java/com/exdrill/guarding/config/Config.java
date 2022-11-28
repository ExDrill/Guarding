package com.exdrill.guarding.config;

import com.exdrill.guarding.Guarding;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public record Config(float parryKnockback, float parryExhaustion, int parryTickRange, int barbedDamage, boolean requireSneak, boolean applyCooldown, boolean shieldHuggingPunishment, boolean enableExperimentalFeatures) {

    private static Properties properties = new Properties();

    public static Config run() {
        float parryKnockback = 0.5F;
        int parryExhaustion = 5;
        int parryTickRange = 2;
        int barbedDamage = 3;
        boolean requireSneak = false;
        boolean applyCooldown = false;
        boolean enableShieldHuggingPunishment = true;
        boolean enableExperimentalFeatures = false;

        // Config File + Properties
        File file = new File(FabricLoader.getInstance().getConfigDir().toFile(), "guarding.properties");
        properties.setProperty("parry_knockback", "0.5");
        properties.setProperty("parry_exhaustion", "3");
        properties.setProperty("parry_tick_range", "2");
        properties.setProperty("barbed_damage", "3");
        properties.setProperty("require_sneak", "false");
        properties.setProperty("apply_cooldown", "false");
        properties.setProperty("shield_hug_punish_duration", "true");
        properties.setProperty("enable_experimental_features", "false");

        // Deletes the legacy JSON file.
        File jsonFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "guarding.json");
        if (jsonFile.delete()) {
            Guarding.LOGGER.warning("Deleting " + jsonFile.getName() + "...");
        }

        // Load file
        try {
            if (file.isFile()) {
                Properties loadedProperties = new Properties();

                try (FileReader reader = new FileReader(file)) {
                    loadedProperties.load(reader);
                }

                for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                    loadedProperties.putIfAbsent(entry.getKey(), entry.getValue());
                }

                properties = loadedProperties;
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
            parryTickRange = Integer.parseInt(properties.getProperty("parry_tick_range"));
            barbedDamage = Integer.parseInt(properties.getProperty("barbed_damage"));
            requireSneak = Boolean.parseBoolean(properties.getProperty("require_sneak"));
            applyCooldown = Boolean.parseBoolean(properties.getProperty("apply_cooldown"));
            enableShieldHuggingPunishment = Boolean.parseBoolean(properties.getProperty("shield_hugging_punishment"));
            enableExperimentalFeatures = Boolean.parseBoolean(properties.getProperty("enable_experimental_features"));
        } catch (Exception error) {
            error.printStackTrace();
        }

        return new Config(parryKnockback, parryExhaustion, parryTickRange, barbedDamage, requireSneak, applyCooldown, enableShieldHuggingPunishment, enableExperimentalFeatures);
    }
}
