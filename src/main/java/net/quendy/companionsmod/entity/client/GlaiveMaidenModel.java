package net.quendy.companionsmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.quendy.companionsmod.entity.custom.GlaiveMaidenEntity;
import net.quendy.companionsmod.entity.animations.ModAnimationDefinitions;

public class GlaiveMaidenModel<T extends GlaiveMaidenEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "glaive_maiden"), "main");
    private final ModelPart glaive_maiden;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_arm;

    public GlaiveMaidenModel(ModelPart root) {
        this.glaive_maiden = root.getChild("glaive_maiden");
        this.head = this.glaive_maiden.getChild("head");
        this.body = this.glaive_maiden.getChild("body");
        this.right_arm = this.glaive_maiden.getChild("body").getChild("right_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition glaive_maiden = partdefinition.addOrReplaceChild("glaive_maiden", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, -0.5F));

        PartDefinition head = glaive_maiden.addOrReplaceChild("head", CubeListBuilder.create().texOffs(34, 20).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, 18.5F, -4.5F, 9.0F, 11.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(34, 36).addBox(-3.0F, 17.0F, 4.4F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 36).addBox(-3.5F, 15.0F, -3.5F, 7.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -27.0F, 0.0F));

        PartDefinition body = glaive_maiden.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 36).addBox(-3.0F, 0.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm = arms.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 45).addBox(0.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 41).addBox(-0.5F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition right_arm = arms.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(8, 45).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 41).addBox(-2.5F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition legs = body.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(36, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 0.0F, 0.0F));

        PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(36, 10).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 0.0F, 0.0F));

        PartDefinition dress = body.addOrReplaceChild("dress", CubeListBuilder.create().texOffs(0, 20).addBox(-4.5F, 0.0F, -4.0F, 9.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(18, 41).addBox(-3.0F, -1.5F, 3.9F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animateWalk(ModAnimationDefinitions.GLAIVE_MAIDEN_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(((GlaiveMaidenEntity) entity).idleAnimationState, ModAnimationDefinitions.GLAIVE_MAIDEN_IDLE, ageInTicks, 1f);
        this.animate(((GlaiveMaidenEntity) entity).attackAnimationState, ModAnimationDefinitions.GLAIVE_MAIDEN_ATTACK, ageInTicks, 1f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        glaive_maiden.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return null;
    }
}
