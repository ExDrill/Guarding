package com.exdrill.guarding.util;

import com.exdrill.guarding.Guarding;
import com.exdrill.guarding.config.Config;
import com.exdrill.guarding.registry.GuardingEnchantments;
import com.exdrill.guarding.registry.GuardingParticles;
import com.exdrill.guarding.registry.ModSounds;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ShieldUtil {

    public static void onShieldHit(LivingEntity defender, LivingEntity attacker) {
        World world = defender.getWorld();
        Random random = world.getRandom();
        int useDuration = defender.getActiveItem().getMaxUseTime() - defender.getItemUseTimeLeft();
        ItemStack itemStack = defender.getActiveItem();

        if (defender instanceof PlayerEntity player) {
            if (useDuration <= Guarding.config.parryTickRange() && defender.isBlocking()) {

                if (Guarding.config.requireSneak() && !player.isSneaking()) return;

                attacker.takeKnockback(Guarding.config.parryKnockback() + getPummelKnockback(itemStack), player.getX() - attacker.getX(), player.getZ() - attacker.getZ());
                attacker.velocityModified = true;

                if (hasBarbed(itemStack)) {
                    attacker.damage(DamageSource.thorns(defender), Guarding.config.barbedDamage() * 1.0F);
                }

                player.increaseStat(Guarding.PARRY, 1);
                world.playSound(null, defender.getBlockPos(), ModSounds.ITEM_SHIELD_PARRY, SoundCategory.PLAYERS, 1.0F, random.nextFloat() * 0.2F + 0.9F);
                if (world instanceof ServerWorld server) {
                    server.spawnParticles(GuardingParticles.PARRY, attacker.getX(), attacker.getEyeY(), attacker.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
                player.addExhaustion(Guarding.config.parryExhaustion());

                if (Config.run().applyCooldown()) {
                    disableShield(player, 100);
                }
                itemStack.damage(getDamageOnHit(itemStack), defender, (entity -> entity.sendToolBreakStatus(defender.getActiveHand())));
            }
            if (useDuration >= 200 && Guarding.config.shieldHuggingPunishment()) {
                disableShield(player, 200);
            }
        }
    }

    public static void onShieldDisable(LivingEntity entity, boolean sprinting) {
        if (entity instanceof PlayerEntity player) {
            float f = 0.25F + (float)EnchantmentHelper.getEfficiency(player) * 0.05F;
            if (sprinting) {
                f += 0.75F;
            }
            if (player.getRandom().nextFloat() < f) {
                if (player.getActiveItem().getItem() instanceof ShieldItem shieldItem && player.getActiveItem().getItem() != Items.SHIELD) {
                    player.getItemCooldownManager().set(shieldItem, 100);
                    player.clearActiveItem();
                    player.world.sendEntityStatus(player, (byte)30);
                }
            }
        }
    }

    private static void disableShield(PlayerEntity player, int duration) {
        if (player.getActiveItem().getItem() instanceof ShieldItem shieldItem) {
            player.clearActiveItem();
            player.getItemCooldownManager().set(shieldItem, duration);
        }
    }

    private static boolean hasBarbed(ItemStack itemStack) {
        return EnchantmentHelper.getLevel(GuardingEnchantments.BARBED_ENCHANTMENT, itemStack) > 0;
    }

    public static float getPummelKnockback(ItemStack itemStack) {
        int pummelLevel = EnchantmentHelper.getLevel(GuardingEnchantments.PUMMELING_ENCHANTMENT, itemStack);
        return pummelLevel > 0 ? pummelLevel * 0.15F : 0;
    }

    public static int getDamageOnHit(ItemStack itemStack) {
        return hasBarbed(itemStack) ? 2 : 1;
    }
}
