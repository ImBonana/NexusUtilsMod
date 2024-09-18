package me.imbanana.nexusutils.networking.packets;

import io.wispforest.owo.network.ClientAccess;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public record SyncBackpackPacket(int playerId, ItemStack backpack) {
    public static void receiveClient(SyncBackpackPacket message, ClientAccess access) {
        ClientWorld world = access.runtime().world;
        if(world == null) return;
        Entity entity = world.getEntityById(message.playerId());
        if(entity == null) return;

        if(entity instanceof PlayerEntity player) {
            player.getInventory().nexusUtils$setBackpackItemStack(message.backpack());
        }
    }
}
