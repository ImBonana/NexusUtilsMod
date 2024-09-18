package me.imbanana.nexusutils.networking.packets.screens;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.BlockPos;

public record BlockEntityScreenOpeningData(BlockPos pos) {
    public static final PacketCodec<RegistryByteBuf, BlockEntityScreenOpeningData> PACKET_CODEC = PacketCodec.of(BlockEntityScreenOpeningData::write, BlockEntityScreenOpeningData::new);

    public BlockEntityScreenOpeningData(RegistryByteBuf buf) {
        this(buf.readBlockPos());
    }
    public void write(RegistryByteBuf buf) {
        buf.writeBlockPos(pos);
    }
}
