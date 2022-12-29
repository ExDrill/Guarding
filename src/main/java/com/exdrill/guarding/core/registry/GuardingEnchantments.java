package com.exdrill.guarding.core.registry;

import com.exdrill.guarding.Guarding;
import com.exdrill.guarding.common.enchantment.BarbedEnchantment;
import com.exdrill.guarding.common.enchantment.PummelingEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GuardingEnchantments {

    public static final DeferredRegister<Enchantment> HELPER = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Guarding.MODID);

    public static final RegistryObject<Enchantment> PUMMELING = HELPER.register("pummeling", () -> new PummelingEnchantment(Enchantment.Rarity.COMMON, Guarding.GUARDING_SHIELD, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    public static final RegistryObject<Enchantment> BARBED = HELPER.register("barbed", () -> new BarbedEnchantment(Enchantment.Rarity.RARE, Guarding.GUARDING_SHIELD, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
}
