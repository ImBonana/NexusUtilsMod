package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.entity.custom.wolf.WolfBombBeltFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.WolfEntityRenderer;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.entity.passive.WolfEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfEntityRenderer.class)
public abstract class WolfEntityRendererMixin extends MobEntityRenderer<WolfEntity, WolfEntityModel<WolfEntity>> {
    public WolfEntityRendererMixin(EntityRendererFactory.Context context, WolfEntityModel<WolfEntity> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectInit(EntityRendererFactory.Context context, CallbackInfo ci) {
        this.addFeature(new WolfBombBeltFeatureRenderer(this));
    }
}
