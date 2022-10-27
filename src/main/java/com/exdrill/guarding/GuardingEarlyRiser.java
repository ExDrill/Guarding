package com.exdrill.guarding;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class GuardingEarlyRiser implements Runnable {

    public void run() {
        MappingResolver mappingResolver = FabricLoader.getInstance().getMappingResolver();

        String enchantmentTarget = mappingResolver.mapClassName("intermediary", "net.minecraft.class_1886");
        ClassTinkerers.enumBuilder(enchantmentTarget, new Class[0]).addEnumSubclass("GUARDING_SHIELD", "com.exdrill.guarding.util.GuardingEnchantmentTarget").build();
    }
}
