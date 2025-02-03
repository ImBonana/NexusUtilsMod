package me.imbanana.nexusutils.util.accessors;

import net.minecraft.item.ItemStack;

public interface ILivingEntity {
    default int nexusUtils$getJumpingCooldown() { return 0; }
    default void nexusUtils$setJumpingCooldown(int cooldown) { }
    default int nexusUtils$getJumpLeft() { return 0; }
    default void nexusUtils$setJumpLeft(int jumps) { }
    default void nexusUtils$decrementJumpLeft() { }
    default boolean nexusUtils$isJumped() { return false; }
    default void nexusutils$onEquipBackpack(ItemStack stack, ItemStack previousStack) { }
}
