package net.quendy.companionsmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.quendy.companionsmod.CompanionsMod;
import net.quendy.companionsmod.entity.custom.GlaiveMaidenEntity;

public class GlaiveMaidenRenderer extends MobRenderer<GlaiveMaidenEntity, GlaiveMaidenModel<GlaiveMaidenEntity>> {
    private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation("companionsmod", "textures/entity/glaive_maiden/default.png");
    private static final ResourceLocation APRON_TEXTURE = new ResourceLocation("companionsmod", "textures/entity/glaive_maiden/apron.png");
    private static final ResourceLocation CROWN_TEXTURE = new ResourceLocation("companionsmod", "textures/entity/glaive_maiden/crown.png");

    public GlaiveMaidenRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GlaiveMaidenModel<>(pContext.bakeLayer(ModModelLayers.GLAIVE_MAIDEN_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(GlaiveMaidenEntity glaiveMaidenEntity) {
        int skinType = glaiveMaidenEntity.getSkinType();
        switch (skinType) {
            case 1:
                return APRON_TEXTURE;
            case 2:
                return CROWN_TEXTURE;
            default:
                return DEFAULT_TEXTURE;
        }
    }

    @Override
    public void render(GlaiveMaidenEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pPoseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}