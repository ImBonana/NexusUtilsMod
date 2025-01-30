package me.imbanana.nexusutils.entity.custom.wolf;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.util.ITerroristable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.render.entity.state.WolfEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

@Environment(EnvType.CLIENT)
public class WolfBombBeltFeatureRenderer extends FeatureRenderer<WolfEntityRenderState, WolfEntityModel> {
    private static final Identifier BOMB_BELT_TEXTURE = NexusUtils.idOf("textures/entity/wolf/wolf_bomb_belt.png");

    public WolfBombBeltFeatureRenderer(FeatureRendererContext<WolfEntityRenderState, WolfEntityModel> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WolfEntityRenderState state, float limbAngle, float limbDistance) {
        if(!state.nexusUtils$hasBombBelt() || state.invisible) return;

        WolfBombBeltFeatureRenderer.renderModel(this.getContextModel(), BOMB_BELT_TEXTURE, matrices, vertexConsumers, light, state, ColorHelper.fromFloats(1f, 1f, 1f, 1f));
    }
}
