package me.imbanana.nexusutils.entity.client;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.entity.client.models.SnailModel;
import me.imbanana.nexusutils.entity.custom.SnailEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;

public class SnailRenderer extends MobEntityRenderer<SnailEntity, LivingEntityRenderState, SnailModel> {
    public static final Identifier TEXTURE = NexusUtils.idOf("textures/entity/snail.png");

    public SnailRenderer(EntityRendererFactory.Context context) {
        super(context, new SnailModel(context.getPart(ModModelLayers.SNAIL)), 0.3f);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void updateRenderState(SnailEntity livingEntity, LivingEntityRenderState livingEntityRenderState, float f) {
        super.updateRenderState(livingEntity, livingEntityRenderState, f);
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return TEXTURE;
    }
}
