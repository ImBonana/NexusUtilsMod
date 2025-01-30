package me.imbanana.nexusutils.events;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.block.entity.ItemDisplayBlockEntity;
import me.imbanana.nexusutils.enchantment.componentTypes.ModEnchantmentEffectComponentTypes;
import me.imbanana.nexusutils.enchantment.custom.OreExcavationEnchantment;
import me.imbanana.nexusutils.enchantment.custom.TimberEnchantment;
import me.imbanana.nexusutils.util.BlockFinder;
import me.x150.renderer.util.RendererUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockPredicatesChecker;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.RaycastContext;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Consumer;

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
        HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if(player == null) return;

            ClientWorld world = player.clientWorld;
            if(world == null) return;

            // collect information on camera
            Vec3d cameraPos = player.getCameraPosVec(1);
            Vec3d rotation = player.getRotationVec(1);
            double reachDistance = player.getAttributeValue(EntityAttributes.BLOCK_INTERACTION_RANGE);
            Vec3d combined = cameraPos.add(rotation.x * reachDistance, rotation.y * reachDistance, rotation.z * reachDistance);

            // find block the player is currently looking at
            BlockHitResult blockHitResult = world.raycast(new RaycastContext(cameraPos, combined, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));

            BlockEntity blockEntity = world.getBlockEntity(blockHitResult.getBlockPos());

            if(blockEntity instanceof ItemDisplayBlockEntity itemDisplayBlockEntity) {
                ItemStack itemStack = itemDisplayBlockEntity.getRenderStack();
                TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
                boolean advancedItemTooltips = MinecraftClient.getInstance().options.advancedItemTooltips;
                Vec3d screenPos = RendererUtils.worldSpaceToScreenSpace(itemDisplayBlockEntity.getPos().toCenterPos()).add(7, 0, 0);
                int x =(int) screenPos.x;
                int y = (int) screenPos.y;
                if(Screen.hasAltDown()) {
                    drawContext.drawItemTooltip(textRenderer, itemStack, x, y);
                } else {
                    List<Text> toolTips = getSimpleTooltip(itemStack, player, advancedItemTooltips);
                    toolTips.add(Text.translatable("tooltip.nexusutils.expend_key"));
                    drawContext.drawTooltip(textRenderer, toolTips, Optional.empty(), x, y);
                }
            }
        });

        WorldRenderEvents.BLOCK_OUTLINE.register((worldRenderContext, blockOutlineContext) -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;

            if(player == null) return true;

            ItemStack tool = player.getMainHandStack();

            Pair<Unit, Integer> timberEnchantment = EnchantmentHelper.getHighestLevelEffect(tool, ModEnchantmentEffectComponentTypes.TIMBER);
            Pair<Unit, Integer> oreExcavationEnchantment = EnchantmentHelper.getHighestLevelEffect(player.getMainHandStack(), ModEnchantmentEffectComponentTypes.ORE_EXCAVATION);

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

    private static List<Text> getSimpleTooltip(ItemStack stack, PlayerEntity player, boolean advancedItemTooltips) {
        boolean bl = stack.getItem().shouldShowOperatorBlockWarnings(stack, player);
        TooltipType type = advancedItemTooltips ? TooltipType.Default.ADVANCED : TooltipType.Default.BASIC;
        if (!player.isCreative() && stack.contains(DataComponentTypes.HIDE_TOOLTIP)) {
            return bl ? ItemStack.OPERATOR_WARNINGS : List.of();
        } else {
            List<Text> list = Lists.newArrayList();
            list.add(stack.getFormattedName());
            if (!type.isAdvanced() && !stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                MapIdComponent mapIdComponent = stack.get(DataComponentTypes.MAP_ID);
                if (mapIdComponent != null) {
                    list.add(FilledMapItem.getIdText(mapIdComponent));
                }
            }

            Consumer<Text> consumer = list::add;
            if (!stack.contains(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP)) {
                stack.getItem().appendTooltip(stack, Item.TooltipContext.create(player.getWorld()), list, type);
            }

            BlockPredicatesChecker blockPredicatesChecker = stack.get(DataComponentTypes.CAN_BREAK);
            if (blockPredicatesChecker != null && blockPredicatesChecker.showInTooltip()) {
                consumer.accept(ScreenTexts.EMPTY);
                consumer.accept(BlockPredicatesChecker.CAN_BREAK_TEXT);
                blockPredicatesChecker.addTooltips(consumer);
            }

            BlockPredicatesChecker blockPredicatesChecker2 = stack.get(DataComponentTypes.CAN_PLACE_ON);
            if (blockPredicatesChecker2 != null && blockPredicatesChecker2.showInTooltip()) {
                consumer.accept(ScreenTexts.EMPTY);
                consumer.accept(BlockPredicatesChecker.CAN_PLACE_TEXT);
                blockPredicatesChecker2.addTooltips(consumer);
            }

            if (type.isAdvanced()) {
                if (stack.isDamaged()) {
                    list.add(Text.translatable("item.durability", stack.getMaxDamage() - stack.getDamage(), stack.getMaxDamage()));
                }

                list.add(Text.literal(Registries.ITEM.getId(stack.getItem()).toString()).formatted(Formatting.DARK_GRAY));
                int i = stack.getComponents().size();
                if (i > 0) {
                    list.add(Text.translatable("item.components", i).formatted(Formatting.DARK_GRAY));
                }
            }

            if (bl) {
                list.addAll(ItemStack.OPERATOR_WARNINGS);
            }

            return list;
        }
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
                VertexRendering.drawOutline(matrices, vertexConsumerProvider.getBuffer(ON_TOP_LINES), shape, crosshairPos.getX() - cameraX, crosshairPos.getY() - cameraY, crosshairPos.getZ() - cameraZ, ColorHelper.fromFloats(0.75f, 0.75f, 0.75f, 0.75f));
                VertexRendering.drawOutline(matrices, vertexConsumerProvider.getBuffer(RenderLayer.LINES), shape, crosshairPos.getX() - cameraX, crosshairPos.getY() - cameraY, crosshairPos.getZ() - cameraZ, ColorHelper.fromFloats(1, 1, 1, 1));

                return true;
            }
        }

        return false;
    }
}
