package me.imbanana.nexusutils.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class BlockFinder {
    public static List<BlockPos> findPositions(World world, PlayerEntity playerEntity, int radius, int depth) {
        ArrayList<BlockPos> potentialBrokenBlocks = new ArrayList<>();

        // collect information on camera
        Vec3d cameraPos = playerEntity.getCameraPosVec(1);
        Vec3d rotation = playerEntity.getRotationVec(1);
        double reachDistance = getReachDistance(playerEntity);
        Vec3d combined = cameraPos.add(rotation.x * reachDistance, rotation.y * reachDistance, rotation.z * reachDistance);

        // find block the player is currently looking at
        BlockHitResult blockHitResult = world.raycast(new RaycastContext(cameraPos, combined, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, playerEntity));

        // only show an extended hitbox if the player is looking at a block
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            Direction.Axis axis = blockHitResult.getSide().getAxis();
            ArrayList<Vec3i> positions = new ArrayList<>();

            // create a box using the given radius
            for(int x = -radius; x <= radius; x++) {
                for(int y = -radius; y <= radius; y++) {
                    for(int z = -radius; z <= radius; z++) {
                        positions.add(new Vec3i(x, y, z));
                    }
                }
            }

            BlockPos origin = blockHitResult.getBlockPos();

            // check if each position inside the box is valid
            for(Vec3i pos : positions) {
                boolean valid = false;

                if(axis == Direction.Axis.Y) {
                    if(pos.getY() == 0) {
                        potentialBrokenBlocks.add(origin.add(pos));
                        valid = true;
                    }
                }

                else if (axis == Direction.Axis.X) {
                    if(pos.getX() == 0) {
                        potentialBrokenBlocks.add(origin.add(pos));
                        valid = true;
                    }
                }

                else if (axis == Direction.Axis.Z) {
                    if(pos.getZ() == 0) {
                        potentialBrokenBlocks.add(origin.add(pos));
                        valid = true;
                    }
                }

                // Operate on depth by extending the current block away from the player.
                if(valid) {
                    for (int i = 1; i <= depth; i++) {
                        Vec3i vec = blockHitResult.getSide().getOpposite().getVector();
                        potentialBrokenBlocks.add(origin.add(pos).add(vec.getX() * i, vec.getY() * i, vec.getZ() * i));
                    }
                }
            }
        }

        return potentialBrokenBlocks;
    }

    public static List<BlockPos> getVeinBlocks(Block block, BlockPos pos, World world, int limit) {
        HashSet<BlockPos> visitedBlocks = new HashSet<>();
        HashSet<BlockPos> newPositions = new HashSet<>();
        newPositions.add(pos);

        while (!newPositions.isEmpty() && visitedBlocks.size() < limit) {
            BlockPos currPos = newPositions.iterator().next();

            if (visitedBlocks.size() + newPositions.size() < limit) {
                Stream<BlockPos> potentialBlocks = getSameBlocksConnectedToPos(block, currPos, world);
                newPositions.addAll(potentialBlocks.filter(pos1 -> !visitedBlocks.contains(pos1)).map(BlockPos::toImmutable).toList());
            }

            visitedBlocks.add(currPos);
            newPositions.remove(currPos);
        }

        return visitedBlocks.stream().toList();
    }

    public static Stream<BlockPos> getSameBlocksConnectedToPos(Block block, BlockPos pos, World world) {
        return BlockPos.stream(pos.add(-1, -1, -1), pos.add(1, 1, 1))
                .filter(bpos -> !bpos.equals(pos) && world.getBlockState(bpos).getBlock() == block);
    }

    public static double getReachDistance(PlayerEntity playerEntity) {
        return playerEntity.isCreative() ? 5.0F : 4.5F;
    }
}
