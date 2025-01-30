package me.imbanana.nexusutils.util.accessors;

import net.minecraft.item.ItemStack;

public interface IPlayerEntityRenderState {
    default void nexusUtils$setBackpack(ItemStack backpackStack) { }
    default ItemStack nexusUtils$getBackpack() { return ItemStack.EMPTY; }
}
