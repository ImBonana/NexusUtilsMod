package me.imbanana.nexusutils.entity.custom.wolf;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.util.ITerroristable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WolfBombBeltFeatureRenderer extends FeatureRenderer<WolfEntity, WolfEntityModel<WolfEntity>> {
    private static final Identifier BOMB_BELT_TEXTURE = new Identifier(NexusUtils.MOD_ID, "textures/entity/wolf/wolf_bomb_belt.png");

    public WolfBombBeltFeatureRenderer(FeatureRendererContext<WolfEntity, WolfEntityModel<WolfEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WolfEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ITerroristable terrorist = (ITerroristable) entity;
        if(!terrorist.nexusUtils$hasBombBelt() || entity.isInvisible()) return;

        WolfBombBeltFeatureRenderer.renderModel(this.getContextModel(), BOMB_BELT_TEXTURE, matrices, vertexConsumers, light, entity, 1, 1, 1);
    }
}
