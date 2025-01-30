package me.imbanana.nexusutils.block;

import kotlin.jvm.functions.Function2;
import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.custom.*;
import me.imbanana.nexusutils.block.entity.ModBlockEntities;
import me.imbanana.nexusutils.block.entity.renderer.ItemDisplayBlockEntityRenderer;
import me.imbanana.nexusutils.block.entity.renderer.SleepingBagBlockEntityRenderer;
import me.imbanana.nexusutils.block.enums.SleepingBagPart;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.item.custom.SleepingBagItem;
import me.imbanana.nexusutils.item.models.SleepingBagModelRenderer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;

import java.util.ArrayList;
import java.util.function.Function;

public class ModBlocks {
    public static final ArrayList<SleepingBagBlock> SLEEPING_BAGS = new ArrayList<>();

    public static final Block FROZEN_LAVA = registerBlock("frozen_lava", FrozenLavaBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.BLACK)
                    .ticksRandomly()
                    .requiresTool()
                    .strength(25.0f, 600.0f)
                    .sounds(BlockSoundGroup.GLASS)
                    .instrument(NoteBlockInstrument.BASEDRUM),
    false
    );

    public static final Block COPPER_HOPPER = registerBlock("copper_hopper", CopperHopperBlock::new, AbstractBlock.Settings.copy(Blocks.HOPPER));

    public static final Block ITEM_DISPLAY = registerBlock("item_display", ItemDisplayBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS).nonOpaque());

    public static final Block MAIL_BOX = registerBlock("mail_box", MailBoxBlock::new,
            AbstractBlock.Settings.create()
                    .nonOpaque()
                    .mapColor(DyeColor.BLUE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)
    );

    public static final Block POST_BOX = registerBlock("post_box", PostBoxBlock::new,
            AbstractBlock.Settings.create()
                    .nonOpaque()
                    .mapColor(DyeColor.BLUE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)
    );

    public static final Block BLACK_SLEEPING_BAG = registerSleepingBag(DyeColor.BLACK, Blocks.BLACK_WOOL);
    public static final Block BLUE_SLEEPING_BAG = registerSleepingBag(DyeColor.BLUE, Blocks.BLUE_WOOL);
    public static final Block BROWN_SLEEPING_BAG = registerSleepingBag(DyeColor.BROWN, Blocks.BROWN_WOOL);
    public static final Block CYAN_SLEEPING_BAG = registerSleepingBag(DyeColor.CYAN, Blocks.CYAN_WOOL);
    public static final Block GRAY_SLEEPING_BAG = registerSleepingBag(DyeColor.GRAY, Blocks.GRAY_WOOL);
    public static final Block GREEN_SLEEPING_BAG = registerSleepingBag(DyeColor.GREEN, Blocks.GREEN_WOOL);
    public static final Block LIGHT_BLUE_SLEEPING_BAG = registerSleepingBag(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
    public static final Block LIGHT_GRAY_SLEEPING_BAG = registerSleepingBag(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
    public static final Block LIME_SLEEPING_BAG = registerSleepingBag(DyeColor.LIME, Blocks.LIME_WOOL);
    public static final Block MAGENTA_SLEEPING_BAG = registerSleepingBag(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
    public static final Block ORANGE_SLEEPING_BAG = registerSleepingBag(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
    public static final Block PINK_SLEEPING_BAG = registerSleepingBag(DyeColor.PINK, Blocks.PINK_WOOL);
    public static final Block PURPLE_SLEEPING_BAG = registerSleepingBag(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
    public static final Block RED_SLEEPING_BAG = registerSleepingBag(DyeColor.RED, Blocks.RED_WOOL);
    public static final Block WHITE_SLEEPING_BAG = registerSleepingBag(DyeColor.WHITE, Blocks.WHITE_WOOL);
    public static final Block YELLOW_SLEEPING_BAG = registerSleepingBag(DyeColor.YELLOW, Blocks.YELLOW_WOOL);

    public static Block registerSleepingBag(DyeColor color, Block particleSource) {
        Block sleepingBagBlock = registerBlock(
                color.getName() + "_sleeping_bag",
                settings -> new SleepingBagBlock(color, particleSource, settings),
                AbstractBlock.Settings.create()
                        .mapColor(state -> state.get(SleepingBagBlock.PART) == SleepingBagPart.FOOT ? color.getMapColor() : MapColor.WHITE_GRAY)
                        .sounds(BlockSoundGroup.WOOL)
                        .strength(0.2f)
                        .nonOpaque()
                        .burnable()
                        .pistonBehavior(PistonBehavior.DESTROY),
                true,
                true,
                SleepingBagItem::new,
                new Item.Settings().maxCount(1)
        );

        SLEEPING_BAGS.add((SleepingBagBlock) sleepingBagBlock);

        return sleepingBagBlock;
    }
    
    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings blockSettings) {
        return registerBlock(name, blockFactory, blockSettings, true, true);
    }
    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings blockSettings, boolean hasItem) {
        return registerBlock(name, blockFactory, blockSettings, hasItem, false);
    }

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings blockSettings, boolean hasItem, boolean addToCategory) {
        return registerBlock(name, blockFactory, blockSettings, hasItem, addToCategory, new Item.Settings().maxCount(64));
    }

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings blockSettings, boolean hasItem, boolean addToCategory, Item.Settings itemSettings) {
        return registerBlock(name, blockFactory, blockSettings, hasItem, addToCategory, BlockItem::new, itemSettings);
    }

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings blockSettings, boolean hasItem, boolean addToCategory, Function2<Block, Item.Settings, BlockItem> itemFactory, Item.Settings itemSettings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, NexusUtils.idOf(name));
        Block block = blockFactory.apply(blockSettings.registryKey(key));
        if(hasItem) registerBlockItem(name, block, addToCategory, itemFactory, itemSettings);
        return Registry.register(Registries.BLOCK, key, block);
    }

    private static Item registerBlockItem(String name, Block block, boolean addToCategory, Function2<Block, Item.Settings, BlockItem> factory, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, NexusUtils.idOf(name));
        BlockItem blockItemInst = factory.invoke(block, settings.registryKey(key));
        Item registered = Registry.register(Registries.ITEM, key, blockItemInst);
        if(addToCategory) ModItems.addItemToCategory(registered);
        return registered;
    }

    public static void  registerModBlocks() {
        NexusUtils.LOGGER.info("Registering Mod Blocks for " + NexusUtils.MOD_ID);
    }

    public static void registerClientRender() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ITEM_DISPLAY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COPPER_HOPPER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MAIL_BOX, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POST_BOX, RenderLayer.getTranslucent());

        registerSleepingBagsRenders();

        BlockEntityRendererFactories.register(ModBlockEntities.ITEM_DISPLAY_BLOCK_ENTITY, ItemDisplayBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.SLEEPING_BAG_BLOCK_ENTITY, SleepingBagBlockEntityRenderer::new);
    }

    private static void registerSleepingBagsRenders() {
        for (SleepingBagBlock sleepingBagBlock : SLEEPING_BAGS) {
            BlockRenderLayerMap.INSTANCE.putBlock(sleepingBagBlock, RenderLayer.getCutout());

            SpecialModelTypes.ID_MAPPER.put(NexusUtils.idOf("sleeping_bag"), SleepingBagModelRenderer.Unbaked.CODEC);
        }
    }
}
