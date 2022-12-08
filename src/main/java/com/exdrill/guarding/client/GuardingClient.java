package com.exdrill.guarding.client;

import com.exdrill.guarding.Guarding;
import com.exdrill.guarding.client.particle.ParryParticle;
import com.exdrill.guarding.core.registry.GuardingItems;
import com.exdrill.guarding.core.registry.GuardingParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Guarding.MODID, value = Dist.CLIENT)
public class GuardingClient {

    @SubscribeEvent
    public static void registerParticles(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(GuardingParticles.PARRY.get(), ParryParticle.Provider::new);
    }

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(GuardingItems.NETHERITE_SHIELD.get(), new ResourceLocation(Guarding.MODID, "blocking"), ((pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F) );
        });
    }
}
