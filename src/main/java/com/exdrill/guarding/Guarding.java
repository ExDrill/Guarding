package com.exdrill.guarding;

import com.exdrill.guarding.enchantment.BarbedEnchantment;
import com.exdrill.guarding.enchantment.PummelingEnchantment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Guarding implements ModInitializer {
    public static String NAMESPACE = "guarding";

    private static Enchantment BARBED = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier(NAMESPACE, "barbed"),
            new BarbedEnchantment()
    );

    private static Enchantment PUMMELING = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier(NAMESPACE, "pummeling"),
            new PummelingEnchantment()
    );

    @Override
    public void onInitialize() {
        System.out.println("Guarding is loaded");
    }
}
