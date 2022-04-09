package com.exdrill.guarding.mixin;

import com.exdrill.guarding.enchantment.ShieldEnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
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

    @Shadow public abstract void disableShield(boolean sprinting);

    public void parryCooldown() {
        this.getItemCooldownManager().set(Items.SHIELD, 20);
    }

    @Shadow protected abstract void damageShield(float amount);

    @Shadow public abstract ItemCooldownManager getItemCooldownManager();

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

    @Inject(method = "takeShieldHit", at = @At("HEAD"))
    private void takeShieldHit(LivingEntity attacker, CallbackInfo ci) {
        System.out.println(parryKnockback);
        if (shieldParry <= 8 && shieldParry >= 1 && player.isSneaking()) {
            attacker.playSound(SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.5F, 1.0F);
            if (ShieldEnchantmentHelper.hasBarbed(player)) {
                attacker.damage(DamageSource.GENERIC, 4.0F);
                this.damageShield(2.0F);
            }
            double d = attacker.getX() - player.getX();
            double e = attacker.getZ() - player.getZ();
            double f = Math.max(Math.sqrt(d * d + e * e), 0.001D);
            attacker.addVelocity( d/f * parryKnockback, 0.2D, e/f * parryKnockback);
            attacker.velocityModified = true;
            player.addExhaustion(0.2F);
            shieldParry = 13;
            this.parryCooldown();
        }
        if (shieldUseDuration > 100) {
            this.disableShield(false);
        }
    }
}
