package me.imbanana.nexusutils.block.entity;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<ItemDisplayBlockEntity> ITEM_DISPLAY_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(NexusUtils.MOD_ID, "item_display_be"),
                    FabricBlockEntityTypeBuilder.create(ItemDisplayBlockEntity::new, ModBlocks.ITEM_DISPLAY).build());

    public static final BlockEntityType<CopperHopperBlockEntity> COPPER_HOPPER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(NexusUtils.MOD_ID, "copper_hopper_be"),
                    FabricBlockEntityTypeBuilder.create(CopperHopperBlockEntity::new, ModBlocks.COPPER_HOPPER).build());

    public static final BlockEntityType<SleepingBagBlockEntity> SLEEPING_BAG_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(NexusUtils.MOD_ID, "sleeping_bag_be"),
                    FabricBlockEntityTypeBuilder.create(
                            SleepingBagBlockEntity::new,

                            ModBlocks.BLACK_SLEEPING_BAG,
                            ModBlocks.BLUE_SLEEPING_BAG,
                            ModBlocks.BROWN_SLEEPING_BAG,
                            ModBlocks.CYAN_SLEEPING_BAG,
                            ModBlocks.GRAY_SLEEPING_BAG,
                            ModBlocks.GREEN_SLEEPING_BAG,
                            ModBlocks.LIGHT_BLUE_SLEEPING_BAG,
                            ModBlocks.LIGHT_GRAY_SLEEPING_BAG,
                            ModBlocks.LIME_SLEEPING_BAG,
                            ModBlocks.MAGENTA_SLEEPING_BAG,
                            ModBlocks.ORANGE_SLEEPING_BAG,
                            ModBlocks.PINK_SLEEPING_BAG,
                            ModBlocks.PURPLE_SLEEPING_BAG,
                            ModBlocks.RED_SLEEPING_BAG,
                            ModBlocks.WHITE_SLEEPING_BAG,
                            ModBlocks.YELLOW_SLEEPING_BAG
                    ).build());


    public static void registerBlockEntities() {
        NexusUtils.LOGGER.info("Registering Block Entities for " + NexusUtils.MOD_ID);
    }
}
