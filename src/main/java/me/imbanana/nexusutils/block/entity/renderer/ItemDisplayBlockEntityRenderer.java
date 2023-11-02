package me.imbanana.nexusutils.block.entity.renderer;

import me.imbanana.nexusutils.block.entity.ItemDisplayBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class ItemDisplayBlockEntityRenderer implements BlockEntityRenderer<ItemDisplayBlockEntity> {
    public ItemDisplayBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(ItemDisplayBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = entity.getRenderStack();
        matrices.push();

        matrices.translate(0.5f, stack.getItem() instanceof BlockItem ? 0.3f : 0.4f, 0.5f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getRotation(tickDelta)));
        itemRenderer.renderItem(
                stack,
                ModelTransformationMode.GROUND,
                getLightLevel(entity.getWorld(), entity.getPos()),
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                entity.getWorld(),
                1
        );
        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
