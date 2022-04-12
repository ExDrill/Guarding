package com.exdrill.guarding;

import com.exdrill.guarding.enchantment.BarbedEnchantment;
import com.exdrill.guarding.enchantment.PummelingEnchantment;
import com.exdrill.guarding.enchantment.ShieldEnchantmentHelper;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldBlockCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldDisabledCallback;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Guarding implements ModInitializer {
    public static String NAMESPACE = "guarding";


    public static final PummelingEnchantment PUMMELING_ENCHANTMENT = new PummelingEnchantment(Enchantment.Rarity.COMMON, false, false);
    public static final BarbedEnchantment BARBED_ENCHANTMENT = new BarbedEnchantment(Enchantment.Rarity.RARE, false, false);


    public static final Identifier PARRY = new Identifier(NAMESPACE, "parry");

    public double parryKnockbackMultiplier = 0D;

    @Override
    public void onInitialize() {
        System.out.println("Guarding is loaded");
        // Enchantments
        Registry.register(Registry.ENCHANTMENT, new Identifier(NAMESPACE, "pummeling"), PUMMELING_ENCHANTMENT);
        Registry.register(Registry.ENCHANTMENT, new Identifier(NAMESPACE, "barbed"), BARBED_ENCHANTMENT);

        // Stats
        Registry.register(Registry.CUSTOM_STAT, new Identifier(NAMESPACE, "parry"), PARRY);
        Stats.CUSTOM.getOrCreateStat(PARRY, StatFormatter.DEFAULT);


        // Shield Parrying
        ShieldBlockCallback.EVENT.register((defender, source, amount, hand, shield) -> {
            // Living Entity
            LivingEntity attacker = (LivingEntity) source.getAttacker();
            assert attacker != null;

            // Parry
            if (defender instanceof PlayerEntity player && defender.getItemUseTime() <= 10 && defender.isSneaking() && !source.isProjectile()) {
                attacker.playSound(SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.5F, 1.0F);

                // Pummeling
                if (ShieldEnchantmentHelper.hasPummeling(defender)) {
                    parryKnockbackMultiplier = 1.25D;
                } else {
                    parryKnockbackMultiplier = 0.5D;
                }

                // Barbed
                if (ShieldEnchantmentHelper.hasBarbed(defender)) {
                    attacker.damage(DamageSource.MAGIC, 4.0F);
                }

                // Knockback
                double d = attacker.getX() - defender.getX();
                double e = attacker.getZ() - defender.getZ();
                double f = Math.max(Math.sqrt(d * d + e * e), 0.001D);
                attacker.addVelocity( d/f * parryKnockbackMultiplier, 0.25D, e/f * parryKnockbackMultiplier);
                attacker.velocityModified = true;
                ((PlayerEntity) defender).addExhaustion(1.0F);
                ((PlayerEntity) defender).getItemCooldownManager().set(Items.SHIELD, 20);
                defender.clearActiveItem();
                defender.world.sendEntityStatus(defender, (byte) 30);
                defender.setInvulnerable(true);

            }
            if (defender.getItemUseTime() > 200) {
                assert defender instanceof PlayerEntity;
                ((PlayerEntity) defender).getItemCooldownManager().set(Items.SHIELD, 100);
                defender.clearActiveItem();
                defender.world.sendEntityStatus(defender, (byte) 30);
            }

            return ActionResult.success(true);
        });

    }
}
