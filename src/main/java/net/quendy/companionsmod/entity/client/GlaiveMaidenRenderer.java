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
        super(pContext, new GlaiveMaidenModel<>(pContext.bakeLayer(ModModelLayers.GLAIVE_MAIDEN_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(GlaiveMaidenEntity glaiveMaidenEntity) {
        return new ResourceLocation(CompanionsMod.MOD_ID, "textures/entity/glaive_maiden_crown.png");
    }

    @Override
    public void render(GlaiveMaidenEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pPoseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}