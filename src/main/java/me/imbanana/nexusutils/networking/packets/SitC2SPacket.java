package me.imbanana.nexusutils.networking.packets;

import me.imbanana.nexusutils.mixin.sit.SitLivingEntityAccessor;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SitC2SPacket {
    static Map<UUID, Long> cooldowns = new HashMap<>();
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        if(cooldowns.getOrDefault(player.getUuid(), System.currentTimeMillis()) > System.currentTimeMillis()) return;

        if (player.hasVehicle()) {
            player.sendMessage(Text.translatable("message.nexusutils.sit.try_already").fillStyle(Style.EMPTY.withColor(Formatting.RED)));
            cooldowns.put(player.getUuid(), System.currentTimeMillis() + 100);
            return;
        }

        if (!player.isOnGround()) {
            player.sendMessage(Text.translatable("message.nexusutils.sit.try_falling").fillStyle(Style.EMPTY.withColor(Formatting.RED)));
            cooldowns.put(player.getUuid(), System.currentTimeMillis() + 100);
            return;
        }

        BlockPos pos = player.getBlockPos();
        World world = player.getWorld();
        BlockState state = world.getBlockState(pos);
        double topHeight = getTopHeight(world, state, pos, player);

        // Skip if it's not solid or taller than jump height.
        if (topHeight < 0.D || topHeight > maxJumpHeight(player)) {
            pos = pos.down();
            state = world.getBlockState(pos);
            topHeight = getTopHeight(world, state, pos, player);
        }

        if (topHeight < 0.D) {
            player.sendMessage(Text.translatable("message.nexusutils.sit.try_air").fillStyle(Style.EMPTY.withColor(Formatting.RED)));
            cooldowns.put(player.getUuid(), System.currentTimeMillis() + 100);
            return;
        }

        double x = player.getX();
        double z = player.getZ();

        double y = pos.getY()+getYHeight(BlockPos.ofFloored(x, pos.getY(), z), world, player.getChunkPos(), player.getY());

        DisplayEntity.TextDisplayEntity entity = new DisplayEntity.TextDisplayEntity(EntityType.TEXT_DISPLAY, player.getServerWorld());
        player.getServerWorld().spawnEntity(entity);
        entity.setPos(x,y,z);
        if(!player.startRiding(entity)) {
            entity.discard();
        } else {
            player.sendMessage(Text.translatable("message.nexusutils.sit.success").fillStyle(Style.EMPTY.withColor(Formatting.GREEN)));
        }
        cooldowns.put(player.getUuid(), System.currentTimeMillis() + 100);
    }

    static double getYHeight(BlockPos pos, World world, ChunkPos chunkPos, double playerY) {
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();

        if (block instanceof StairsBlock && blockState.get(StairsBlock.HALF) == BlockHalf.BOTTOM) {
            return playerY-1 == pos.getY() ? 1 : 0.5;
        }

        if(block instanceof FenceBlock || block instanceof FenceGateBlock) return 1;

        if (blockState.isFullCube(world,pos)) return 1;

        return blockState.getCollisionShape(world.getChunkAsView(chunkPos.x, chunkPos.z), pos).getMax(Direction.Axis.Y);
    }

    static double getTopHeight(BlockView world, BlockState state, BlockPos pos, Entity entity) {
        if (state.isAir()) {
            return -1.D;
        }

        if (state.getBlock() instanceof StairsBlock && state.get(StairsBlock.HALF) == BlockHalf.BOTTOM) {
            return 0.5D;
        }

        final VoxelShape shape = state.getCollisionShape(world, pos, ShapeContext.of(entity));

        return shape.getMax(Direction.Axis.Y);
    }

    static double maxJumpHeight(LivingEntity entity) {
        return maxJumpHeight(((SitLivingEntityAccessor) entity).invokeGetJumpVelocity());
    }

    static double maxJumpHeight(double velocity) {
        return heightAtTime(velocity, zeroTangent(velocity));
    }

    static double zeroTangent(double velocity) {
        return 49.4983 * Math.log(Math.fma(0.25768759333570745, velocity, 1.010135365875973));
    }

    static double heightAtTime(double velocity, double ticks) {
        return -50 * (velocity + 3.92) * (Math.pow(0.98, ticks) - 1D) - 3.92 * ticks;
    }
}
