package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.enchantment.ModEnchantments;
import me.imbanana.nexusutils.enchantment.custom.OreExcavationEnchantment;
import me.imbanana.nexusutils.enchantment.custom.TimberEnchantment;
import me.imbanana.nexusutils.util.BlockFinder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(WorldRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class WorldRenderMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "drawBlockOutline", at = @At(value = "HEAD"), cancellable = true)
    private void InjectDrawBlockOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity, double cameraX, double cameraY, double cameraZ, BlockPos pos, BlockState state, CallbackInfo ci) {
        ClientPlayerEntity player = this.client.player;

        if(player == null) return;

        ClientWorld world = this.client.world;

        if(world == null) return;

        int blastEnchantmentTarget = EnchantmentHelper.getLevel(ModEnchantments.BLAST, player.getMainHandStack());
        int timberEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.TIMBER, player.getMainHandStack());
        int oreExcavationLevel = EnchantmentHelper.getLevel(ModEnchantments.ORE_EXCAVATION, player.getMainHandStack());

        if(blastEnchantmentTarget > 0) {
            List<BlockPos> posList = BlockFinder.findPositions(world, player, 1, 0);
            boolean success = renderShape(pos, world, posList, matrices, vertexConsumer, cameraX, cameraY, cameraZ, 1, 1, 1, 1);
            if(success) ci.cancel();
        }

        if(timberEnchantmentLevel > 0 && TimberEnchantment.canBreak(state.getBlock())) {
            List<BlockPos> posList = BlockFinder.getVeinBlocks(state.getBlock(), pos, world, 10 + (5 * timberEnchantmentLevel));
            boolean success = renderShape(pos, world, posList, matrices, vertexConsumer, cameraX, cameraY, cameraZ, 1, 1, 1, 1);
            if(success) ci.cancel();
        }

        if(oreExcavationLevel > 0 && OreExcavationEnchantment.canBreak(state.getBlock())) {
            List<BlockPos> posList = BlockFinder.getVeinBlocks(state.getBlock(), pos, world, 10 + (5 * oreExcavationLevel));
            boolean success = renderShape(pos, world, posList, matrices, vertexConsumer, cameraX, cameraY, cameraZ, 1, 1, 1, 1);
            if(success) ci.cancel();
        }
    }

    @Invoker("drawCuboidShapeOutline")
    public static void drawCuboidShapeOutline(MatrixStack matrices, VertexConsumer vertexConsumer, VoxelShape shape, double offsetX, double offsetY, double offsetZ, float red, float green, float blue, float alpha) {
        throw new AssertionError();
    }

    @Unique
    public boolean renderShape(BlockPos pos, ClientWorld world, List<BlockPos> posList, MatrixStack matrices, VertexConsumer vertexConsumer, double cameraX, double cameraY, double cameraZ, float red, float green, float blue, float alpha) {
        if (client.crosshairTarget instanceof BlockHitResult crosshairTarget) {
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(VoxelShapes.empty());

            BlockPos crosshairPos = crosshairTarget.getBlockPos();
            BlockState crosshairState = client.world.getBlockState(crosshairPos);

            if(!crosshairState.isAir() && client.world.getWorldBorder().contains(crosshairPos)) {

                Block block = world.getBlockState(pos).getBlock();

                for (BlockPos blockPos : posList) {
                    BlockState blockState = world.getBlockState(blockPos);

                    BlockPos diffPos = blockPos.subtract(crosshairPos);
                    BlockState offsetShape = world.getBlockState(blockPos);

                    if (blockState.getBlock() == block) {
                        // Merge outline
                        shapes.set(0, VoxelShapes.union(shapes.get(0), offsetShape.getOutlineShape(world, blockPos).offset(diffPos.getX(), diffPos.getY(), diffPos.getZ())));
                    }
                }

                // render
                for (VoxelShape shape : shapes) {
                    drawCuboidShapeOutline(matrices, vertexConsumer, shape, crosshairPos.getX() - cameraX, crosshairPos.getY() - cameraY, crosshairPos.getZ() - cameraZ, red, green, blue, alpha);
                }

                return true;
            }
        }

        return false;
    }
}
