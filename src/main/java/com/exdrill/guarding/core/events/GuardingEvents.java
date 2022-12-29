package com.exdrill.guarding.core.events;

import com.exdrill.guarding.Guarding;
import com.exdrill.guarding.core.util.ShieldUtil;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Guarding.MODID)
public class GuardingEvents {

    @SubscribeEvent
    public static void onShieldBlock(ShieldBlockEvent event) {
        if (ShieldUtil.onParry(event.getEntity(), event.getDamageSource())) {
            event.setShieldTakesDamage(true);
        }
    }
}
