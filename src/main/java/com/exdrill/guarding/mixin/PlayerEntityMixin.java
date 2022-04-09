package com.exdrill.guarding.mixin;

import com.exdrill.guarding.enchantment.ShieldEnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
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
    public int shieldParry = 0;
    public double parryKnockback = 0D;
    PlayerEntity player = (PlayerEntity) (Object) this;

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (player.isBlocking()) {
            shieldUseDuration++;
            shieldParry++;
        } else if(shieldUseDuration != 0 && !player.isBlocking()) {
            shieldUseDuration-=2;
            shieldParry = 0;
        }
        if (shieldUseDuration <= 0) {
            shieldUseDuration = 0;
        }

        if (ShieldEnchantmentHelper.hasPummeling(player)) {
            parryKnockback = 1.0D;
        } else {
            parryKnockback = 0.5D;
        }
    }

    @Inject(method = "takeShieldHit", at = @At("TAIL"))
    private void takeShieldHit(LivingEntity attacker, CallbackInfo ci) {
        System.out.println(parryKnockback);
        if (shieldParry <= 8 && shieldParry >= 0) {
            attacker.playSound(SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.5F, 1.0F);
            if (ShieldEnchantmentHelper.hasBarbed(player)) {
                attacker.damage(DamageSource.thorns(attacker), 3.0F);
            }
            attacker.takeKnockback( parryKnockback,  -1 * (attacker.getX() - player.getX()), -1 * (attacker.getZ() - player.getZ()));
            shieldParry = 8;
        }
        if (shieldUseDuration > 100) {
            this.disableShield(false);
        }
    }
}
