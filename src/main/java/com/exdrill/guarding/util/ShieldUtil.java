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
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ShieldUtil {

    public static void onShieldHit(LivingEntity defender, LivingEntity attacker) {
        World world = defender.getWorld();
        Random random = world.getRandom();
        int useDuration = defender.getItemUseTime();
        ItemStack itemStack = defender.getActiveItem();

        if (defender instanceof PlayerEntity player) {
            if (useDuration <= 5 && defender.isSneaking() && !(attacker.getMainHandStack().getItem() instanceof AxeItem)) {

                attacker.takeKnockback(Config.baseKnockbackValue + getPummelKnockback(itemStack), player.getX() - attacker.getX(), player.getZ() - attacker.getZ());
                attacker.velocityModified = true;

                if (hasBarbed(itemStack)) {
                    attacker.damage(DamageSource.thorns(defender), Config.barbedDamage * 1.0F);
                }

                player.increaseStat(Guarding.PARRY, 1);
                world.playSound(null, defender.getBlockPos(), ModSounds.ITEM_SHIELD_PARRY, SoundCategory.PLAYERS, 1.0F, random.nextFloat() * 0.2F + 0.9F);
                if (world instanceof ServerWorld server) {
                    server.spawnParticles(GuardingParticles.PARRY, attacker.getX(), attacker.getEyeY(), attacker.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
                disableShield(player, 40);
                itemStack.damage(getDamageOnHit(itemStack), defender, (entity -> entity.sendToolBreakStatus(defender.getActiveHand())));
            }
            if (useDuration >= 200 && Config.enableShieldHuggingPunishment) {
                disableShield(player, 200);
            }
        }
    }

    private static void disableShield(PlayerEntity player, int duration) {
        player.clearActiveItem();
        player.getItemCooldownManager().set(Items.SHIELD, duration);
    }

    private static boolean hasBarbed(ItemStack itemStack) {
        return EnchantmentHelper.getLevel(GuardingEnchantments.BARBED_ENCHANTMENT, itemStack) > 0;
    }

    public static float getPummelKnockback(ItemStack itemStack) {
        int pummelLevel = EnchantmentHelper.getLevel(GuardingEnchantments.PUMMELING_ENCHANTMENT, itemStack);
        return pummelLevel > 0 ? pummelLevel * 0.1F : 0;
    }

    public static int getDamageOnHit(ItemStack itemStack) {
        return hasBarbed(itemStack) ? 2 : 1;
    }
}
