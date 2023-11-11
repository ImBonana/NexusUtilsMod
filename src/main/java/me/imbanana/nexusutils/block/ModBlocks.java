package me.imbanana.nexusutils.block;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.custom.CopperHopperBlock;
import me.imbanana.nexusutils.block.custom.FrozenLavaBlock;
import me.imbanana.nexusutils.block.custom.ItemDisplayBlock;
import me.imbanana.nexusutils.item.ModItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
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

    
    private static Block registerBlock(String name, Block block) {
        return registerBlock(name, block, true, true);
    }
    private static Block registerBlock(String name, Block block, boolean hasItem) {
        return registerBlock(name, block, hasItem, false);
    }

    private static Block registerBlock(String name, Block block, boolean hasItem, boolean addToCategory) {
        if(hasItem) registerBlockItem(name, block, addToCategory);
        return Registry.register(Registries.BLOCK, new Identifier(NexusUtils.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, boolean addToCategory) {
        Item registered = Registry.register(Registries.ITEM, new Identifier(NexusUtils.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
        if(addToCategory) ModItems.addItemToCategory(registered);
        return registered;
    }

    public static void  registerModBlocks() {
        NexusUtils.LOGGER.info("Registering Mod Blocks for " + NexusUtils.MOD_ID);
    }
}
