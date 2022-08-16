package com.exdrill.guarding;

import com.chocohead.mm.api.ClassTinkerers;
import com.exdrill.guarding.config.Config;
import com.exdrill.guarding.registry.GuardingEnchantments;
import com.exdrill.guarding.registry.GuardingParticles;
import com.exdrill.guarding.registry.ModItems;
import com.exdrill.guarding.registry.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Guarding implements ModInitializer {

    public static String NAMESPACE = "guarding";


    public static final Identifier PARRY = new Identifier(NAMESPACE, "parry");
    public static final EnchantmentTarget GUARDING_SHIELD = ClassTinkerers.getEnum(EnchantmentTarget.class, "GUARDING_SHIELD");


    @Override
    public void onInitialize() {

        GuardingParticles.register();
        ModSounds.register();
        GuardingEnchantments.register();
        Config.run();

        // Stats
        Registry.register(Registry.CUSTOM_STAT, new Identifier(NAMESPACE, "parry"), PARRY);
        Stats.CUSTOM.getOrCreateStat(PARRY, StatFormatter.DEFAULT);
    }

    /*
    public void onShieldBlock() {
        ShieldBlockCallback.EVENT.register((defender, source, amount, hand, shield) -> {
            // Living Entity
            LivingEntity attacker = (LivingEntity) source.getAttacker();
            assert attacker != null;

            // Parry
            if (defender instanceof PlayerEntity player && defender.getItemUseTime() <= 10 && defender.isSneaking() && !source.isProjectile()) {
                attacker.playSound(SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.5F, 1.0F);
                ItemStack stack = player.getStackInHand(hand);

                parryKnockbackMultiplier = Config.baseKnockbackValue + ((EnchantmentHelper.getEquipmentLevel(PUMMELING_ENCHANTMENT, defender) * 0.416666667) - Config.baseKnockbackValue);

                // Barbed
                if (ShieldEnchantmentHelper.hasBarbed(defender)) {
                    attacker.damage(DamageSource.MAGIC, Config.barbedDamage);
                    stack.damage(2, attacker, (e) -> e.sendToolBreakStatus(hand));
                }

                // Knockback
                double d = attacker.getX() - defender.getX();
                double e = attacker.getZ() - defender.getZ();
                double f = Math.max(Math.sqrt(d * d + e * e), 0.001D);
                attacker.addVelocity( d/f * parryKnockbackMultiplier, 0.25D, e/f * parryKnockbackMultiplier);
                attacker.velocityModified = true;
                player.addExhaustion(1.0F);
                player.getItemCooldownManager().set(Items.SHIELD, 20);
                defender.clearActiveItem();
                defender.world.sendEntityStatus(defender, (byte) 30);
                defender.setInvulnerable(true);
                stack.damage(1, attacker, (e2) -> e2.sendToolBreakStatus(hand));

            }
            if (defender.getItemUseTime() > 200 && defender instanceof PlayerEntity player) {
                player.getItemCooldownManager().set(Items.SHIELD, 100);
                defender.clearActiveItem();
                defender.world.sendEntityStatus(defender, (byte) 30);
            }

            return ActionResult.success(true);
        });
    }

     */
}
