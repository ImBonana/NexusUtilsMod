package me.imbanana.nexusutils.util.accessors;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;

public interface IShulkerBoxScreenHandler {
    Inventory getInventory();

    boolean canUse(PlayerEntity player);
}
