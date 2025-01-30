package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.util.accessors.IWolfEntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.WolfEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WolfEntityRenderState.class)
public abstract class WolfEntityRenderStateMixin extends LivingEntityRenderState implements IWolfEntityRenderState {
    @Unique
    private boolean bombBelt = false;

    @Override
    public void nexusUtils$setBombBelt(boolean bombBelt) {
        this.bombBelt = bombBelt;
    }

    @Override
    public boolean nexusUtils$hasBombBelt() {
        return this.bombBelt;
    }
}
