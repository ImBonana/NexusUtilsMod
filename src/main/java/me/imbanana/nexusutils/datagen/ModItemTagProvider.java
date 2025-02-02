package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS)
                .add(ModItems.PINK_QUARTZ);

        getOrCreateTagBuilder(ModTags.Items.AXES_ENCHANTABLE)
                .forceAddTag(ItemTags.AXES);

        getOrCreateTagBuilder(ModTags.Items.EXPERIENCE_ENCHANTABLE)
                .forceAddTag(ItemTags.MINING_ENCHANTABLE)
                .forceAddTag(ItemTags.WEAPON_ENCHANTABLE)
                .forceAddTag(ItemTags.TRIDENT_ENCHANTABLE);

        getOrCreateTagBuilder(ModTags.Items.HOES_ENCHANTABLE)
                .forceAddTag(ItemTags.HOES);

        getOrCreateTagBuilder(ModTags.Items.ICE_ASPECT_ENCHANTABLE)
                .forceAddTag(ItemTags.SWORD_ENCHANTABLE)
                .add(net.minecraft.item.Items.MACE);

        getOrCreateTagBuilder(ModTags.Items.PICKAXES_ENCHANTABLE)
                .forceAddTag(ItemTags.PICKAXES);

        getOrCreateTagBuilder(ModTags.Items.RANGED_PROJECTILE_ENCHANTABLE)
                .forceAddTag(ItemTags.BOW_ENCHANTABLE)
                .forceAddTag(ItemTags.CROSSBOW_ENCHANTABLE);

        getOrCreateTagBuilder(ModTags.Items.RANGED_WEAPON_ENCHANTABLE)
                .forceAddTag(ItemTags.BOW_ENCHANTABLE)
                .forceAddTag(ItemTags.CROSSBOW_ENCHANTABLE)
                .forceAddTag(ItemTags.TRIDENT_ENCHANTABLE);

        getOrCreateTagBuilder(ModTags.Items.TELEPATHY_ENCHANTABLE)
                .forceAddTag(ItemTags.MINING_ENCHANTABLE)
                .forceAddTag(ItemTags.WEAPON_ENCHANTABLE)
                .forceAddTag(ModTags.Items.RANGED_WEAPON_ENCHANTABLE);

        getOrCreateTagBuilder(ModTags.Items.SLEEPING_BAGS)
                .add(ModBlocks.BLACK_SLEEPING_BAG.asItem())
                .add(ModBlocks.BLUE_SLEEPING_BAG.asItem())
                .add(ModBlocks.BROWN_SLEEPING_BAG.asItem())
                .add(ModBlocks.CYAN_SLEEPING_BAG.asItem())
                .add(ModBlocks.GRAY_SLEEPING_BAG.asItem())
                .add(ModBlocks.GREEN_SLEEPING_BAG.asItem())
                .add(ModBlocks.LIGHT_BLUE_SLEEPING_BAG.asItem())
                .add(ModBlocks.LIGHT_GRAY_SLEEPING_BAG.asItem())
                .add(ModBlocks.LIME_SLEEPING_BAG.asItem())
                .add(ModBlocks.MAGENTA_SLEEPING_BAG.asItem())
                .add(ModBlocks.ORANGE_SLEEPING_BAG.asItem())
                .add(ModBlocks.PINK_SLEEPING_BAG.asItem())
                .add(ModBlocks.PURPLE_SLEEPING_BAG.asItem())
                .add(ModBlocks.RED_SLEEPING_BAG.asItem())
                .add(ModBlocks.WHITE_SLEEPING_BAG.asItem())
                .add(ModBlocks.YELLOW_SLEEPING_BAG.asItem());
    }
}
