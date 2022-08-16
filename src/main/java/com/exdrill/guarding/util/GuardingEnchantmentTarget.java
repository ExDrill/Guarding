package com.exdrill.guarding.util;

import com.exdrill.guarding.mixin.EnchantmentTargetMixin;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class GuardingEnchantmentTarget extends EnchantmentTargetMixin {

    @Override
    public boolean isAcceptableItem(Item item) {
        return item == Items.SHIELD;
    }
}
