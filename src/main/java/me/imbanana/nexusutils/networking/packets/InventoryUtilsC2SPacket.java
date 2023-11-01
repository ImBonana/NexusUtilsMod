package me.imbanana.nexusutils.networking.packets;

import me.imbanana.nexusutils.util.inventorySortUtils.InventoryAutoStack;
import me.imbanana.nexusutils.util.inventorySortUtils.InventorySorter;
import me.imbanana.nexusutils.util.inventorySortUtils.InventoryTransferAll;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class InventoryUtilsC2SPacket {
    public static void receiveSort(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        boolean isPlayerInventory = buf.readBoolean();
        server.execute(() -> {
            if(isPlayerInventory) InventorySorter.sortPlayerInventory(player);
            else InventorySorter.sortCurrentlyOpenInventory(player);
        });
    }

    public static void receiveAutoStack(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        boolean fromPlayerInventory = buf.readBoolean();
        server.execute(() -> {
            InventoryAutoStack.autoStack(player, fromPlayerInventory);
        });
    }

    public static void receiveTransferAll(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        boolean fromPlayerInventory = buf.readBoolean();
        server.execute(() -> {
            InventoryTransferAll.transferAll(player, fromPlayerInventory);
        });
    }
}
