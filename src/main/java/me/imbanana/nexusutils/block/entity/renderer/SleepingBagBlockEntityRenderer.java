package me.imbanana.nexusutils.block.entity.renderer;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import me.imbanana.nexusutils.block.custom.SleepingBagBlock;
import me.imbanana.nexusutils.block.entity.ModBlockEntities;
import me.imbanana.nexusutils.block.entity.SleepingBagBlockEntity;
import me.imbanana.nexusutils.block.enums.SleepingBagPart;
import me.imbanana.nexusutils.entity.client.ModModelLayers;
import me.imbanana.nexusutils.entity.client.ModTexturedRenderLayers;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

public class SleepingBagBlockEntityRenderer implements BlockEntityRenderer<SleepingBagBlockEntity> {
    private final ModelPart sleepingBagHead;
    private final ModelPart sleepingBagFoot;
    private final ModelPart sleepingBagFull;

    public SleepingBagBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.sleepingBagHead = context.getLayerModelPart(ModModelLayers.SLEEPING_BAG_HEAD);
        this.sleepingBagFoot = context.getLayerModelPart(ModModelLayers.SLEEPING_BAG_FOOT);
        this.sleepingBagFull = context.getLayerModelPart(ModModelLayers.SLEEPING_BAG_FULL);
    }

    public static TexturedModelData getHeadTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -3.0F, -8.0F, 16.0F, 3.0F, 16.0F, new Dilation(0.0F)).uv(0, 20).cuboid(-8.0F, -4.0F, 0.0F, 16.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData getFootTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 30).cuboid(-8.0F, -3.0F, -8.0F, 16.0F, 3.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData getFullTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -3.0F, -8.0F, 16.0F, 3.0F, 16.0F, new Dilation(0.0F))
                .uv(0, 20).cuboid(-8.0F, -4.0F, 0.0F, 16.0F, 1.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 30).cuboid(-8.0F, -3.0F, -24.0F, 16.0F, 3.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(SleepingBagBlockEntity sleepingBagBlockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        SpriteIdentifier spriteIdentifier = ModTexturedRenderLayers.SLEEPING_BAG_TEXTURES[sleepingBagBlockEntity.getColor().getId()];
        World world2 = sleepingBagBlockEntity.getWorld();
        if(sleepingBagBlockEntity.isShouldRenderFull()) {
            this.renderPart(matrices, vertexConsumers, this.sleepingBagFull, Direction.SOUTH, spriteIdentifier, light, overlay);
            return;
        }
        if (world2 != null) {
            BlockState blockState = sleepingBagBlockEntity.getCachedState();
            DoubleBlockProperties.PropertySource<SleepingBagBlockEntity> propertySource = DoubleBlockProperties.toPropertySource(ModBlockEntities.SLEEPING_BAG_BLOCK_ENTITY, SleepingBagBlock::getSleepingBagPart, SleepingBagBlock::getOppositePartDirection, ChestBlock.FACING, blockState, world2, sleepingBagBlockEntity.getPos(), (world, pos) -> false);
            int k = propertySource.apply(new LightmapCoordinatesRetriever<>()).get(light);
            this.renderPart(matrices, vertexConsumers, blockState.get(SleepingBagBlock.PART) == SleepingBagPart.HEAD ? this.sleepingBagHead : this.sleepingBagFoot, blockState.get(SleepingBagBlock.FACING), spriteIdentifier, k, overlay);
        } else {
            this.renderPart(matrices, vertexConsumers, this.sleepingBagHead, Direction.SOUTH, spriteIdentifier, light, overlay);
            this.renderPart(matrices, vertexConsumers, this.sleepingBagFoot, Direction.SOUTH, spriteIdentifier, light, overlay);
        }
    }

    private void renderPart(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ModelPart part, Direction direction, SpriteIdentifier sprite, int light, int overlay) {
        matrices.push();
        matrices.translate(0.5f, 1.5f, 0.5f);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0f));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(direction.asRotation()));
        VertexConsumer vertexConsumer = sprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        part.render(matrices, vertexConsumer, light, overlay);
        matrices.pop();
    }
}
