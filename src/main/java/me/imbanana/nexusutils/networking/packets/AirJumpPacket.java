package me.imbanana.nexusutils.networking.packets;

import io.wispforest.owo.network.ClientAccess;
import io.wispforest.owo.network.ServerAccess;
import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.networking.ModNetwork;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.Random;

public record AirJumpPacket(int playerId) {
    private final static Random random = new Random();

    public static void receiveServer(AirJumpPacket message, ServerAccess access) {
        ServerPlayerEntity player = access.player();
        World world = player.getWorld();

        world.playSound(
                null,
                player.getBlockPos(),
                SoundEvents.ENTITY_TURTLE_SHAMBLE,
                SoundCategory.PLAYERS,
                0.4f,
                1f
        );

        NexusUtils.LOGGER.info("Effexct!");

        ModNetwork.NETWORK_CHANNEL.serverHandle(access.runtime()).send(new AirJumpPacket(message.playerId()));
    }

    public static void receiveClient(AirJumpPacket message, ClientAccess access) {
        ClientWorld world = access.runtime().world;
        if(world == null) return;
        Entity entity = world.getEntityById(message.playerId());
        if(entity == null) return;

        if(entity instanceof PlayerEntity player) {
            for(int i = 0; i < 5; ++i) {
                double d = random.nextGaussian() * 0.02D;
                double e = random.nextGaussian() * 0.02D;
                double f = random.nextGaussian() * 0.02D;
                entity.getWorld().addParticle(ParticleTypes.CLOUD, entity.getParticleX(1.0D), entity.getPos().getY(), player.getParticleZ(1.0D), d, e, f);
            }
        }
    }
}
