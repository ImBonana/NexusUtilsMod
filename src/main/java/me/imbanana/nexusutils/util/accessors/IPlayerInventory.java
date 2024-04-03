package me.imbanana.nexusutils.util.accessors;

import net.minecraft.item.ItemStack;

public interface IPlayerInventory {
    ItemStack nexusUtils$getBackpackItemStack();
    void nexusUtils$setBackpackItemStack(ItemStack itemStack);
}
