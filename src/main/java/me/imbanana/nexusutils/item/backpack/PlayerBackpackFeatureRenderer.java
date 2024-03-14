package me.imbanana.nexusutils.item.backpack;

import me.imbanana.nexusutils.entity.client.ModModelLayers;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.screen.backpack.BackpackInventory;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class PlayerBackpackFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private final BackpackEntityModel backpack;

    public PlayerBackpackFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
        this.backpack = new BackpackEntityModel(loader.getModelPart(ModModelLayers.BACKPACK));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(!(entity instanceof PlayerEntity)) return;
        ItemStack itemStack = ((PlayerEntity)entity).getInventory().getStack(BackpackItem.SLOT_ID);
        if(itemStack.isEmpty()) return;
        if(!itemStack.isOf(ModItems.BACKPACK)) return;

        BackpackInventory backpackInventory = new BackpackInventory(itemStack);
        ItemStack sleepingBagItem = backpackInventory.getSleepingBag();

        matrices.push();
        matrices.translate(0f, -0.8f, 0.35f);
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(BackpackEntityModel.TEXTURE), false, itemStack.hasGlint());
        this.backpack.render(sleepingBagItem != null && !sleepingBagItem.isEmpty(), matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);

        matrices.pop();
    }
}
