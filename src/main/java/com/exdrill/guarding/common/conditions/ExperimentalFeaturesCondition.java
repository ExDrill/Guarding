package com.exdrill.guarding.common.conditions;

import com.exdrill.guarding.Guarding;
import com.exdrill.guarding.core.config.GuardingConfig;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class ExperimentalFeaturesCondition implements ICondition {

    private static final ResourceLocation NAME = new ResourceLocation(Guarding.MODID, "experimental");

    public ResourceLocation getID() {
        return NAME;
    }

    public boolean test(IContext context) {
        return GuardingConfig.ENABLE_EXPERIMENTAL_FEATURES.get();
    }

    public static class Serializer implements IConditionSerializer<ExperimentalFeaturesCondition> {

        public void write(JsonObject json, ExperimentalFeaturesCondition value) {

        }

        public ExperimentalFeaturesCondition read(JsonObject json) {
            return new ExperimentalFeaturesCondition();
        }

        public ResourceLocation getID() {
            return ExperimentalFeaturesCondition.NAME;
        }
    }
}
