package jp.tdn.japanese_food_mod.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class CrabEntityModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer leg;
    private final ModelRenderer leg_sub;
    private final ModelRenderer leg2;
    private final ModelRenderer leg_sub2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg_sub3;
    private final ModelRenderer leg4;
    private final ModelRenderer leg_sub4;
    private final ModelRenderer leg5;
    private final ModelRenderer leg_sub5;
    private final ModelRenderer leg6;
    private final ModelRenderer leg_sub6;
    private final ModelRenderer outline;
    private final ModelRenderer outline2;
    private final ModelRenderer arm;
    private final ModelRenderer s;
    private final ModelRenderer arm2;
    private final ModelRenderer s2;

    public CrabEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 23.0F, 0.0F);
        setRotationAngle(body, -0.2618F, 0.0F, 0.0F);
        body.setTextureOffset(0, 0).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);

        leg = new ModelRenderer(this);
        leg.setRotationPoint(3.0F, -3.0341F, 3.2588F);
        body.addChild(leg);
        setRotationAngle(leg, 0.1745F, 0.0F, 0.0F);
        leg.setTextureOffset(17, 0).addBox(-1.0F, 0.0341F, -0.5078F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        leg_sub = new ModelRenderer(this);
        leg_sub.setRotationPoint(-2.0F, 6.3197F, -2.3914F);
        leg.addChild(leg_sub);
        setRotationAngle(leg_sub, 0.1745F, 0.0F, -0.3491F);
        leg_sub.setTextureOffset(16, 2).addBox(4.5F, -3.8673F, 2.8325F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        leg2 = new ModelRenderer(this);
        leg2.setRotationPoint(-3.0F, -3.0341F, 3.2588F);
        body.addChild(leg2);
        setRotationAngle(leg2, 0.1745F, 0.0F, 0.0F);
        leg2.setTextureOffset(17, 0).addBox(-1.0F, 0.0341F, -0.5078F, 2.0F, 1.0F, 1.0F, 0.0F, true);

        leg_sub2 = new ModelRenderer(this);
        leg_sub2.setRotationPoint(2.0F, 6.3197F, -2.3914F);
        leg2.addChild(leg_sub2);
        setRotationAngle(leg_sub2, 0.1745F, 0.0F, 0.3491F);
        leg_sub2.setTextureOffset(16, 2).addBox(-5.5F, -3.8674F, 2.8835F, 1.0F, 2.0F, 1.0F, 0.0F, true);

        leg3 = new ModelRenderer(this);
        leg3.setRotationPoint(3.0F, -2.0681F, 2.5176F);
        body.addChild(leg3);
        setRotationAngle(leg3, 0.1745F, 0.2618F, 0.0F);
        leg3.setTextureOffset(17, 0).addBox(-1.0F, -0.9319F, -0.4755F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        leg_sub3 = new ModelRenderer(this);
        leg_sub3.setRotationPoint(-0.5811F, 9.3544F, -1.8687F);
        leg3.addChild(leg_sub3);
        setRotationAngle(leg_sub3, 0.1745F, 0.0F, -0.3491F);
        leg_sub3.setTextureOffset(16, 2).addBox(4.2675F, -8.0362F, 2.851F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        leg4 = new ModelRenderer(this);
        leg4.setRotationPoint(-3.0F, -2.0681F, 2.5176F);
        body.addChild(leg4);
        setRotationAngle(leg4, 0.1745F, -0.2618F, 0.0F);
        leg4.setTextureOffset(17, 0).addBox(-1.0681F, -0.9318F, -0.4754F, 2.0F, 1.0F, 1.0F, 0.0F, true);

        leg_sub4 = new ModelRenderer(this);
        leg_sub4.setRotationPoint(0.5811F, 9.3544F, -1.8687F);
        leg4.addChild(leg_sub4);
        setRotationAngle(leg_sub4, 0.1745F, 0.0F, 0.3491F);
        leg_sub4.setTextureOffset(16, 2).addBox(-5.5F, -8.0362F, 2.8835F, 1.0F, 2.0F, 1.0F, 0.0F, true);

        leg5 = new ModelRenderer(this);
        leg5.setRotationPoint(-3.0F, -1.8093F, 1.5517F);
        body.addChild(leg5);
        setRotationAngle(leg5, 0.1745F, -0.2618F, 0.0F);
        leg5.setTextureOffset(17, 0).addBox(-1.3951F, -1.1907F, -0.7171F, 2.0F, 1.0F, 1.0F, 0.0F, true);

        leg_sub5 = new ModelRenderer(this);
        leg_sub5.setRotationPoint(0.124F, 2.4742F, -0.6988F);
        leg5.addChild(leg_sub5);
        setRotationAngle(leg_sub5, 0.0873F, 0.0F, 0.3491F);
        leg_sub5.setTextureOffset(23, 0).addBox(-3.3058F, -2.3882F, 0.083F, 1.0F, 3.0F, 1.0F, 0.0F, true);

        leg6 = new ModelRenderer(this);
        leg6.setRotationPoint(3.0F, -1.8093F, 1.5517F);
        body.addChild(leg6);
        setRotationAngle(leg6, 0.1745F, 0.2618F, 0.0F);
        leg6.setTextureOffset(17, 0).addBox(-0.3461F, -1.1907F, -0.6684F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        leg_sub6 = new ModelRenderer(this);
        leg_sub6.setRotationPoint(0.1348F, 2.3064F, -0.65F);
        leg6.addChild(leg_sub6);
        setRotationAngle(leg_sub6, 0.0873F, 0.0F, -0.3491F);
        leg_sub6.setTextureOffset(23, 0).addBox(2.3058F, -2.3882F, 0.083F, 1.0F, 3.0F, 1.0F, 0.0F, false);

        outline = new ModelRenderer(this);
        outline.setRotationPoint(0.0F, 0.0F, -1.0F);
        body.addChild(outline);
        setRotationAngle(outline, 0.0F, -0.2618F, 0.0F);
        outline.setTextureOffset(0, 9).addBox(1.5F, -4.0F, 0.8F, 2.0F, 2.0F, 3.0F, 0.0F, false);

        outline2 = new ModelRenderer(this);
        outline2.setRotationPoint(0.0F, 0.0F, -1.0F);
        body.addChild(outline2);
        setRotationAngle(outline2, 0.0F, 0.2618F, 0.0F);
        outline2.setTextureOffset(0, 9).addBox(-3.5F, -4.0F, 0.8F, 2.0F, 2.0F, 3.0F, 0.0F, true);

        arm = new ModelRenderer(this);
        arm.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(arm);
        setRotationAngle(arm, 0.0F, 0.2618F, 0.0F);
        arm.setTextureOffset(0, 16).addBox(-3.0F, -2.5F, -1.5176F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        s = new ModelRenderer(this);
        s.setRotationPoint(0.0F, 0.0F, 0.0F);
        arm.addChild(s);
        s.setTextureOffset(0, 14).addBox(-2.5479F, -2.5F, -1.9507F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        arm2 = new ModelRenderer(this);
        arm2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(arm2);
        setRotationAngle(arm2, 0.0F, -0.2618F, 0.0F);
        arm2.setTextureOffset(10, 12).addBox(2.0F, -2.5F, -2.5176F, 1.0F, 1.0F, 3.0F, 0.0F, true);

        s2 = new ModelRenderer(this);
        s2.setRotationPoint(0.7159F, 0.2588F, -1.1918F);
        arm2.addChild(s2);
        s2.setTextureOffset(10, 10).addBox(-0.7159F, -2.9F, -1.9507F, 3.0F, 1.0F, 1.0F, 0.0F, true);
    }

    @Override
    public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        leg.rotateAngleY = MathHelper.sin(limbSwing * 1.5f) * 1.4f * limbSwingAmount;
        leg2.rotateAngleY = MathHelper.sin(limbSwing * 1.4f) * 1.4f * limbSwingAmount;
        leg3.rotateAngleY = MathHelper.sin(limbSwing * 1.35f) * 1.4f * limbSwingAmount;
        leg4.rotateAngleY = MathHelper.sin(limbSwing * 1.3f) * 1.4f * limbSwingAmount;
        leg5.rotateAngleY = MathHelper.sin(limbSwing * 1.2f) * 1.4f * limbSwingAmount;
        leg6.rotateAngleY = MathHelper.sin(limbSwing * 1.15f) * 1.4f * limbSwingAmount;

        arm.rotateAngleX = MathHelper.sin(ageInTicks * 0.3f) * 0.3f;
        arm2.rotateAngleX = -MathHelper.sin(ageInTicks * 0.2f) * 0.3f;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
