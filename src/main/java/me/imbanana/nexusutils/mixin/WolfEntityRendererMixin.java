package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.entity.custom.wolf.WolfBombBeltFeatureRenderer;
import net.minecraft.client.render.entity.AgeableMobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.WolfEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.render.entity.state.WolfEntityRenderState;
import net.minecraft.entity.passive.WolfEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfEntityRenderer.class)
public abstract class WolfEntityRendererMixin extends AgeableMobEntityRenderer<WolfEntity, WolfEntityRenderState, WolfEntityModel> {

    public WolfEntityRendererMixin(EntityRendererFactory.Context context, WolfEntityModel model, WolfEntityModel babyModel, float shadowRadius) {
        super(context, model, babyModel, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectInit(EntityRendererFactory.Context context, CallbackInfo ci) {
        this.addFeature(new WolfBombBeltFeatureRenderer(this));
    }

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/passive/WolfEntity;Lnet/minecraft/client/render/entity/state/WolfEntityRenderState;F)V", at = @At("TAIL"))
    private void injectUpdateRenderState(WolfEntity wolfEntity, WolfEntityRenderState wolfEntityRenderState, float f, CallbackInfo ci) {
        wolfEntityRenderState.nexusUtils$setBombBelt(wolfEntity.nexusUtils$hasBombBelt());
    }
}
