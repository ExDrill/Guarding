package com.exdrill.guarding.registry;

import com.exdrill.guarding.Guarding;
import com.exdrill.guarding.particle.ParryParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GuardingParticles {

    public static final DefaultParticleType PARRY = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Guarding.MODID, "parry"), PARRY);
    }

    public static void registerFactories() {
        ParticleFactoryRegistry.getInstance().register(PARRY, ParryParticle.Factory::new);
    }
}
