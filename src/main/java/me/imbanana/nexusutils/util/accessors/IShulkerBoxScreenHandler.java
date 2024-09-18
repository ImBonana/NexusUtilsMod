package me.imbanana.nexusutils.util.accessors;

import net.minecraft.inventory.Inventory;

public interface IShulkerBoxScreenHandler {
    default Inventory nexusUtils$getInventory() {
        return null;
    }
}
