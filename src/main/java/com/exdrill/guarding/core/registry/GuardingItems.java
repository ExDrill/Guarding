package com.exdrill.guarding.core.registry;

import com.exdrill.guarding.Guarding;
import com.exdrill.guarding.common.item.NetheriteShieldItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GuardingItems {

    public static final DeferredRegister<Item> HELPER = DeferredRegister.create(ForgeRegistries.ITEMS, Guarding.MODID);

    public static final RegistryObject<Item> NETHERITE_SHIELD = HELPER.register("netherite_shield", () -> new NetheriteShieldItem(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1).durability(612).fireResistant()));
}
