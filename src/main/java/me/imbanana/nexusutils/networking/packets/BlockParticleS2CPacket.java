package me.imbanana.nexusutils.networking.packets;

import me.imbanana.nexusutils.NexusUtils;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class BlockParticleS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender response) {
        String worldId = buf.readString();
        if(!worldId.equals(client.world.getRegistryKey().getValue().toString())) return;
        BlockPos blockPos = buf.readBlockPos();
        String strId = buf.readString();
        Block block = Registries.BLOCK.get(new Identifier(strId));
        if(block == null) return;
        client.world.addBlockBreakParticles(blockPos, block.getDefaultState());
    }
}
