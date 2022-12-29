package com.exdrill.guarding.core.registry;

import com.exdrill.guarding.Guarding;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GuardingSoundEvents {

    public static final DeferredRegister<SoundEvent> HELPER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Guarding.MODID);

    public static final RegistryObject<SoundEvent> ITEM_SHIELD_PARRY = register("item.shield.parry");


    public static RegistryObject<SoundEvent> register(String id) {
        return HELPER.register(id, () -> new SoundEvent(new ResourceLocation(Guarding.MODID, id)));
    }
}
