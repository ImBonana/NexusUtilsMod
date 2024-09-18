package me.imbanana.nexusutils.networking.packets.screens;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

public record ScreenOpeningData() {
    public static final PacketCodec<RegistryByteBuf, ScreenOpeningData> PACKET_CODEC = PacketCodec.of(ScreenOpeningData::write, ScreenOpeningData::new);

    public ScreenOpeningData(RegistryByteBuf buf) { this(); }
    public void write(RegistryByteBuf buf) { }
}
