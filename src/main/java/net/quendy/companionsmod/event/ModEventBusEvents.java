package net.quendy.companionsmod.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.quendy.companionsmod.CompanionsMod;
import net.quendy.companionsmod.entity.ModEntities;
import net.quendy.companionsmod.entity.custom.GlaiveMaidenEntity;

@Mod.EventBusSubscriber(modid = CompanionsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GLAIVE_MAIDEN.get(), GlaiveMaidenEntity.createAttributes().build());
    }
}