package com.exdrill.guarding.util;

import com.exdrill.guarding.mixin.EnchantmentTargetMixin;
import net.minecraft.item.Item;
import net.minecraft.item.ShieldItem;

public class GuardingEnchantmentTarget extends EnchantmentTargetMixin {

    public boolean isAcceptableItem(Item item) {
        return item instanceof ShieldItem;
    }
}
