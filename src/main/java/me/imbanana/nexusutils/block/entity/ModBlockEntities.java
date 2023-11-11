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


    public static void registerBlockEntities() {
        NexusUtils.LOGGER.info("Registering Block Entities for " + NexusUtils.MOD_ID);
    }
}
