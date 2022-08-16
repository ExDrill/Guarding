package com.exdrill.guarding.registry;

import com.exdrill.guarding.Guarding;
import com.exdrill.guarding.enchantment.BarbedEnchantment;
import com.exdrill.guarding.enchantment.PummelingEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GuardingEnchantments {

    public static final Enchantment PUMMELING_ENCHANTMENT = register("pummeling", new PummelingEnchantment(Enchantment.Rarity.COMMON, Guarding.GUARDING_SHIELD, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    public static final Enchantment BARBED_ENCHANTMENT = register("barbed", new BarbedEnchantment(Enchantment.Rarity.RARE, Guarding.GUARDING_SHIELD, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));

    public static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, new Identifier(Guarding.NAMESPACE, name), enchantment);
    }

    public static void register() {}
}
