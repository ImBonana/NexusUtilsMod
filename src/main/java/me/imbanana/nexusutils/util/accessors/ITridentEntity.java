package me.imbanana.nexusutils.util.accessors;

import net.minecraft.item.ItemStack;

public interface ITridentEntity {
    default ItemStack nexusUtils$getTridentItemStack() {
        return ItemStack.EMPTY;
    }
}
