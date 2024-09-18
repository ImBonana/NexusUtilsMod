package me.imbanana.nexusutils.events;

import com.mojang.datafixers.util.Pair;
import me.imbanana.nexusutils.enchantment.componentTypes.ModEnchantmentEffectComponentTypes;
import me.imbanana.nexusutils.enchantment.custom.OreExcavationEnchantment;
import me.imbanana.nexusutils.enchantment.custom.TimberEnchantment;
import me.imbanana.nexusutils.util.BlockFinder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Unit;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.List;
import java.util.OptionalDouble;

@Environment(EnvType.CLIENT)
public class ModClientEvents {
    public static final RenderLayer.MultiPhase ON_TOP_LINES = RenderLayer.of(
            "on_top_lines",
            VertexFormats.LINES,
            VertexFormat.DrawMode.LINES,
            1536,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(RenderPhase.LINES_PROGRAM)
                    .lineWidth(new RenderPhase.LineWidth(OptionalDouble.empty()))
                    .layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
                    .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                    .target(RenderPhase.ITEM_ENTITY_TARGET)
                    .writeMaskState(RenderPhase.ALL_MASK)
                    .cull(RenderPhase.DISABLE_CULLING)
                    .depthTest(RenderPhase.ALWAYS_DEPTH_TEST)
                    .build(false)
    );

    public static void registerEvents() {
        WorldRenderEvents.BLOCK_OUTLINE.register((worldRenderContext, blockOutlineContext) -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;

            if(player == null) return true;

            ItemStack tool = player.getMainHandStack();

            Pair<Unit, Integer> timberEnchantment = EnchantmentHelper.getEffectListAndLevel(tool, ModEnchantmentEffectComponentTypes.TIMBER);
            Pair<Unit, Integer> oreExcavationEnchantment = EnchantmentHelper.getEffectListAndLevel(player.getMainHandStack(), ModEnchantmentEffectComponentTypes.ORE_EXCAVATION);

            if(EnchantmentHelper.hasAnyEnchantmentsWith(tool, ModEnchantmentEffectComponentTypes.BLAST)) {
                List<BlockPos> posList = BlockFinder.findPositions(worldRenderContext.world(), player, 1, 0);
                boolean success = renderShape(
                        blockOutlineContext.blockPos(),
                        worldRenderContext.world(),
                        posList,
                        worldRenderContext.matrixStack(),
                        worldRenderContext.consumers(),
                        blockOutlineContext.cameraX(),
                        blockOutlineContext.cameraY(),
                        blockOutlineContext.cameraZ()
                );
                if(success) return false;
            }

            if(timberEnchantment != null && TimberEnchantment.canBreak(blockOutlineContext.blockState().getBlock())) {
                List<BlockPos> posList = BlockFinder.getVeinBlocks(blockOutlineContext.blockState().getBlock(), blockOutlineContext.blockPos(), worldRenderContext.world(), 10 + (5 * timberEnchantment.getSecond()));
                boolean success = renderShape(
                        blockOutlineContext.blockPos(),
                        worldRenderContext.world(),
                        posList,
                        worldRenderContext.matrixStack(),
                        worldRenderContext.consumers(),
                        blockOutlineContext.cameraX(),
                        blockOutlineContext.cameraY(),
                        blockOutlineContext.cameraZ()
                );
                if(success) return false;
            }

            if(oreExcavationEnchantment != null && OreExcavationEnchantment.canBreak(blockOutlineContext.blockState().getBlock())) {
                List<BlockPos> posList = BlockFinder.getVeinBlocks(blockOutlineContext.blockState().getBlock(), blockOutlineContext.blockPos(), worldRenderContext.world(), 10 + (5 * oreExcavationEnchantment.getSecond()));
                boolean success = renderShape(
                        blockOutlineContext.blockPos(),
                        worldRenderContext.world(),
                        posList,
                        worldRenderContext.matrixStack(),
                        worldRenderContext.consumers(),
                        blockOutlineContext.cameraX(),
                        blockOutlineContext.cameraY(),
                        blockOutlineContext.cameraZ()
                );
                if(success) return false;
            }

            return true;
        });
    }

    public static boolean renderShape(BlockPos pos, ClientWorld world, List<BlockPos> posList, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, double cameraX, double cameraY, double cameraZ) {
        if (MinecraftClient.getInstance().crosshairTarget instanceof BlockHitResult crosshairTarget) {
            VoxelShape shape = VoxelShapes.empty();

            BlockPos crosshairPos = crosshairTarget.getBlockPos();
            BlockState crosshairState = world.getBlockState(crosshairPos);

            if(!crosshairState.isAir() && world.getWorldBorder().contains(crosshairPos)) {

                Block block = world.getBlockState(pos).getBlock();

                for (BlockPos blockPos : posList) {
                    BlockState blockState = world.getBlockState(blockPos);

                    BlockPos diffPos = blockPos.subtract(crosshairPos);
                    BlockState offsetShape = world.getBlockState(blockPos);

                    if (blockState.getBlock() == block) {
                        // Merge outline
                        shape = VoxelShapes.union(shape, offsetShape.getOutlineShape(world, blockPos).offset(diffPos.getX(), diffPos.getY(), diffPos.getZ()));
                    }
                }

                // render
                WorldRenderer.drawCuboidShapeOutline(matrices, vertexConsumerProvider.getBuffer(ON_TOP_LINES), shape, crosshairPos.getX() - cameraX, crosshairPos.getY() - cameraY, crosshairPos.getZ() - cameraZ, 0.75f, 0.75f, 0.75f, 0.75f);
                WorldRenderer.drawCuboidShapeOutline(matrices, vertexConsumerProvider.getBuffer(RenderLayer.LINES), shape, crosshairPos.getX() - cameraX, crosshairPos.getY() - cameraY, crosshairPos.getZ() - cameraZ, 1, 1, 1, 1);

                return true;
            }
        }

        return false;
    }
}
