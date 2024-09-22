package me.imbanana.nexusutils.item.backpack;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.components.ModComponents;
import me.imbanana.nexusutils.components.custom.BackpackTierComponent;
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
        BackpackTierComponent.Tier tier = stack.getOrDefault(ModComponents.BACKPACK_TIER, BackpackTierComponent.createDefaultBackpackTier()).tier();
        ItemStack sleepingBagItem = backpackInventory.getSleepingBag();
        matrices.push();
        matrices.scale(1.0f, -1.0f, -1.0f);
        matrices.translate(0.5f, -1.47f, -0.65f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
        this.backpackModel.render(tier, matrices, vertexConsumers, light, overlay, ColorHelper.Argb.fromFloats(1f, 1f, 1f, 1f), stack.hasGlint(), sleepingBagItem != null && !sleepingBagItem.isEmpty());
        matrices.pop();
    }
}
