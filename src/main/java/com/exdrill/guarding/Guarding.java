package com.exdrill.guarding;

import com.chocohead.mm.api.ClassTinkerers;
import com.exdrill.guarding.config.Config;
import com.exdrill.guarding.registry.GuardingEnchantments;
import com.exdrill.guarding.registry.GuardingParticles;
import com.exdrill.guarding.registry.ModItems;
import com.exdrill.guarding.registry.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Guarding implements ModInitializer {

    public static String NAMESPACE = "guarding";
    public static final Identifier PARRY = new Identifier(NAMESPACE, "parry");
    public static final EnchantmentTarget GUARDING_SHIELD = ClassTinkerers.getEnum(EnchantmentTarget.class, "GUARDING_SHIELD");
    public static Config config;

    public void onInitialize() {
        GuardingParticles.register();
        config = Config.run();
        ModSounds.register();
        ModItems.register();
        GuardingEnchantments.register();
        ResourceConditions.register(new Identifier(NAMESPACE, "experimental_netherite_shield_toggle_enabled"), (jsonObject -> config.enableExperimentalNetheriteShield()));

        // Stats
        Registry.register(Registry.CUSTOM_STAT, new Identifier(NAMESPACE, "parry"), PARRY);
        Stats.CUSTOM.getOrCreateStat(PARRY, StatFormatter.DEFAULT);
    }
}
