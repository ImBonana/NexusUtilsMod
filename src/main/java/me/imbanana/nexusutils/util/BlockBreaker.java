package me.imbanana.nexusutils.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BlockBreaker {
    public static void breakBlocks(ServerWorld world, BlockPos pos, List<BlockPos> posList, ServerPlayerEntity player, ItemStack heldStack) {
        Block block = world.getBlockState(pos).getBlock();

        for (BlockPos blockPos : posList) {
            BlockState blockState = world.getBlockState(blockPos);

            if (blockState.getBlock() == block) {
                boolean canMine = player.canHarvest(blockState);

                world.breakBlock(blockPos, canMine && !player.isCreative(), player, 512);
                heldStack.postMine(world, blockState, blockPos, player);

                if(canMine) {
                    player.incrementStat(Stats.MINED.getOrCreateStat(blockState.getBlock()));
                    player.addExhaustion(0.005f);
                }
            }
        }
    }
}
