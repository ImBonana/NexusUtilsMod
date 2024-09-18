package me.imbanana.nexusutils.item.backpack;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BackpackEntityModel extends Model {
	public static final Identifier TEXTURE = NexusUtils.idOf("textures/entity/backpack.png");

	private final ModelPart backpack;
	private final ModelPart sleepingBag;

	public BackpackEntityModel(ModelPart root) {
		super(RenderLayer::getEntitySolid);
		this.backpack = root.getChild("backpack");
		this.sleepingBag = root.getChild("sleeping_bag");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData backpack = modelPartData.addChild("backpack", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 7.0F, new Dilation(0.0F))
				.uv(19, 21).cuboid(-3.0F, -10.0F, -3.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData front = backpack.addChild("front", ModelPartBuilder.create().uv(24, 0).cuboid(-3.0F, -7.0F, 3.0F, 6.0F, 4.0F, 1.0F, new Dilation(0.0F))
				.uv(19, 31).cuboid(-2.0F, -6.0F, 3.2F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		front.addChild("bone", ModelPartBuilder.create().uv(13, 33).cuboid(-1.0F, -5.0F, 3.2F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(19, 31).cuboid(1.0F, -6.0F, 3.2F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData sleeping_bag = backpack.addChild("sleeping_bag", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -1.0F));

		sleeping_bag.addChild("left2", ModelPartBuilder.create().uv(31, 6).cuboid(3.0F, -4.0F, 4.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 21).cuboid(3.0F, -1.0F, 3.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(2.0F, -3.0F, 6.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
				.uv(10, 29).cuboid(2.0F, -1.0F, 4.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		sleeping_bag.addChild("left3", ModelPartBuilder.create().uv(31, 6).cuboid(3.0F, -4.0F, 4.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 21).cuboid(3.0F, -1.0F, 3.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(3.0F, -3.0F, 6.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
				.uv(28, 25).cuboid(3.0F, -1.0F, 4.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, 0.0F, 0.0F));

		backpack.addChild("bottom", ModelPartBuilder.create().uv(21, 25).cuboid(3.0F, -1.0F, -3.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 21).cuboid(-3.0F, -1.0F, -4.0F, 6.0F, 1.0F, 6.0F, new Dilation(0.0F))
				.uv(27, 12).cuboid(-4.0F, -1.0F, -3.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		backpack.addChild("left_side", ModelPartBuilder.create().uv(0, 29).cuboid(3.5F, -6.0F, -3.0F, 1.0F, 4.0F, 5.0F, new Dilation(0.0F))
				.uv(0, 30).cuboid(3.7F, -5.5F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		backpack.addChild("right_side", ModelPartBuilder.create().uv(0, 29).cuboid(-1.5F, -6.0F, -3.0F, 1.0F, 4.0F, 5.0F, new Dilation(0.0F))
				.uv(0, 30).cuboid(-1.3F, -5.5F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 0.0F, -1.0F, 0.0F, 3.1416F, 0.0F));

		modelPartData.addChild("sleeping_bag", ModelPartBuilder.create().uv(0, 16).cuboid(-6.0F, -3.0F, 3.0F, 12.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		this.backpack.render(matrices, vertexConsumer, light, overlay, color);
		this.sleepingBag.render(matrices, vertexConsumer, light, overlay, color);
	}

	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color, boolean showSleepingBag) {
		this.backpack.render(matrices, vertexConsumer, light, overlay, color);
		if(showSleepingBag) this.sleepingBag.render(matrices, vertexConsumer, light, overlay, color);
	}
}