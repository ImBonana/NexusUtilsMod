package me.imbanana.nexusutils.networking.packets;

import io.wispforest.owo.network.ServerAccess;
import me.imbanana.nexusutils.block.entity.CopperHopperBlockEntity;
import net.minecraft.util.math.BlockPos;

public record UpdateCopperHopperFilterModePacket(BlockPos pos, boolean whitelist) {
    public static void receiveServer(UpdateCopperHopperFilterModePacket message, ServerAccess access) {
        access.runtime().execute(() -> {
            if(!(access.player().getWorld().getBlockEntity(message.pos()) instanceof CopperHopperBlockEntity copperHopper)) return;
            copperHopper.setWhitelistMode(message.whitelist());
        });
    }
}
