package com.exdrill.guarding.mixin;

import com.exdrill.guarding.Guarding;
import net.minecraft.client.sound.Sound;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public abstract void disableShield(boolean sprinting);

    @Mutable
    public int shieldUseDuration = 0;
    PlayerEntity player = (PlayerEntity) (Object) this;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (player.isBlocking()) {
            shieldUseDuration++;
        } else if(shieldUseDuration != 0 && !player.isBlocking()) {
            shieldUseDuration-=2;
        }
        if (shieldUseDuration <= 0) {
            shieldUseDuration = 0;
        }
    }

    @Inject(method = "takeShieldHit", at = @At("HEAD"))
    private void takeShieldHit(LivingEntity attacker, CallbackInfo ci) {
        if (shieldUseDuration <= 13 && shieldUseDuration > 3) {
            attacker.knockbackVelocity = 1f;
            attacker.takeKnockback(1f, 0, 0);
            System.out.println("Shield Parry!");
            shieldUseDuration = 10;
            attacker.playSound(SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.5F, 1.0F);
        }

        if (shieldUseDuration > 100) {
            this.disableShield(false);
            shieldUseDuration = 20;
        }
        shieldUseDuration = 13;

    }
}
