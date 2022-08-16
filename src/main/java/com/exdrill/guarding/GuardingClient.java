package com.exdrill.guarding;

import com.exdrill.guarding.registry.GuardingParticles;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class GuardingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GuardingParticles.registerFactories();
        ModelPredicateProviderRegistry.register(new Identifier(Guarding.NAMESPACE, "blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
    }
}
