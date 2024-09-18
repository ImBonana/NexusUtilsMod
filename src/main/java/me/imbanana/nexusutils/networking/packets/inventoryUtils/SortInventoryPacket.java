package me.imbanana.nexusutils.networking.packets.inventoryUtils;

import io.wispforest.owo.network.ServerAccess;
import me.imbanana.nexusutils.util.inventorySortUtils.InventorySorter;

public record SortInventoryPacket(boolean isPlayerInventory) {
    public static void receiveServer(SortInventoryPacket message, ServerAccess access) {
        access.runtime().execute(() -> {
            if(message.isPlayerInventory()) InventorySorter.sortPlayerInventory(access.player());
            else InventorySorter.sortCurrentlyOpenInventory(access.player());
        });
    }
}
