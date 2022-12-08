package com.exdrill.guarding.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GuardingConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Float> PARRY_KNOCKBACK;
    public static final ForgeConfigSpec.ConfigValue<Float> PARRY_EXHAUSTION;
    public static final ForgeConfigSpec.ConfigValue<Integer> PARRY_TICK_RANGE;
    public static final ForgeConfigSpec.ConfigValue<Integer> BARBED_DAMAGE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> REQUIRE_SNEAK;
    public static final ForgeConfigSpec.ConfigValue<Boolean> APPLY_COOLDOWN;
    public static final ForgeConfigSpec.ConfigValue<Boolean> PUNISH_SHIELD_HUGGING;
    public static final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_EXPERIMENTAL_FEATURES;

    static {
        BUILDER.push("Guarding Configurations");
        PARRY_KNOCKBACK = BUILDER.comment("Changes the value of the base amount of knockback parries provide.").define("parry_knockback", 0.5F);
        PARRY_EXHAUSTION = BUILDER.comment("Changes the value of the amount of exhaustion applied to the player when a parry is performed.").define("parry_exhaustion", 5F);
        PARRY_TICK_RANGE = BUILDER.comment("Changes the value of the time needed to perform a parry, measured in ticks.").define("parry_tick_range", 2);
        BARBED_DAMAGE = BUILDER.comment("Changes the value of the amount of damage the barbed enchantment does to it's target.").define("barbed_damage", 3);
        REQUIRE_SNEAK = BUILDER.define("require_sneak", false);
        APPLY_COOLDOWN = BUILDER.define("apply_cooldown", false);
        PUNISH_SHIELD_HUGGING = BUILDER.define("punish_shield_hugging", false);
        ENABLE_EXPERIMENTAL_FEATURES = BUILDER.define("enable_experimental_features", false);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
