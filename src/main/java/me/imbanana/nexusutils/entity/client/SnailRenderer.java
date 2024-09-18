package me.imbanana.nexusutils.entity.client;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.entity.client.models.SnailModel;
import me.imbanana.nexusutils.entity.custom.SnailEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SnailRenderer extends MobEntityRenderer<SnailEntity, SnailModel<SnailEntity>> {
    private static final Identifier TEXTURE = NexusUtils.idOf("textures/entity/snail.png");

    public SnailRenderer(EntityRendererFactory.Context context) {
        super(context, new SnailModel<>(context.getPart(ModModelLayers.SNAIL)), 0.3f);
    }

    @Override
    public Identifier getTexture(SnailEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(SnailEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        if(mobEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
