package com.exdrill.guarding.mixin;

import com.exdrill.guarding.enchantment.ShieldEnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow protected abstract void damageShield(float amount);

    @Shadow public abstract void disableShield(boolean sprinting);

    @Mutable
    public int shieldUseDuration = 0;
    public int shieldParry = 0;
    public double parryKnockbackMultiplier = 0D;

    PlayerEntity player = (PlayerEntity) (Object) this;

    public void parryCooldown() {
        player.getItemCooldownManager().set(Items.SHIELD, 20);
        player.clearActiveItem();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (player.isBlocking()) {
            shieldUseDuration++;
            shieldParry++;
        } else if(shieldUseDuration != 0 && !player.isBlocking()) {
            shieldUseDuration--;
            shieldParry = 0;
        }
        if (ShieldEnchantmentHelper.hasPummeling(player)) {
            parryKnockbackMultiplier = 1.25D;
        } else {
            parryKnockbackMultiplier = 0.5D;
        }
    }

    @Inject(method = "takeShieldHit", at = @At("HEAD"))
    private void takeShieldHit(LivingEntity attacker, CallbackInfo ci) {
        if (shieldParry <= 6 && shieldParry >= 1 && player.isSneaking()) {
            attacker.playSound(SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.5F, 1.0F);
            if (ShieldEnchantmentHelper.hasBarbed(player)) {
                attacker.damage(DamageSource.MAGIC, 4.0F);
                this.damageShield(2.0F);
            }
            double d = attacker.getX() - player.getX();
            double e = attacker.getZ() - player.getZ();
            double f = Math.max(Math.sqrt(d * d + e * e), 0.001D);
            attacker.addVelocity( d/f * parryKnockbackMultiplier, 0.25D, e/f * parryKnockbackMultiplier);
            attacker.velocityModified = true;
            player.addExhaustion(0.5F);
            shieldParry = 13;
            this.parryCooldown();
        }
        if (shieldUseDuration > 100) {
            player.disableShield(false);
            shieldUseDuration = 0;
        }


    }
}
