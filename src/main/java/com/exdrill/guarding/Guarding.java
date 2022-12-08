package com.exdrill.guarding;

import com.exdrill.guarding.common.conditions.ExperimentalFeaturesCondition;
import com.exdrill.guarding.core.config.GuardingConfig;
import com.exdrill.guarding.core.registry.GuardingEnchantments;
import com.exdrill.guarding.core.registry.GuardingItems;
import com.exdrill.guarding.core.registry.GuardingParticles;
import com.exdrill.guarding.core.registry.GuardingSoundEvents;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Guarding.MODID)
public class Guarding {
    public static final String MODID = "guarding";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final EnchantmentCategory GUARDING_SHIELD = EnchantmentCategory.create("guarding_shield", (item) -> item instanceof ShieldItem);

    public Guarding() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::commonSetup);


        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GuardingConfig.SPEC, "guarding.toml");
        CraftingHelper.register(new ExperimentalFeaturesCondition.Serializer());


        GuardingParticles.HELPER.register(bus);
        GuardingEnchantments.HELPER.register(bus);
        GuardingSoundEvents.HELPER.register(bus);
        GuardingItems.HELPER.register(bus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            if (GuardingConfig.ENABLE_EXPERIMENTAL_FEATURES.get()) {
                LOGGER.info("Enabling experimental features on Guarding");
            }
        });
    }

}
