package com.exdrill.guarding.enchantment;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;

public class PummelingEnchantment extends FabricShieldEnchantment {
    public PummelingEnchantment(Rarity weight, boolean isTreasure, boolean isCurse) {
        super(weight, isTreasure, isCurse);
    }

    public int getMinPower(int level) {
        return 0;
    }

    public int getMaxPower(int level) {
        return 50;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return super.isAcceptableItem(stack) && stack.getItem() instanceof ShieldItem;
    }

    public int getMaxLevel() {
        return 1;
    }

    public boolean canAccept(Enchantment other) {
        return !(other instanceof BarbedEnchantment) && super.canAccept(other);
    }

}
