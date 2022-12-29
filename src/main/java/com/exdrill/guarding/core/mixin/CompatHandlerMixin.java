package com.exdrill.guarding.core.mixin;

import com.exdrill.guarding.core.util.ShieldUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class CompatHandlerMixin extends LivingEntity {

    @Shadow public abstract void awardStat(Stat<Item> stat);

    @Shadow public abstract void setItemSlot(EquipmentSlot pSlot, ItemStack pStack);

    protected CompatHandlerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "disableShield", at = @At("HEAD"))
    private void disableShield(boolean sprinting, CallbackInfo ci) {
        ShieldUtil.onShieldDisable(this, sprinting);
    }

    @Inject(method = "hurtCurrentlyUsedShield", at = @At("HEAD"))
    private void damageShield(float amount, CallbackInfo ci) {
        if (this.useItem.getItem() instanceof ShieldItem && !this.getUseItem().getItem().equals(Items.SHIELD)) {
            if (!this.level.isClientSide) {
                this.awardStat(Stats.ITEM_USED.get(this.useItem.getItem()));
            }

            if (amount >= 3.0F) {
                int i = 1 + Mth.floor(amount);
                InteractionHand hand = this.getUsedItemHand();
                this.useItem.hurtAndBreak(i, this, (player) -> player.broadcastBreakEvent(hand));
                if (this.useItem.isEmpty()) {
                    if (hand == InteractionHand.MAIN_HAND) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    } else {
                        this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }

                    this.useItem = ItemStack.EMPTY;
                    this.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + this.level.random.nextFloat() * 0.4F);
                }
            }
        }
    }
}
