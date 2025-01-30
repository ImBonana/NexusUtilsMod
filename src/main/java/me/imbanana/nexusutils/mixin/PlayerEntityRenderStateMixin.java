package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.util.accessors.IPlayerEntityRenderState;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public abstract class PlayerEntityRenderStateMixin extends BipedEntityRenderState implements IPlayerEntityRenderState {
    @Unique
    private ItemStack backpack = ItemStack.EMPTY;

    @Override
    public void nexusUtils$setBackpack(ItemStack backpack) {
        this.backpack = backpack;
    }

    @Override
    public ItemStack nexusUtils$getBackpack() {
        return this.backpack;
    }
}
