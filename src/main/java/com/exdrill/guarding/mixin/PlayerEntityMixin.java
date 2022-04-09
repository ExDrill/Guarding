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

    @Shadow protected abstract void damageShield(float amount);

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
        if (shieldParry <= 12 && shieldParry >= 0) {
            attacker.playSound(SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.5F, 1.0F);
            if (ShieldEnchantmentHelper.hasBarbed(player)) {
                attacker.damage(DamageSource.GENERIC, 3.0F);
                this.damageShield(2.0F);
            }
            double d = attacker.getX() - player.getX();
            double e = attacker.getZ() - player.getZ();
            double f = Math.max(Math.sqrt(d * d + e * e), 0.001D);
            attacker.addVelocity( d/f * parryKnockback, 0.5D, e/f * parryKnockback);
            attacker.velocityModified = true;
            shieldParry = 13;
        }
        if (shieldUseDuration > 100) {
            this.disableShield(false);
        }
    }
}
