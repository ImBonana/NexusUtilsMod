package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.tags.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                registerSleepingBags(exporter);

                createShaped(RecipeCategory.MISC, ModItems.CRAFTING_ON_A_STICK, 1)
                        .pattern("  C")
                        .pattern(" S ")
                        .input('C', Items.CRAFTING_TABLE)
                        .input('S', Items.STICK)
                        .criterion(hasItem(Items.CRAFTING_TABLE), conditionsFromItem(Items.CRAFTING_TABLE))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.CRAFTING_ON_A_STICK, 1)
                        .pattern("C  ")
                        .pattern(" S ")
                        .input('C', Items.CRAFTING_TABLE)
                        .input('S', Items.STICK)
                        .criterion(hasItem(Items.CRAFTING_TABLE), conditionsFromItem(Items.CRAFTING_TABLE))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("crafting_on_a_stick_mirror")));

                createShaped(RecipeCategory.MISC, ModItems.VOID_TOTEM, 1)
                        .pattern("ECE")
                        .pattern("CTC")
                        .pattern("ECE")
                        .input('E', Items.ENDER_EYE)
                        .input('C', Items.CRYING_OBSIDIAN)
                        .input('T', Items.TOTEM_OF_UNDYING)
                        .criterion(hasItem(Items.TOTEM_OF_UNDYING), conditionsFromItem(Items.TOTEM_OF_UNDYING))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.ITEM_DISPLAY, 1)
                        .pattern("GGG")
                        .pattern("G G")
                        .pattern("SSS")
                        .input('G', Items.GLASS)
                        .input('S', Items.SMOOTH_STONE)
                        .criterion(hasItem(Items.GLASS), conditionsFromItem(Items.GLASS))
                        .criterion(hasItem(Items.SMOOTH_STONE), conditionsFromItem(Items.SMOOTH_STONE))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, Items.STRING, 4)
                        .input(ItemTags.WOOL)
                        .criterion("has_wool_nx", conditionsFromTag(ItemTags.WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.COPPER_HOPPER, 1)
                        .pattern("C C")
                        .pattern("CSC")
                        .pattern(" C ")
                        .input('C', Items.COPPER_INGOT)
                        .input('S', Items.CHEST)
                        .criterion(hasItem(Items.CHEST), conditionsFromItem(Items.CHEST))
                        .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.COPPER_HOPPER, 1)
                        .pattern("CWC")
                        .pattern("CWC")
                        .pattern(" C ")
                        .input('C', Items.COPPER_INGOT)
                        .input('W', ItemTags.LOGS)
                        .criterion(hasItem(Items.CHEST), conditionsFromItem(Items.CHEST))
                        .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("copper_hopper_fast")));

                createShaped(RecipeCategory.MISC, Items.HOPPER, 1)
                        .pattern("CWC")
                        .pattern("CWC")
                        .pattern(" C ")
                        .input('C', Items.IRON_INGOT)
                        .input('W', ItemTags.LOGS)
                        .criterion(hasItem(Items.CHEST), conditionsFromItem(Items.CHEST))
                        .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.HOPPER_FILTER, 4)
                        .pattern("CSC")
                        .pattern("SSS")
                        .pattern("CSC")
                        .input('S', Items.STRING)
                        .input('C', Items.COPPER_INGOT)
                        .criterion(hasItem(Items.STRING), conditionsFromItem(Items.STRING))
                        .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.BACKPACK, 1)
                        .pattern("LGL")
                        .pattern("ICI")
                        .pattern("LSL")
                        .input('L', Items.LEATHER)
                        .input('G', Items.GOLD_INGOT)
                        .input('I', Items.IRON_INGOT)
                        .input('C', Items.CHEST)
                        .input('S', ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                        .criterion(hasItem(Items.CHEST), conditionsFromItem(Items.CHEST))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.MAIL_BOX, 1)
                        .pattern("ICI")
                        .pattern(" I ")
                        .pattern(" L ")
                        .input('I', Items.IRON_INGOT)
                        .input('C', Items.CHEST)
                        .input('L', Items.LIGHT_BLUE_CONCRETE)
                        .criterion(hasItem(Items.CHEST), conditionsFromItem(Items.CHEST))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.POST_BOX, 1)
                        .pattern("III")
                        .pattern("LCL")
                        .pattern("LPL")
                        .input('I', Items.IRON_INGOT)
                        .input('C', Items.CHEST)
                        .input('L', Items.LIGHT_BLUE_CONCRETE)
                        .input('P', Items.PAPER)
                        .criterion(hasItem(Items.CHEST), conditionsFromItem(Items.CHEST))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ModItems.PINK_QUARTZ)
                        .input(Items.QUARTZ)
                        .input(Items.AMETHYST_SHARD)
                        .criterion(hasItem(Items.QUARTZ), conditionsFromItem(Items.QUARTZ))
                        .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.TERRORIST_DOG, 2)
                        .pattern(" C ")
                        .pattern("WTW")
                        .pattern(" S ")
                        .input('C', Items.COPPER_INGOT)
                        .input('W', ItemTags.WOOL)
                        .input('T', Items.TNT)
                        .input('S', Items.STRING)
                        .criterion(hasItem(Items.TNT), conditionsFromItem(Items.TNT))
                        .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.TERRORIST_DOG_REMOTE, 1)
                        .pattern("III")
                        .pattern("ICI")
                        .pattern("III")
                        .input('C', Items.COPPER_INGOT)
                        .input('I', Items.IRON_INGOT)
                        .criterion(hasItem(Items.TNT), conditionsFromItem(Items.TNT))
                        .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                        .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.BACKPACK_IRON_UPGRADE, 1)
                        .pattern("ILI")
                        .pattern("ICI")
                        .pattern("III")
                        .input('I', Items.IRON_INGOT)
                        .input('L', Items.LEATHER)
                        .input('C', Items.CHEST)
                        .criterion(hasItem(Items.CHEST), conditionsFromItem(Items.CHEST))
                        .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                        .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.BACKPACK_GOLD_UPGRADE, 1)
                        .pattern("ILI")
                        .pattern("IUI")
                        .pattern("III")
                        .input('I', Items.GOLD_INGOT)
                        .input('L', Items.LEATHER)
                        .input('U', ModItems.BACKPACK_IRON_UPGRADE)
                        .criterion(hasItem(ModItems.BACKPACK_IRON_UPGRADE), conditionsFromItem(ModItems.BACKPACK_IRON_UPGRADE))
                        .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                        .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.BACKPACK_DIAMOND_UPGRADE, 1)
                        .pattern("ILI")
                        .pattern("IUI")
                        .pattern("III")
                        .input('I', Items.DIAMOND)
                        .input('L', Items.LEATHER)
                        .input('U', ModItems.BACKPACK_GOLD_UPGRADE)
                        .criterion(hasItem(ModItems.BACKPACK_GOLD_UPGRADE), conditionsFromItem(ModItems.BACKPACK_GOLD_UPGRADE))
                        .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                        .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                        .offerTo(exporter);

                SmithingTransformRecipeJsonBuilder.create(
                                Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                                Ingredient.ofItems(ModItems.BACKPACK_DIAMOND_UPGRADE),
                                Ingredient.ofItems(Items.NETHERITE_INGOT),
                                RecipeCategory.MISC,
                                ModItems.BACKPACK_NETHERITE_UPGRADE
                        )
                        .criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem(Items.NETHERITE_INGOT))
                        .criterion(hasItem(ModItems.BACKPACK_DIAMOND_UPGRADE), conditionsFromItem(ModItems.BACKPACK_DIAMOND_UPGRADE))
                        .criterion(hasItem(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModItems.BACKPACK_NETHERITE_UPGRADE).getPath() + "_smithing");
            }

            private void registerSleepingBags(RecipeExporter exporter) {
                createShaped(RecipeCategory.MISC, ModBlocks.BLACK_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.BLACK_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.BLACK_WOOL), conditionsFromItem(Items.BLACK_WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.BLUE_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.BLUE_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.BLUE_WOOL), conditionsFromItem(Items.BLUE_WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.BROWN_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.BROWN_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.BROWN_WOOL), conditionsFromItem(Items.BROWN_WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.CYAN_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.CYAN_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.CYAN_WOOL), conditionsFromItem(Items.CYAN_WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.GRAY_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.GRAY_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.GRAY_WOOL), conditionsFromItem(Items.GRAY_WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.GREEN_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.GREEN_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.GREEN_WOOL), conditionsFromItem(Items.GREEN_WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.LIGHT_BLUE_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.LIGHT_BLUE_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.LIGHT_BLUE_WOOL), conditionsFromItem(Items.LIGHT_BLUE_WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.LIGHT_GRAY_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.LIGHT_GRAY_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.LIGHT_GRAY_WOOL), conditionsFromItem(Items.LIGHT_GRAY_WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.LIME_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.LIME_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.LIME_WOOL), conditionsFromItem(Items.LIME_WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.MAGENTA_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.MAGENTA_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.MAGENTA_WOOL), conditionsFromItem(Items.MAGENTA_WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.ORANGE_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.ORANGE_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.ORANGE_WOOL), conditionsFromItem(Items.ORANGE_WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.PINK_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.PINK_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.PINK_WOOL), conditionsFromItem(Items.PINK_WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.PURPLE_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.PURPLE_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.PURPLE_WOOL), conditionsFromItem(Items.PURPLE_WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.RED_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.RED_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.RED_WOOL), conditionsFromItem(Items.RED_WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.WHITE_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.WHITE_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.YELLOW_SLEEPING_BAG, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', Items.WHITE_WOOL)
                        .input('C', Items.YELLOW_WOOL)
                        .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                        .criterion(hasItem(Items.YELLOW_WOOL), conditionsFromItem(Items.YELLOW_WOOL))
                        .offerTo(exporter);


                createShapeless(RecipeCategory.MISC, ModBlocks.BLACK_SLEEPING_BAG, 1)
                        .input(Items.BLACK_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.BLACK_DYE), conditionsFromItem(Items.BLACK_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("black_sleeping_bag_recolor")));

                createShapeless(RecipeCategory.MISC, ModBlocks.BLUE_SLEEPING_BAG, 1)
                        .input(Items.BLUE_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.BLUE_DYE), conditionsFromItem(Items.BLUE_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("blue_sleeping_bag_recolor")));

                createShapeless(RecipeCategory.MISC, ModBlocks.BROWN_SLEEPING_BAG, 1)
                        .input(Items.BROWN_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.BROWN_DYE), conditionsFromItem(Items.BROWN_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("brown_sleeping_bag_recolor")));

                createShapeless(RecipeCategory.MISC, ModBlocks.CYAN_SLEEPING_BAG, 1)
                        .input(Items.CYAN_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.CYAN_DYE), conditionsFromItem(Items.CYAN_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("cyan_sleeping_bag_recolor")));

                createShapeless(RecipeCategory.MISC, ModBlocks.GRAY_SLEEPING_BAG, 1)
                        .input(Items.GRAY_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.GRAY_DYE), conditionsFromItem(Items.GRAY_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("gray_sleeping_bag_recolor")));

                createShapeless(RecipeCategory.MISC, ModBlocks.GREEN_SLEEPING_BAG, 1)
                        .input(Items.GREEN_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.GREEN_DYE), conditionsFromItem(Items.GREEN_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("green_sleeping_bag_recolor")));

                createShapeless(RecipeCategory.MISC, ModBlocks.LIGHT_BLUE_SLEEPING_BAG, 1)
                        .input(Items.LIGHT_BLUE_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.LIGHT_BLUE_DYE), conditionsFromItem(Items.LIGHT_BLUE_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("light_blue_sleeping_bag_recolor")));

                createShapeless(RecipeCategory.MISC, ModBlocks.LIGHT_GRAY_SLEEPING_BAG, 1)
                        .input(Items.LIGHT_GRAY_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.LIGHT_GRAY_DYE), conditionsFromItem(Items.LIGHT_GRAY_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("light_gray_sleeping_bag_recolor")));

                createShapeless(RecipeCategory.MISC, ModBlocks.LIME_SLEEPING_BAG, 1)
                        .input(Items.LIME_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.LIME_DYE), conditionsFromItem(Items.LIME_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("lime_sleeping_bag_recolor")));

                createShapeless(RecipeCategory.MISC, ModBlocks.MAGENTA_SLEEPING_BAG, 1)
                        .input(Items.MAGENTA_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.MAGENTA_DYE), conditionsFromItem(Items.MAGENTA_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("magenta_sleeping_bag_recolor")));

                createShapeless(RecipeCategory.MISC, ModBlocks.ORANGE_SLEEPING_BAG, 1)
                        .input(Items.ORANGE_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.ORANGE_DYE), conditionsFromItem(Items.ORANGE_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("orange_sleeping_bag_recolor")));

                createShapeless(RecipeCategory.MISC, ModBlocks.PINK_SLEEPING_BAG, 1)
                        .input(Items.PINK_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.PINK_DYE), conditionsFromItem(Items.PINK_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("pink_sleeping_bag_recolor")));

                createShapeless(RecipeCategory.MISC, ModBlocks.PURPLE_SLEEPING_BAG, 1)
                        .input(Items.PURPLE_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.PURPLE_DYE), conditionsFromItem(Items.PURPLE_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("purple_sleeping_bag_recolor")));

                createShapeless(RecipeCategory.MISC, ModBlocks.RED_SLEEPING_BAG, 1)
                        .input(Items.RED_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.RED_DYE), conditionsFromItem(Items.RED_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("red_sleeping_bag_recolor")));

                createShapeless(RecipeCategory.MISC, ModBlocks.WHITE_SLEEPING_BAG, 1)
                        .input(Items.WHITE_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.WHITE_DYE), conditionsFromItem(Items.WHITE_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("white_sleeping_bag_recolor")));

                createShapeless(RecipeCategory.MISC, ModBlocks.YELLOW_SLEEPING_BAG, 1)
                        .input(Items.YELLOW_DYE)
                        .input(ModItemTags.SLEEPING_BAGS)
                        .criterion(hasItem(Items.YELLOW_DYE), conditionsFromItem(Items.YELLOW_DYE))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("yellow_sleeping_bag_recolor")));
            }
        };
    }

    @Override
    public String getName() {
        return "nexusutilsRecipeProvider";
    }
}
