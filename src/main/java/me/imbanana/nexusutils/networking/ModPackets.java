package me.imbanana.nexusutils.networking;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.networking.packets.BlockParticleS2CPacket;
import me.imbanana.nexusutils.networking.packets.SitC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final Identifier SIT_ID = new Identifier(NexusUtils.MOD_ID, "sit");
    public static final Identifier BLOCK_PARTICLE = new Identifier(NexusUtils.MOD_ID, "block_particle");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(SIT_ID, SitC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(BLOCK_PARTICLE, BlockParticleS2CPacket::receive);
    }
}
