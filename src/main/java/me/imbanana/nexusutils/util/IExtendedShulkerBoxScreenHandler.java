package me.imbanana.nexusutils.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;

public interface IExtendedShulkerBoxScreenHandler {
    Inventory getInventory();

    boolean canUse(PlayerEntity player);
}
