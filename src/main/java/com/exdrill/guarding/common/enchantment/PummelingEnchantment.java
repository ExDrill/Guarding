package com.exdrill.guarding.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PummelingEnchantment extends Enchantment {

    public PummelingEnchantment(Rarity weight, EnchantmentCategory type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    public int getMinCost(int level) {
        return 10 * level;
    }

    public int getMaxCost(int level) {
        return this.getMinCost(level) + 30;
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ShieldItem;
    }

}
