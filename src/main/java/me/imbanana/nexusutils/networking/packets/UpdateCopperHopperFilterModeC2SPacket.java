package me.imbanana.nexusutils.networking.packets;

import me.imbanana.nexusutils.block.entity.CopperHopperBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class UpdateCopperHopperFilterModeC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        BlockPos pos = buf.readBlockPos();
        boolean newValue = buf.readBoolean();

        server.execute(() -> {
            if(!(player.getWorld().getBlockEntity(pos) instanceof CopperHopperBlockEntity copperHopper)) return;
            copperHopper.setWhitelist(newValue);
        });
    }
}
