package me.imbanana.nexusutils.networking.packets.inventoryUtils;

import io.wispforest.owo.network.ServerAccess;
import me.imbanana.nexusutils.util.inventorySortUtils.InventoryTransferAll;

public record TransferAllInventoryPacket(boolean fromPlayerInventory) {
    public static void receiveServer(TransferAllInventoryPacket message, ServerAccess access) {
        access.runtime().execute(() -> InventoryTransferAll.transferAll(access.player(), message.fromPlayerInventory()));
    }
}
