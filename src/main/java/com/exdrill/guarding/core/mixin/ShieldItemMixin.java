package com.exdrill.guarding.core.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShieldItem.class)
public class ShieldItemMixin extends Item {
    public ShieldItemMixin(Properties pProperties) {
        super(pProperties);
    }

    public int getEnchantmentValue(ItemStack stack) {
        return 15;
    }
}
