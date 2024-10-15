package net.quendy.companionsmod.entity.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.quendy.companionsmod.CompanionsMod;

public class ModModelLayers {
    public static final ModelLayerLocation GLAIVE_MAIDEN_LAYER =
            new ModelLayerLocation(new ResourceLocation(CompanionsMod.MOD_ID, "glaive_maiden_layer"), "main");

    public static void registerLayers(EntityRendererProvider.Context context) {
    }
}
