package me.imbanana.nexusutils.item.backpack;

import me.imbanana.nexusutils.screen.backpack.BackpackInventory;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.RotationAxis;

public class BackpackEntityModelRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private final BackpackEntityModel backpackModel;

    public BackpackEntityModelRenderer() {
        this.backpackModel = new BackpackEntityModel(BackpackEntityModel.getTexturedModelData().createModel());
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BackpackInventory backpackInventory = new BackpackInventory(stack);
        ItemStack sleepingBagItem = backpackInventory.getSleepingBag();
        matrices.push();
        matrices.scale(1.0f, -1.0f, -1.0f);
        matrices.translate(0.5f, -1.5f, -0.5f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
        VertexConsumer vertexConsumer2 = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.backpackModel.getLayer(BackpackEntityModel.TEXTURE), false, stack.hasGlint());
        this.backpackModel.render(matrices, vertexConsumer2, light, overlay, ColorHelper.Argb.fromFloats(1f, 1f, 1f, 1f), sleepingBagItem != null && !sleepingBagItem.isEmpty());
        matrices.pop();
    }
}
