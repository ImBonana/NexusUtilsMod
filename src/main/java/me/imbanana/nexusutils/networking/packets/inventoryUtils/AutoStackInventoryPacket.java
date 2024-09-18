package me.imbanana.nexusutils.networking.packets.inventoryUtils;

import io.wispforest.owo.network.ServerAccess;
import me.imbanana.nexusutils.util.inventorySortUtils.InventoryAutoStack;

public record AutoStackInventoryPacket(boolean fromPlayerInventory) {
    public static void receiveServer(AutoStackInventoryPacket message, ServerAccess access) {
        access.runtime().execute(() -> InventoryAutoStack.autoStack(access.player(), message.fromPlayerInventory()));
    }
}
