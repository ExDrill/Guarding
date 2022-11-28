package com.exdrill.guarding.util;

import com.exdrill.guarding.Guarding;
import com.exdrill.guarding.config.Config;
import com.exdrill.guarding.registry.GuardingEnchantments;
import com.exdrill.guarding.registry.GuardingParticles;
import com.exdrill.guarding.registry.ModSounds;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ShieldUtil {

    public static void onParry(Entity entity, DamageSource source) {
        if (entity instanceof PlayerEntity player) {
            World world = player.getWorld();
            Entity sourceEntity = source.getSource();
            int useDuration = player.getActiveItem().getMaxUseTime() - player.getItemUseTimeLeft();

            if (useDuration > Guarding.config.parryTickRange()) return;
            if (!player.isBlocking()) return;
            if (sourceEntity == null) return;
            if (Guarding.config.requireSneak() && !player.isSneaking()) return;

            if (source.isProjectile()) {
                ProjectileEntity projectile = (ProjectileEntity) sourceEntity;
            }

            if (source.getSource() instanceof LivingEntity attacker) {
                parryEntityAttack(player, attacker);
            }
            parryEffects(player, sourceEntity, world);
            player.increaseStat(Guarding.PARRY, 1);

            if (Config.run().applyCooldown()) {
                disableShield(player, 100);
            }
        }
    }

    public static void parryEntityAttack(LivingEntity defender, LivingEntity attacker) {
        ItemStack itemStack = defender.getActiveItem();

        attacker.takeKnockback(Guarding.config.parryKnockback() + getPummelKnockback(itemStack), defender.getX() - attacker.getX(), defender.getZ() - attacker.getZ());
        attacker.velocityModified = true;

        if (hasBarbed(itemStack)) {
            attacker.damage(DamageSource.thorns(defender), Guarding.config.barbedDamage() * 1.0F);
        }
    }

    private static void parryEffects(PlayerEntity player, Entity attacker, World world) {
        Random random = world.getRandom();
        world.playSound(null, player.getBlockPos(), ModSounds.ITEM_SHIELD_PARRY, SoundCategory.PLAYERS, 1.0F, random.nextFloat() * 0.2F + 0.9F);
        if (world instanceof ServerWorld server) {
            server.spawnParticles(GuardingParticles.PARRY, attacker.getX(), attacker.getEyeY(), attacker.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private static boolean hasBarbed(ItemStack itemStack) {
        return EnchantmentHelper.getLevel(GuardingEnchantments.BARBED_ENCHANTMENT, itemStack) > 0;
    }

    public static float getPummelKnockback(ItemStack itemStack) {
        int pummelLevel = EnchantmentHelper.getLevel(GuardingEnchantments.PUMMELING_ENCHANTMENT, itemStack);
        return pummelLevel > 0 ? pummelLevel * 0.15F : 0;
    }

    public static void onShieldDisable(LivingEntity entity, boolean sprinting) {
        if (entity instanceof PlayerEntity player) {
            float f = 0.25F + (float) EnchantmentHelper.getEfficiency(player) * 0.05F;
            if (sprinting) {
                f += 0.75F;
            }
            if (player.getRandom().nextFloat() < f && player.getActiveItem().getItem() instanceof ShieldItem shieldItem) {
                player.getItemCooldownManager().set(shieldItem, 100);
                player.clearActiveItem();
                player.world.sendEntityStatus(player, (byte)30);
            }
        }
    }

    private static void disableShield(PlayerEntity player, int duration) {
        if (player.getActiveItem().getItem() instanceof ShieldItem shieldItem) {
            player.clearActiveItem();
            player.getItemCooldownManager().set(shieldItem, duration);
        }
    }
}
