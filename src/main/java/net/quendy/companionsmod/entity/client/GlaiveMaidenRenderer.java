package net.quendy.companionsmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.quendy.companionsmod.CompanionsMod;
import net.quendy.companionsmod.entity.custom.GlaiveMaidenEntity;

public class GlaiveMaidenRenderer extends MobRenderer<GlaiveMaidenEntity, GlaiveMaidenModel<GlaiveMaidenEntity>> {
    public GlaiveMaidenRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GlaiveMaidenModel<>(pContext.bakeLayer(ModModelLayers.GLAIVE_MAIDEN_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(GlaiveMaidenEntity pEntity) {
        return new ResourceLocation(CompanionsMod.MOD_ID, "textures/entity/glaive_maiden.png");
    }

    @Override
    public void render(GlaiveMaidenEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
