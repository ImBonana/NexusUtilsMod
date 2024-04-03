package me.imbanana.nexusutils.networking.packets;

import me.imbanana.nexusutils.util.accessors.IPlayerInventory;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

public class SyncBackpackS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender response) {
        int playerId = buf.readInt();
        ItemStack backpack = buf.readItemStack();

        if(client.world == null) return;
        Entity entity = client.world.getEntityById(playerId);
        if(entity == null) return;

        if(entity instanceof PlayerEntity player) {
            ((IPlayerInventory) player.getInventory()).nexusUtils$setBackpackItemStack(backpack);
        }
    }
}
