package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.block.custom.SleepingBagBlock;
import me.imbanana.nexusutils.block.enums.SleepingBagPart;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.predicate.StatePredicate;

public class ModLootTableProvider extends FabricBlockLootTableProvider {

    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.ITEM_DISPLAY);
        addDrop(ModBlocks.COPPER_HOPPER);

        addSleepingBagDrop(ModBlocks.BLACK_SLEEPING_BAG);
        addSleepingBagDrop(ModBlocks.BLUE_SLEEPING_BAG);
        addSleepingBagDrop(ModBlocks.BROWN_SLEEPING_BAG);
        addSleepingBagDrop(ModBlocks.CYAN_SLEEPING_BAG);
        addSleepingBagDrop(ModBlocks.GRAY_SLEEPING_BAG);
        addSleepingBagDrop(ModBlocks.GREEN_SLEEPING_BAG);
        addSleepingBagDrop(ModBlocks.LIGHT_BLUE_SLEEPING_BAG);
        addSleepingBagDrop(ModBlocks.LIGHT_GRAY_SLEEPING_BAG);
        addSleepingBagDrop(ModBlocks.LIME_SLEEPING_BAG);
        addSleepingBagDrop(ModBlocks.MAGENTA_SLEEPING_BAG);
        addSleepingBagDrop(ModBlocks.ORANGE_SLEEPING_BAG);
        addSleepingBagDrop(ModBlocks.PINK_SLEEPING_BAG);
        addSleepingBagDrop(ModBlocks.PURPLE_SLEEPING_BAG);
        addSleepingBagDrop(ModBlocks.RED_SLEEPING_BAG);
        addSleepingBagDrop(ModBlocks.WHITE_SLEEPING_BAG);
        addSleepingBagDrop(ModBlocks.YELLOW_SLEEPING_BAG);
    }

    private void addSleepingBagDrop(Block sleepingBagBlock) {
        addDrop(sleepingBagBlock, dropsWithProperty(sleepingBagBlock, SleepingBagBlock.PART, SleepingBagPart.HEAD));

    }
}
