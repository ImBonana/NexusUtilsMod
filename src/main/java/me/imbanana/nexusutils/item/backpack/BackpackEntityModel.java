package me.imbanana.nexusutils.item.backpack;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.components.custom.BackpackTierComponent;
import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BackpackEntityModel extends Model {
	public static final Identifier TEXTURE = NexusUtils.idOf("textures/entity/backpack/backpack.png");

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
		ModelPartData backpack = modelPartData.addChild("backpack", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -9.0F, -3.0F, 8.0F, 8.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData front = backpack.addChild("front", ModelPartBuilder.create().uv(27, 0).cuboid(-3.0F, -7.0F, 2.0F, 6.0F, 4.0F, 1.0F, new Dilation(0.0F))
				.uv(37, 13).cuboid(-2.0F, -6.0F, 2.2F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		front.addChild("bone", ModelPartBuilder.create().uv(18, 19).cuboid(-1.0F, -5.0F, 2.2F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(37, 13).cuboid(1.0F, -6.0F, 2.2F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		backpack.addChild("top", ModelPartBuilder.create().uv(32, 6).cuboid(1.0F, -10.0F, -3.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(27, 6).cuboid(-2.0F, -10.0F, -3.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(27, 10).cuboid(-2.0F, -11.0F, -3.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 1.0F));

		backpack.addChild("left_side", ModelPartBuilder.create().uv(39, 3).cuboid(3.5F, -6.0F, -2.0F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F))
				.uv(32, 13).cuboid(3.7F, -5.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		backpack.addChild("right_side", ModelPartBuilder.create().uv(39, 3).cuboid(-1.5F, -6.0F, -2.0F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F))
				.uv(32, 13).cuboid(-1.3F, -5.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 0.0F, -1.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData sleeping_bag = modelPartData.addChild("sleeping_bag", ModelPartBuilder.create().uv(0, 14).cuboid(-6.0F, -3.0F, 3.0F, 12.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, -1.0F));

		sleeping_bag.addChild("left2", ModelPartBuilder.create().uv(42, 11).cuboid(3.0F, -4.0F, 3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(27, 13).cuboid(3.0F, -1.0F, 2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(29, 16).cuboid(2.0F, -3.0F, 5.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
				.uv(9, 19).cuboid(2.0F, -1.0F, 3.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		sleeping_bag.addChild("left3", ModelPartBuilder.create().uv(42, 11).cuboid(3.0F, -4.0F, 3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(27, 13).cuboid(3.0F, -1.0F, 2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(29, 16).cuboid(3.0F, -3.0F, 5.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 19).cuboid(3.0F, -1.0F, 3.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		this.backpack.render(matrices, vertexConsumer, light, overlay, color);
		this.sleepingBag.render(matrices, vertexConsumer, light, overlay, color);
	}

	public void render(BackpackTierComponent.Tier tier, MatrixStack matrices, VertexConsumerProvider vertexConsumer, int light, int overlay, int color, boolean hasGlint, boolean showSleepingBag) {
		VertexConsumer vc = vertexConsumer.getBuffer(this.getLayer(TEXTURE));
		matrices.translate(0, 0.05, -0.02);
		this.backpack.render(matrices, vc, light, overlay, color);
		if(showSleepingBag) this.sleepingBag.render(matrices, vc, light, overlay, color);

		if(tier != BackpackTierComponent.Tier.TIER_0) this.renderTierOverlay(tier, matrices, vertexConsumer, light);

		if(hasGlint) this.renderGlint(matrices, vertexConsumer, light);
	}

	private void renderTierOverlay(BackpackTierComponent.Tier tier, MatrixStack matrices, VertexConsumerProvider vertexConsumer, int light) {
		VertexConsumer vc = vertexConsumer.getBuffer(RenderLayer.getEntityCutoutNoCull(this.getTierTexture(tier)));
		this.backpack.render(matrices, vc, light, OverlayTexture.DEFAULT_UV);
	}

	private void renderGlint( MatrixStack matrices, VertexConsumerProvider provider, int light) {
		VertexConsumer vc = VertexConsumers.union(provider.getBuffer(RenderLayer.getDirectEntityGlint()), provider.getBuffer(this.getLayer(TEXTURE)));
		this.backpack.render(matrices, vc, light, OverlayTexture.DEFAULT_UV);
	}

	private Identifier getTierTexture(BackpackTierComponent.Tier tier) {
		return NexusUtils.idOf("textures/entity/backpack/backpack_tier_%s.png".formatted(tier.asNumber()));
	}
}