package me.imbanana.nexusutils.entity.client;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.entity.client.models.NetherBatModel;
import me.imbanana.nexusutils.entity.client.models.SnailModel;
import me.imbanana.nexusutils.entity.custom.NetherBatEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class NetherBatRenderer extends MobEntityRenderer<NetherBatEntity, NetherBatModel<NetherBatEntity>> {
    private static final Identifier TEXTURE = new Identifier(NexusUtils.MOD_ID, "textures/entity/nether_bat.png");


    public NetherBatRenderer(EntityRendererFactory.Context context) {
        super(context, new NetherBatModel<>(context.getPart(ModModelLayers.NETHER_BAT)), 0f);
    }

    @Override
    public Identifier getTexture(NetherBatEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void setupTransforms(NetherBatEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getPitch()));
    }
}
