package me.imbanana.nexusutils.util.accessors;

import net.minecraft.item.ItemStack;

public interface IPlayerInventory {
    default ItemStack nexusUtils$getBackpackItemStack() {
        return ItemStack.EMPTY;
    }

    default void nexusUtils$setBackpackItemStack(ItemStack itemStack) { }

    default boolean nexusUtils$isBackapckEquipped() {
        return false;
    }
}
