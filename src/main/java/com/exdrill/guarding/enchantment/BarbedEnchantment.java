package com.exdrill.guarding.enchantment;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;

public class BarbedEnchantment extends FabricShieldEnchantment {
    public BarbedEnchantment(Rarity weight, boolean isTreasure, boolean isCurse) {
        super(weight, isTreasure, isCurse);
    }

    public int getMinPower(int level) {
        return 0;
    }

    public int getMaxPower(int level) {
        return 50;
    }

    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return super.isAcceptableItem(stack) && stack.getItem() instanceof ShieldItem;
    }
}
