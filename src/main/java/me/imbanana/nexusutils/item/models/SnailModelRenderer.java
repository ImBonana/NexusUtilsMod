package me.imbanana.nexusutils.item.models;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.entity.renderer.SleepingBagBlockEntityRenderer;
import me.imbanana.nexusutils.entity.ModEntities;
import me.imbanana.nexusutils.entity.client.ModModelLayers;
import me.imbanana.nexusutils.entity.client.ModTexturedRenderLayers;
import me.imbanana.nexusutils.entity.client.SnailRenderer;
import me.imbanana.nexusutils.entity.client.models.SnailModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.SimpleSpecialModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SnailModelRenderer implements SimpleSpecialModelRenderer {
    private final SnailModel snailModel;
    private final Identifier textureId;

    public SnailModelRenderer(SnailModel snailModel, Identifier textureId) {
        this.snailModel = snailModel;
        this.textureId = textureId.withPrefixedPath("textures/entity/").withSuffixedPath(".png");
    }

    @Override
    public void render(ModelTransformationMode modelTransformationMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean glint) {
        this.snailModel.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(textureId)), light, overlay);
    }

    public record Unbaked(Identifier texture) implements SimpleSpecialModelRenderer.Unbaked {
        public static final MapCodec<Unbaked> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        Identifier.CODEC.fieldOf("texture").forGetter(Unbaked::texture)
                ).apply(instance, Unbaked::new)
        );

        public Unbaked() {
            this(NexusUtils.idOf("snail"));
        }

        @Override
        public MapCodec<? extends SpecialModelRenderer.Unbaked> getCodec() {
            return CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
            return new SnailModelRenderer(new SnailModel(entityModels.getModelPart(ModModelLayers.SNAIL)), texture);
        }
    }
}
