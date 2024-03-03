package me.imbanana.nexusutils.entity.client.models;

import me.imbanana.nexusutils.entity.custom.NetherBatEntity;
import me.imbanana.nexusutils.entity.custom.SnailEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class NetherBatModel<T extends NetherBatEntity> extends SinglePartEntityModel<T> {
	private final ModelPart bone;
	public NetherBatModel(ModelPart root) {
		this.bone = root.getChild("bone");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -5.0F, -7.0F, 16.0F, 5.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, -1.0F));

		ModelPartData wings = bone.addChild("wings", ModelPartBuilder.create().uv(38, 24).cuboid(8.0F, -5.0F, -6.0F, 10.0F, 1.0F, 17.0F, new Dilation(0.0F))
				.uv(0, 22).cuboid(-18.0F, -5.0F, -6.0F, 10.0F, 1.0F, 17.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 1.0F));

		ModelPartData tail = bone.addChild("tail", ModelPartBuilder.create().uv(0, 41).cuboid(-2.5F, -4.5F, 6.0F, 5.0F, 4.0F, 7.0F, new Dilation(0.0F))
				.uv(25, 43).cuboid(-2.0F, -4.0F, 13.0F, 4.0F, 3.0F, 5.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-1.5F, -3.5F, 18.0F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 1.0F));

		ModelPartData idk_r1 = tail.addChild("idk_r1", ModelPartBuilder.create().uv(0, 22).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.5F, 22.0F, 0.0F, 2.3998F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bone.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return bone;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}
}