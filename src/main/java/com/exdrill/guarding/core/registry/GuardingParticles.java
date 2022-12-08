package com.exdrill.guarding.core.registry;

import com.exdrill.guarding.Guarding;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GuardingParticles {

    public static final DeferredRegister<ParticleType<?>> HELPER = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Guarding.MODID);

    public static final RegistryObject<SimpleParticleType> PARRY = HELPER.register("parry", () -> new SimpleParticleType(false));
}
