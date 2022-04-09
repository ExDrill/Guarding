package com.exdrill.guarding.enchantment;

import com.exdrill.guarding.Guarding;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;

public class ShieldEnchantmentHelper extends EnchantmentHelper {

    public static boolean hasPummeling(LivingEntity entity) {
        return getEquipmentLevel(Guarding.PUMMELING, entity) == 1;
    }

    public static boolean hasBarbed(LivingEntity entity) {
        return getEquipmentLevel(Guarding.BARBED, entity) == 1;
    }
}
