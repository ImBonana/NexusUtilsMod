package me.imbanana.nexusutils.util;

import net.minecraft.item.ItemStack;

public interface TridentEntityItemStackGetter {
    default ItemStack getTridentItemStack() {
        return ItemStack.EMPTY;
    }
}
