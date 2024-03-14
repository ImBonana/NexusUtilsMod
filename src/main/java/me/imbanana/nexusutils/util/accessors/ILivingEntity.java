package me.imbanana.nexusutils.util.accessors;

import net.minecraft.item.ItemStack;

public interface ILivingEntity {
    default void nexusutils$onEquipBackpack(ItemStack stack, ItemStack previousStack) { }
}
