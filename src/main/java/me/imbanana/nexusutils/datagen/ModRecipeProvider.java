package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
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
                registerSleepingBags();

                createShaped(RecipeCategory.MISC, ModItems.CRAFTING_ON_A_STICK, 1)
                        .pattern("  C")
                        .pattern(" S ")
                        .input('C', net.minecraft.item.Items.CRAFTING_TABLE)
                        .input('S', net.minecraft.item.Items.STICK)
                        .criterion(hasItem(net.minecraft.item.Items.CRAFTING_TABLE), conditionsFromItem(net.minecraft.item.Items.CRAFTING_TABLE))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.CRAFTING_ON_A_STICK, 1)
                        .pattern("C  ")
                        .pattern(" S ")
                        .input('C', net.minecraft.item.Items.CRAFTING_TABLE)
                        .input('S', net.minecraft.item.Items.STICK)
                        .criterion(hasItem(net.minecraft.item.Items.CRAFTING_TABLE), conditionsFromItem(net.minecraft.item.Items.CRAFTING_TABLE))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("crafting_on_a_stick_mirror")));

                createShaped(RecipeCategory.MISC, ModItems.VOID_TOTEM, 1)
                        .pattern("ECE")
                        .pattern("CTC")
                        .pattern("ECE")
                        .input('E', net.minecraft.item.Items.ENDER_EYE)
                        .input('C', net.minecraft.item.Items.CRYING_OBSIDIAN)
                        .input('T', net.minecraft.item.Items.TOTEM_OF_UNDYING)
                        .criterion(hasItem(net.minecraft.item.Items.TOTEM_OF_UNDYING), conditionsFromItem(net.minecraft.item.Items.TOTEM_OF_UNDYING))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.ITEM_DISPLAY, 1)
                        .pattern("GGG")
                        .pattern("G G")
                        .pattern("SSS")
                        .input('G', net.minecraft.item.Items.GLASS)
                        .input('S', net.minecraft.item.Items.SMOOTH_STONE)
                        .criterion(hasItem(net.minecraft.item.Items.GLASS), conditionsFromItem(net.minecraft.item.Items.GLASS))
                        .criterion(hasItem(net.minecraft.item.Items.SMOOTH_STONE), conditionsFromItem(net.minecraft.item.Items.SMOOTH_STONE))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, net.minecraft.item.Items.STRING, 4)
                        .input(ItemTags.WOOL)
                        .criterion("has_wool_nx", conditionsFromTag(ItemTags.WOOL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.COPPER_HOPPER, 1)
                        .pattern("C C")
                        .pattern("CSC")
                        .pattern(" C ")
                        .input('C', net.minecraft.item.Items.COPPER_INGOT)
                        .input('S', net.minecraft.item.Items.CHEST)
                        .criterion(hasItem(net.minecraft.item.Items.CHEST), conditionsFromItem(net.minecraft.item.Items.CHEST))
                        .criterion(hasItem(net.minecraft.item.Items.COPPER_INGOT), conditionsFromItem(net.minecraft.item.Items.COPPER_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.COPPER_HOPPER, 1)
                        .pattern("CWC")
                        .pattern("CWC")
                        .pattern(" C ")
                        .input('C', net.minecraft.item.Items.COPPER_INGOT)
                        .input('W', ItemTags.LOGS)
                        .criterion(hasItem(net.minecraft.item.Items.CHEST), conditionsFromItem(net.minecraft.item.Items.CHEST))
                        .criterion(hasItem(net.minecraft.item.Items.COPPER_INGOT), conditionsFromItem(net.minecraft.item.Items.COPPER_INGOT))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf("copper_hopper_fast")));

                createShaped(RecipeCategory.MISC, net.minecraft.item.Items.HOPPER, 1)
                        .pattern("CWC")
                        .pattern("CWC")
                        .pattern(" C ")
                        .input('C', net.minecraft.item.Items.IRON_INGOT)
                        .input('W', ItemTags.LOGS)
                        .criterion(hasItem(net.minecraft.item.Items.CHEST), conditionsFromItem(net.minecraft.item.Items.CHEST))
                        .criterion(hasItem(net.minecraft.item.Items.IRON_INGOT), conditionsFromItem(net.minecraft.item.Items.IRON_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.HOPPER_FILTER, 4)
                        .pattern("CSC")
                        .pattern("SSS")
                        .pattern("CSC")
                        .input('S', net.minecraft.item.Items.STRING)
                        .input('C', net.minecraft.item.Items.COPPER_INGOT)
                        .criterion(hasItem(net.minecraft.item.Items.STRING), conditionsFromItem(net.minecraft.item.Items.STRING))
                        .criterion(hasItem(net.minecraft.item.Items.COPPER_INGOT), conditionsFromItem(net.minecraft.item.Items.COPPER_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.BACKPACK, 1)
                        .pattern("LGL")
                        .pattern("ICI")
                        .pattern("LSL")
                        .input('L', net.minecraft.item.Items.LEATHER)
                        .input('G', net.minecraft.item.Items.GOLD_INGOT)
                        .input('I', net.minecraft.item.Items.IRON_INGOT)
                        .input('C', net.minecraft.item.Items.CHEST)
                        .input('S', ModTags.Items.SLEEPING_BAGS)
                        .criterion(hasItem(net.minecraft.item.Items.LEATHER), conditionsFromItem(net.minecraft.item.Items.LEATHER))
                        .criterion(hasItem(net.minecraft.item.Items.CHEST), conditionsFromItem(net.minecraft.item.Items.CHEST))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModTags.Items.SLEEPING_BAGS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.MAIL_BOX, 1)
                        .pattern("ICI")
                        .pattern(" I ")
                        .pattern(" L ")
                        .input('I', net.minecraft.item.Items.IRON_INGOT)
                        .input('C', net.minecraft.item.Items.CHEST)
                        .input('L', net.minecraft.item.Items.LIGHT_BLUE_CONCRETE)
                        .criterion(hasItem(net.minecraft.item.Items.CHEST), conditionsFromItem(net.minecraft.item.Items.CHEST))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.POST_BOX, 1)
                        .pattern("III")
                        .pattern("LCL")
                        .pattern("LPL")
                        .input('I', net.minecraft.item.Items.IRON_INGOT)
                        .input('C', net.minecraft.item.Items.CHEST)
                        .input('L', net.minecraft.item.Items.LIGHT_BLUE_CONCRETE)
                        .input('P', net.minecraft.item.Items.PAPER)
                        .criterion(hasItem(net.minecraft.item.Items.CHEST), conditionsFromItem(net.minecraft.item.Items.CHEST))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ModItems.PINK_QUARTZ)
                        .input(net.minecraft.item.Items.QUARTZ)
                        .input(net.minecraft.item.Items.AMETHYST_SHARD)
                        .criterion(hasItem(net.minecraft.item.Items.QUARTZ), conditionsFromItem(net.minecraft.item.Items.QUARTZ))
                        .criterion(hasItem(net.minecraft.item.Items.AMETHYST_SHARD), conditionsFromItem(net.minecraft.item.Items.AMETHYST_SHARD))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.TERRORIST_DOG, 2)
                        .pattern(" C ")
                        .pattern("WTW")
                        .pattern(" S ")
                        .input('C', net.minecraft.item.Items.COPPER_INGOT)
                        .input('W', ItemTags.WOOL)
                        .input('T', net.minecraft.item.Items.TNT)
                        .input('S', net.minecraft.item.Items.STRING)
                        .criterion(hasItem(net.minecraft.item.Items.TNT), conditionsFromItem(net.minecraft.item.Items.TNT))
                        .criterion(hasItem(net.minecraft.item.Items.COPPER_INGOT), conditionsFromItem(net.minecraft.item.Items.COPPER_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.TERRORIST_DOG_REMOTE, 1)
                        .pattern("III")
                        .pattern("ICI")
                        .pattern("III")
                        .input('C', net.minecraft.item.Items.COPPER_INGOT)
                        .input('I', net.minecraft.item.Items.IRON_INGOT)
                        .criterion(hasItem(net.minecraft.item.Items.TNT), conditionsFromItem(net.minecraft.item.Items.TNT))
                        .criterion(hasItem(net.minecraft.item.Items.COPPER_INGOT), conditionsFromItem(net.minecraft.item.Items.COPPER_INGOT))
                        .criterion(hasItem(net.minecraft.item.Items.IRON_INGOT), conditionsFromItem(net.minecraft.item.Items.IRON_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.BACKPACK_IRON_UPGRADE, 1)
                        .pattern("ILI")
                        .pattern("ICI")
                        .pattern("III")
                        .input('I', net.minecraft.item.Items.IRON_INGOT)
                        .input('L', net.minecraft.item.Items.LEATHER)
                        .input('C', net.minecraft.item.Items.CHEST)
                        .criterion(hasItem(net.minecraft.item.Items.CHEST), conditionsFromItem(net.minecraft.item.Items.CHEST))
                        .criterion(hasItem(net.minecraft.item.Items.LEATHER), conditionsFromItem(net.minecraft.item.Items.LEATHER))
                        .criterion(hasItem(net.minecraft.item.Items.IRON_INGOT), conditionsFromItem(net.minecraft.item.Items.IRON_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.BACKPACK_GOLD_UPGRADE, 1)
                        .pattern("ILI")
                        .pattern("IUI")
                        .pattern("III")
                        .input('I', net.minecraft.item.Items.GOLD_INGOT)
                        .input('L', net.minecraft.item.Items.LEATHER)
                        .input('U', ModItems.BACKPACK_IRON_UPGRADE)
                        .criterion(hasItem(ModItems.BACKPACK_IRON_UPGRADE), conditionsFromItem(ModItems.BACKPACK_IRON_UPGRADE))
                        .criterion(hasItem(net.minecraft.item.Items.LEATHER), conditionsFromItem(net.minecraft.item.Items.LEATHER))
                        .criterion(hasItem(net.minecraft.item.Items.GOLD_INGOT), conditionsFromItem(net.minecraft.item.Items.GOLD_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.BACKPACK_DIAMOND_UPGRADE, 1)
                        .pattern("ILI")
                        .pattern("IUI")
                        .pattern("III")
                        .input('I', net.minecraft.item.Items.DIAMOND)
                        .input('L', net.minecraft.item.Items.LEATHER)
                        .input('U', ModItems.BACKPACK_GOLD_UPGRADE)
                        .criterion(hasItem(ModItems.BACKPACK_GOLD_UPGRADE), conditionsFromItem(ModItems.BACKPACK_GOLD_UPGRADE))
                        .criterion(hasItem(net.minecraft.item.Items.LEATHER), conditionsFromItem(net.minecraft.item.Items.LEATHER))
                        .criterion(hasItem(net.minecraft.item.Items.DIAMOND), conditionsFromItem(net.minecraft.item.Items.DIAMOND))
                        .offerTo(exporter);

                SmithingTransformRecipeJsonBuilder.create(
                                Ingredient.ofItems(net.minecraft.item.Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                                Ingredient.ofItems(ModItems.BACKPACK_DIAMOND_UPGRADE),
                                Ingredient.ofItems(net.minecraft.item.Items.NETHERITE_INGOT),
                                RecipeCategory.MISC,
                                ModItems.BACKPACK_NETHERITE_UPGRADE
                        )
                        .criterion(hasItem(net.minecraft.item.Items.NETHERITE_INGOT), conditionsFromItem(net.minecraft.item.Items.NETHERITE_INGOT))
                        .criterion(hasItem(ModItems.BACKPACK_DIAMOND_UPGRADE), conditionsFromItem(ModItems.BACKPACK_DIAMOND_UPGRADE))
                        .criterion(hasItem(net.minecraft.item.Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(net.minecraft.item.Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModItems.BACKPACK_NETHERITE_UPGRADE).getPath() + "_smithing");
            }

            private void registerSleepingBags() {
                registerSleepingBag(ModBlocks.BLACK_SLEEPING_BAG, net.minecraft.item.Items.BLACK_WOOL, net.minecraft.item.Items.BLACK_DYE);
                registerSleepingBag(ModBlocks.BLUE_SLEEPING_BAG, net.minecraft.item.Items.BLUE_WOOL, net.minecraft.item.Items.BLUE_DYE);
                registerSleepingBag(ModBlocks.BROWN_SLEEPING_BAG, net.minecraft.item.Items.BROWN_WOOL, net.minecraft.item.Items.BROWN_DYE);
                registerSleepingBag(ModBlocks.CYAN_SLEEPING_BAG, net.minecraft.item.Items.CYAN_WOOL, net.minecraft.item.Items.CYAN_DYE);
                registerSleepingBag(ModBlocks.GRAY_SLEEPING_BAG, net.minecraft.item.Items.GRAY_WOOL, net.minecraft.item.Items.GRAY_DYE);
                registerSleepingBag(ModBlocks.GREEN_SLEEPING_BAG, net.minecraft.item.Items.GREEN_WOOL, net.minecraft.item.Items.GREEN_DYE);
                registerSleepingBag(ModBlocks.LIGHT_BLUE_SLEEPING_BAG, net.minecraft.item.Items.LIGHT_BLUE_WOOL, net.minecraft.item.Items.LIGHT_BLUE_DYE);
                registerSleepingBag(ModBlocks.LIGHT_GRAY_SLEEPING_BAG, net.minecraft.item.Items.LIGHT_GRAY_WOOL, net.minecraft.item.Items.LIGHT_GRAY_DYE);
                registerSleepingBag(ModBlocks.LIME_SLEEPING_BAG, net.minecraft.item.Items.LIME_WOOL, net.minecraft.item.Items.LIME_DYE);
                registerSleepingBag(ModBlocks.MAGENTA_SLEEPING_BAG, net.minecraft.item.Items.MAGENTA_WOOL, net.minecraft.item.Items.MAGENTA_DYE);
                registerSleepingBag(ModBlocks.ORANGE_SLEEPING_BAG, net.minecraft.item.Items.ORANGE_WOOL, net.minecraft.item.Items.ORANGE_DYE);
                registerSleepingBag(ModBlocks.PINK_SLEEPING_BAG, net.minecraft.item.Items.PINK_WOOL, net.minecraft.item.Items.PINK_DYE);
                registerSleepingBag(ModBlocks.PURPLE_SLEEPING_BAG, net.minecraft.item.Items.PURPLE_WOOL, net.minecraft.item.Items.PURPLE_DYE);
                registerSleepingBag(ModBlocks.RED_SLEEPING_BAG, net.minecraft.item.Items.RED_WOOL, net.minecraft.item.Items.RED_DYE);
                registerSleepingBag(ModBlocks.WHITE_SLEEPING_BAG, net.minecraft.item.Items.WHITE_WOOL, net.minecraft.item.Items.WHITE_DYE);
                registerSleepingBag(ModBlocks.YELLOW_SLEEPING_BAG, net.minecraft.item.Items.YELLOW_WOOL, net.minecraft.item.Items.YELLOW_DYE);
            }

            private void registerSleepingBag(Block result, Item wool, Item dye) {
                createShaped(RecipeCategory.MISC, result, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("WCC")
                        .input('W', net.minecraft.item.Items.WHITE_WOOL)
                        .input('C', wool)
                        .group("sleeping_bag")
                        .criterion(hasItem(net.minecraft.item.Items.WHITE_WOOL), conditionsFromItem(net.minecraft.item.Items.WHITE_WOOL))
                        .criterion(hasItem(wool), conditionsFromItem(wool))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, result, 1)
                        .input(dye)
                        .input(ModTags.Items.SLEEPING_BAGS)
                        .group("sleeping_bag_recolor")
                        .criterion(hasItem(dye), conditionsFromItem(dye))
                        .criterion("has_sleeping_bag", conditionsFromTag(ModTags.Items.SLEEPING_BAGS))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, NexusUtils.idOf(getRecipeName(result) + "_recolor")));
            }
        };
    }

    @Override
    public String getName() {
        return "nexusutilsRecipeProvider";
    }
}
