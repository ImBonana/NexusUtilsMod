package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.FROZEN_LAVA)
                .add(ModBlocks.ITEM_DISPLAY)
                .add(ModBlocks.COPPER_HOPPER)
                .add(ModBlocks.MAIL_BOX)
                .add(ModBlocks.POST_BOX);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.FROZEN_LAVA);

        getOrCreateTagBuilder(ModTags.Blocks.SNAIL_SPAWNABLE)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.MUD)
                .add(Blocks.MANGROVE_ROOTS)
                .add(Blocks.MUDDY_MANGROVE_ROOTS);

        getOrCreateTagBuilder(ModTags.Blocks.SLEEPING_BAGS)
                .add(ModBlocks.BLACK_SLEEPING_BAG)
                .add(ModBlocks.BLUE_SLEEPING_BAG)
                .add(ModBlocks.BROWN_SLEEPING_BAG)
                .add(ModBlocks.CYAN_SLEEPING_BAG)
                .add(ModBlocks.GRAY_SLEEPING_BAG)
                .add(ModBlocks.GREEN_SLEEPING_BAG)
                .add(ModBlocks.LIGHT_BLUE_SLEEPING_BAG)
                .add(ModBlocks.LIGHT_GRAY_SLEEPING_BAG)
                .add(ModBlocks.LIME_SLEEPING_BAG)
                .add(ModBlocks.MAGENTA_SLEEPING_BAG)
                .add(ModBlocks.ORANGE_SLEEPING_BAG)
                .add(ModBlocks.PINK_SLEEPING_BAG)
                .add(ModBlocks.PURPLE_SLEEPING_BAG)
                .add(ModBlocks.RED_SLEEPING_BAG)
                .add(ModBlocks.WHITE_SLEEPING_BAG)
                .add(ModBlocks.YELLOW_SLEEPING_BAG);
    }
}
