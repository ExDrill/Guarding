package com.exdrill.guarding.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BarbedEnchantment extends Enchantment {

    public BarbedEnchantment(Rarity weight, EnchantmentCategory type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    public int getMinCost(int level) {
        return 20;
    }

    public int getMaxCost(int level) {
        return 50;
    }

    public int getMaxLevel() {
        return 1;
    }

    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ShieldItem;
    }
}
