package com.exdrill.guarding.core.util;

import com.exdrill.guarding.core.config.GuardingConfig;
import com.exdrill.guarding.core.registry.GuardingEnchantments;
import com.exdrill.guarding.core.registry.GuardingParticles;
import com.exdrill.guarding.core.registry.GuardingSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class ShieldUtil {

    public static boolean onParry(Entity entity, DamageSource source) {
        if (entity instanceof Player player) {
            Level world = player.getLevel();
            Entity sourceEntity = source.getDirectEntity();
            int useDuration = player.getUseItem().getUseDuration() - player.getUseItemRemainingTicks();

            if (useDuration > GuardingConfig.PARRY_TICK_RANGE.get()) return false;
            if (!player.isBlocking()) return false;
            if (sourceEntity == null) return false;
            if (GuardingConfig.REQUIRE_SNEAK.get() && !player.isCrouching()) return false;

            if (source.isProjectile()) {
                Projectile projectile = (Projectile) sourceEntity;
            }

            if (sourceEntity instanceof LivingEntity attacker) {
                parryEntityAttack(player, attacker);
            }
            parryEffects(player, sourceEntity, world);
            //player.awardStat(Guarding.PARRY, 1);

            if (GuardingConfig.APPLY_COOLDOWN.get()) {
                disableShield(player, 100);
            }
            return true;
        }
        return false;
    }

    public static void parryEntityAttack(LivingEntity defender, LivingEntity attacker) {
        ItemStack itemStack = defender.getUseItem();

        attacker.knockback(GuardingConfig.PARRY_KNOCKBACK.get() + getPummelKnockback(itemStack), defender.getX() - attacker.getX(), defender.getZ() - attacker.getZ());
        attacker.hurtMarked = true;

        if (hasBarbed(itemStack)) {
            attacker.hurt(DamageSource.thorns(defender), GuardingConfig.BARBED_DAMAGE.get() * 1.0F);
        }
    }

    private static void parryEffects(Player player, Entity attacker, Level world) {
        RandomSource random = world.getRandom();
        world.playSound(null, player.blockPosition(), GuardingSoundEvents.ITEM_SHIELD_PARRY.get(), SoundSource.PLAYERS, 1.0F, random.nextFloat() * 0.2F + 0.9F);
        if (world instanceof ServerLevel server) {
            server.sendParticles(GuardingParticles.PARRY.get(), attacker.getX(), attacker.getEyeY(), attacker.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private static boolean hasBarbed(ItemStack itemStack) {
        return EnchantmentHelper.getItemEnchantmentLevel(GuardingEnchantments.BARBED.get(), itemStack) > 0;
    }

    public static float getPummelKnockback(ItemStack itemStack) {
        int pummelLevel = EnchantmentHelper.getItemEnchantmentLevel(GuardingEnchantments.PUMMELING.get(), itemStack);
        return pummelLevel > 0 ? pummelLevel * 0.15F : 0;
    }

    public static void onShieldDisable(LivingEntity entity, boolean sprinting) {
        if (entity instanceof Player player) {
            float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(player) * 0.05F;
            if (sprinting) {
                f += 0.75F;
            }
            if (player.getRandom().nextFloat() < f && player.getUseItem().getItem() instanceof ShieldItem shieldItem) {
                player.getCooldowns().addCooldown(shieldItem, 100);
                player.stopUsingItem();
                player.level.broadcastEntityEvent(player, (byte)30);
            }
        }
    }

    private static void disableShield(Player player, int duration) {
        if (player.getUseItem().getItem() instanceof ShieldItem shieldItem) {
            player.stopUsingItem();
            player.getCooldowns().addCooldown(shieldItem, duration);
        }
    }
}
