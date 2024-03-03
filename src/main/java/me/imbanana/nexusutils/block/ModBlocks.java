package me.imbanana.nexusutils.block;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.custom.CopperHopperBlock;
import me.imbanana.nexusutils.block.custom.FrozenLavaBlock;
import me.imbanana.nexusutils.block.custom.ItemDisplayBlock;
import me.imbanana.nexusutils.block.custom.SleepingBagBlock;
import me.imbanana.nexusutils.block.entity.ModBlockEntities;
import me.imbanana.nexusutils.block.entity.renderer.ItemDisplayBlockEntityRenderer;
import me.imbanana.nexusutils.block.entity.renderer.SleepingBagBlockEntityRenderer;
import me.imbanana.nexusutils.block.enums.SleepingBagPart;
import me.imbanana.nexusutils.item.ModItems;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block FROZEN_LAVA = registerBlock("frozen_lava", new FrozenLavaBlock(
            FabricBlockSettings.create()
                    .mapColor(MapColor.BLACK)
                    .ticksRandomly()
                    .requiresTool()
                    .strength(25.0f, 600.0f)
                    .sounds(BlockSoundGroup.GLASS)
                    .instrument(Instrument.BASEDRUM)
    ), false);

    public static final Block COPPER_HOPPER = registerBlock("copper_hopper", new CopperHopperBlock(FabricBlockSettings.copyOf(Blocks.HOPPER)));

    public static final Block ITEM_DISPLAY = registerBlock("item_display", new ItemDisplayBlock(FabricBlockSettings.copyOf(Blocks.GLASS).nonOpaque()));

    public static final Block BLACK_SLEEPING_BAG = registerSleepingBag(DyeColor.BLACK);
    public static final Block BLUE_SLEEPING_BAG = registerSleepingBag(DyeColor.BLUE);
    public static final Block BROWN_SLEEPING_BAG = registerSleepingBag(DyeColor.BROWN);
    public static final Block CYAN_SLEEPING_BAG = registerSleepingBag(DyeColor.CYAN);
    public static final Block GRAY_SLEEPING_BAG = registerSleepingBag(DyeColor.GRAY);
    public static final Block GREEN_SLEEPING_BAG = registerSleepingBag(DyeColor.GREEN);
    public static final Block LIGHT_BLUE_SLEEPING_BAG = registerSleepingBag(DyeColor.LIGHT_BLUE);
    public static final Block LIGHT_GRAY_SLEEPING_BAG = registerSleepingBag(DyeColor.LIGHT_GRAY);
    public static final Block LIME_SLEEPING_BAG = registerSleepingBag(DyeColor.LIME);
    public static final Block MAGENTA_SLEEPING_BAG = registerSleepingBag(DyeColor.MAGENTA);
    public static final Block ORANGE_SLEEPING_BAG = registerSleepingBag(DyeColor.ORANGE);
    public static final Block PINK_SLEEPING_BAG = registerSleepingBag(DyeColor.PINK);
    public static final Block PURPLE_SLEEPING_BAG = registerSleepingBag(DyeColor.PURPLE);
    public static final Block RED_SLEEPING_BAG = registerSleepingBag(DyeColor.RED);
    public static final Block WHITE_SLEEPING_BAG = registerSleepingBag(DyeColor.WHITE);
    public static final Block YELLOW_SLEEPING_BAG = registerSleepingBag(DyeColor.YELLOW);

    public static Block registerSleepingBag(DyeColor color) {
        return registerBlock(color.getName() + "_sleeping_bag", new SleepingBagBlock(
                color,
                FabricBlockSettings.create()
                        .mapColor(state -> state.get(SleepingBagBlock.PART) == SleepingBagPart.FOOT ? color.getMapColor() : MapColor.WHITE_GRAY)
                        .sounds(BlockSoundGroup.WOOL)
                        .strength(0.2f)
                        .nonOpaque()
                        .burnable()
                        .pistonBehavior(PistonBehavior.DESTROY)
        ), true, true, 1);
    }
    
    private static Block registerBlock(String name, Block block) {
        return registerBlock(name, block, true, true);
    }
    private static Block registerBlock(String name, Block block, boolean hasItem) {
        return registerBlock(name, block, hasItem, false);
    }

    private static Block registerBlock(String name, Block block, boolean hasItem, boolean addToCategory) {
        return registerBlock(name, block, hasItem, addToCategory, 64);
    }

    private static Block registerBlock(String name, Block block, boolean hasItem, boolean addToCategory, int maxStack) {
        if(hasItem) registerBlockItem(name, block, addToCategory, maxStack);
        return Registry.register(Registries.BLOCK, new Identifier(NexusUtils.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, boolean addToCategory, int maxStack) {
        Item registered = Registry.register(Registries.ITEM, new Identifier(NexusUtils.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
        if(addToCategory) ModItems.addItemToCategory(registered);
        return registered;
    }

    public static void  registerModBlocks() {
        NexusUtils.LOGGER.info("Registering Mod Blocks for " + NexusUtils.MOD_ID);
    }

    public static void registerClientRender() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ITEM_DISPLAY, RenderLayer.getCutout());

        registerSleepingBagsRenders();

        BlockEntityRendererFactories.register(ModBlockEntities.ITEM_DISPLAY_BLOCK_ENTITY, ItemDisplayBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.SLEEPING_BAG_BLOCK_ENTITY, SleepingBagBlockEntityRenderer::new);
    }

    private static void registerSleepingBagsRenders() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACK_SLEEPING_BAG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_SLEEPING_BAG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BROWN_SLEEPING_BAG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CYAN_SLEEPING_BAG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRAY_SLEEPING_BAG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GREEN_SLEEPING_BAG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIGHT_BLUE_SLEEPING_BAG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIGHT_GRAY_SLEEPING_BAG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIME_SLEEPING_BAG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MAGENTA_SLEEPING_BAG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ORANGE_SLEEPING_BAG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINK_SLEEPING_BAG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLE_SLEEPING_BAG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_SLEEPING_BAG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WHITE_SLEEPING_BAG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.YELLOW_SLEEPING_BAG, RenderLayer.getCutout());

    }
}
