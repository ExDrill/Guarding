package com.exdrill.guarding.registry;

import com.exdrill.guarding.Guarding;
import com.exdrill.guarding.item.NetheriteShieldItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item NETHERITE_SHIELD = register("netherite_shield", new NetheriteShieldItem(new Item.Settings().group(ItemGroup.COMBAT).maxDamage(612).fireproof()));

    public static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Guarding.NAMESPACE, name), item);
    }

    public static void register() {}
}
