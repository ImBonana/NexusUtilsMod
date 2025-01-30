package me.imbanana.nexusutils.item.models;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.imbanana.nexusutils.block.entity.renderer.SleepingBagBlockEntityRenderer;
import me.imbanana.nexusutils.entity.client.ModTexturedRenderLayers;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.SimpleSpecialModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class SleepingBagModelRenderer implements SimpleSpecialModelRenderer {
    private final SleepingBagBlockEntityRenderer blockEntityRenderer;
    private final SpriteIdentifier textureId;

    public SleepingBagModelRenderer(SleepingBagBlockEntityRenderer blockEntityRenderer, SpriteIdentifier textureId) {
        this.blockEntityRenderer = blockEntityRenderer;
        this.textureId = textureId;
    }

    @Override
    public void render(ModelTransformationMode modelTransformationMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean glint) {
        this.blockEntityRenderer.renderAsItem(matrices, vertexConsumers, light, overlay, this.textureId);
    }

    public record Unbaked(Identifier texture) implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<SleepingBagModelRenderer.Unbaked> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(Identifier.CODEC.fieldOf("texture").forGetter(SleepingBagModelRenderer.Unbaked::texture)).apply(instance, SleepingBagModelRenderer.Unbaked::new)
        );

        public Unbaked(DyeColor color) {
            this(TexturedRenderLayers.createColorId(color));
        }

        @Override
        public MapCodec<? extends SpecialModelRenderer.Unbaked> getCodec() {
            return CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
            return new SleepingBagModelRenderer(new SleepingBagBlockEntityRenderer(entityModels), ModTexturedRenderLayers.createSleepingBagTextureId(DyeColor.byName(this.texture.getPath(), DyeColor.RED)));
        }
    }
}
