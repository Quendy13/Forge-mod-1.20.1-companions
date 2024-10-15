package net.quendy.companionsmod.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.quendy.companionsmod.CompanionsMod;
import net.quendy.companionsmod.entity.client.GlaiveMaidenModel;
import net.quendy.companionsmod.entity.client.ModModelLayers;

@Mod.EventBusSubscriber(modid = CompanionsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.GLAIVE_MAIDEN_LAYER, GlaiveMaidenModel::createBodyLayer);
    }
}