package me.imbanana.nexusutils.item.backpack;

import me.imbanana.nexusutils.components.ModComponents;
import me.imbanana.nexusutils.components.custom.BackpackTierComponent;
import me.imbanana.nexusutils.entity.client.ModModelLayers;
import me.imbanana.nexusutils.screen.backpack.BackpackInventory;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.ColorHelper;

public class PlayerBackpackFeatureRenderer<T extends PlayerEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private final BackpackEntityModel backpack;

    public PlayerBackpackFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
        this.backpack = new BackpackEntityModel(loader.getModelPart(ModModelLayers.BACKPACK));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack itemStack = entity.getInventory().nexusUtils$getBackpackItemStack();
        if(!(itemStack.getItem() instanceof BackpackItem)) return;

        BackpackInventory backpackInventory = new BackpackInventory(itemStack);
        ItemStack sleepingBagItem = backpackInventory.getSleepingBag();
        BackpackTierComponent.Tier tier = itemStack.getOrDefault(ModComponents.BACKPACK_TIER, BackpackTierComponent.createDefaultBackpackTier()).tier();

        matrices.push();
        matrices.translate(0f, -0.8f, 0.35f);
        boolean shouldRenderSleepingBag = sleepingBagItem != null && !sleepingBagItem.isEmpty();

        this.backpack.render(tier, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, ColorHelper.Argb.fromFloats(1f, 1f, 1f, 1f), itemStack.hasGlint(), shouldRenderSleepingBag);

        matrices.pop();
    }
}
