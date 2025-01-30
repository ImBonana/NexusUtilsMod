package me.imbanana.nexusutils.entity.client.models;

import me.imbanana.nexusutils.entity.custom.SnailEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ModelTransformationMode;

import java.util.Optional;

// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class SnailModel extends EntityModel<LivingEntityRenderState> {

	public SnailModel(ModelPart root) {
        super(root.getChild("snail"));
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData snail = modelPartData.addChild("snail", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		snail.addChild("bone", ModelPartBuilder.create().uv(0, 13).cuboid(1.0F, -2.0F, -1.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 9).cuboid(-5.0F, -1.0F, -1.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		 snail.addChild("shell", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -6.0F, -1.5F, 5.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData eyes = snail.addChild("eyes", ModelPartBuilder.create(), ModelTransform.pivot(4.5F, -2.0F, 0.0F));

		eyes.addChild("cube_r1", ModelPartBuilder.create().uv(13, 13).cuboid(0.0F, -2.0F, -0.5F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -1.0F, 1.5708F, 1.0472F, 1.5708F));

		eyes.addChild("cube_r2", ModelPartBuilder.create().uv(16, 12).cuboid(0.0F, -2.0F, -0.5F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 1.0F, -1.5708F, -1.0472F, 1.5708F));
		return TexturedModelData.of(modelData, 32, 32);
	}
}