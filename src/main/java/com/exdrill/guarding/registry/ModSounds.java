package com.exdrill.guarding.registry;

import com.exdrill.guarding.Guarding;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {

    public static void register() {}

    public static SoundEvent register(String id) {
        Identifier identifier = new Identifier(Guarding.MODID, id);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }

    public static final SoundEvent ITEM_SHIELD_PARRY = register("item.shield.parry");
}
