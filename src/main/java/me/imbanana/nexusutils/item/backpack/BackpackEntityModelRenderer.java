package me.imbanana.nexusutils.item.backpack;

import com.mojang.serialization.MapCodec;
import me.imbanana.nexusutils.components.ModComponents;
import me.imbanana.nexusutils.components.custom.BackpackTierComponent;
import me.imbanana.nexusutils.screen.backpack.BackpackInventory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.RotationAxis;

public class BackpackEntityModelRenderer implements SpecialModelRenderer<BackpackEntityModelRenderer.BackpackState> {
    private final BackpackEntityModel backpackModel;

    public BackpackEntityModelRenderer(BackpackEntityModel backpackModel) {
        this.backpackModel = backpackModel;
    }

    @Override
    public void render(BackpackState backpackState, ModelTransformationMode modelTransformationMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean glint) {
        if(backpackState == null) return;
        matrices.push();
        matrices.scale(1.0f, -1.0f, -1.0f);
        matrices.translate(0.5f, -1.47f, -0.65f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
        this.backpackModel.render(backpackState.tier(), matrices, vertexConsumers, light, overlay, ColorHelper.fromFloats(1f, 1f, 1f, 1f), backpackState.glint(), backpackState.hasSleepingBag());
        matrices.pop();
    }

    @Override
    public BackpackState getData(ItemStack stack) {
        BackpackInventory backpackInventory = new BackpackInventory(stack);
        BackpackTierComponent.Tier tier = stack.getOrDefault(ModComponents.BACKPACK_TIER, BackpackTierComponent.createDefaultBackpackTier()).tier();
        ItemStack sleepingBagItem = backpackInventory.getSleepingBag();
        return new BackpackState(tier, sleepingBagItem != null && !sleepingBagItem.isEmpty(), stack.hasGlint());
    }

    @Environment(EnvType.CLIENT)
    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final Unbaked INSTANCE = new Unbaked();
        public static final MapCodec<BackpackEntityModelRenderer.Unbaked> CODEC = MapCodec.unit(INSTANCE);

        @Override
        public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
            return new BackpackEntityModelRenderer(new BackpackEntityModel(BackpackEntityModel.getTexturedModelData().createModel()));
        }

        @Override
        public MapCodec<? extends SpecialModelRenderer.Unbaked> getCodec() {
            return CODEC;
        }
    }

    public record BackpackState(BackpackTierComponent.Tier tier, boolean hasSleepingBag, boolean glint) { }
}
