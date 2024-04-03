package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.tags.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        registerSleepingBags(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CRAFTING_ON_A_STICK, 1)
                .pattern("  C")
                .pattern(" S ")
                .input('C', Items.CRAFTING_TABLE)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.CRAFTING_TABLE), conditionsFromItem(Items.CRAFTING_TABLE))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "crafting_on_stick_recipe_right"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CRAFTING_ON_A_STICK, 1)
                .pattern("C  ")
                .pattern(" S ")
                .input('C', Items.CRAFTING_TABLE)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.CRAFTING_TABLE), conditionsFromItem(Items.CRAFTING_TABLE))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "crafting_on_stick_recipe_left"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.MOB_GRABBER, 1)
                .pattern("L L")
                .pattern("LRL")
                .pattern("CCC")
                .input('L', Items.LEATHER)
                .input('R', Items.LEAD)
                .input('C', Items.CRYING_OBSIDIAN)
                .criterion(hasItem(Items.LEAD), conditionsFromItem(Items.LEAD))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "mob_grabber_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.VOID_TOTEM, 1)
                .pattern("ECE")
                .pattern("CTC")
                .pattern("ECE")
                .input('E', Items.ENDER_EYE)
                .input('C', Items.CRYING_OBSIDIAN)
                .input('T', Items.TOTEM_OF_UNDYING)
                .criterion(hasItem(Items.TOTEM_OF_UNDYING), conditionsFromItem(Items.TOTEM_OF_UNDYING))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "void_totem_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ITEM_DISPLAY, 1)
                .pattern("GGG")
                .pattern("G G")
                .pattern("SSS")
                .input('G', Items.GLASS)
                .input('S', Items.SMOOTH_STONE)
                .criterion(hasItem(Items.GLASS), conditionsFromItem(Items.GLASS))
                .criterion(hasItem(Items.SMOOTH_STONE), conditionsFromItem(Items.SMOOTH_STONE))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "item_display_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.STRING, 4)
                .input(ItemTags.WOOL)
                .criterion("has_wool_nx", conditionsFromTag(ItemTags.WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "wool_to_string_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.COPPER_HOPPER, 1)
                .pattern("C C")
                .pattern("CSC")
                .pattern(" C ")
                .input('C', Items.COPPER_INGOT)
                .input('S', Items.CHEST)
                .criterion(hasItem(Items.CHEST), conditionsFromItem(Items.CHEST))
                .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "copper_hopper_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.COPPER_HOPPER, 1)
                .pattern("CWC")
                .pattern("CWC")
                .pattern(" C ")
                .input('C', Items.COPPER_INGOT)
                .input('W', ItemTags.LOGS)
                .criterion(hasItem(Items.CHEST), conditionsFromItem(Items.CHEST))
                .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "copper_hopper_recipe_2"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.HOPPER, 1)
                .pattern("CWC")
                .pattern("CWC")
                .pattern(" C ")
                .input('C', Items.IRON_INGOT)
                .input('W', ItemTags.LOGS)
                .criterion(hasItem(Items.CHEST), conditionsFromItem(Items.CHEST))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "hopper_recipe_2"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.HOPPER_FILTER, 4)
                .pattern("CSC")
                .pattern("SSS")
                .pattern("CSC")
                .input('S', Items.STRING)
                .input('C', Items.COPPER_INGOT)
                .criterion(hasItem(Items.STRING), conditionsFromItem(Items.STRING))
                .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "hopper_filter_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BACKPACK, 1)
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
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "backpack"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MAIL_BOX, 1)
                .pattern("ICI")
                .pattern(" I ")
                .pattern(" L ")
                .input('I', Items.IRON_INGOT)
                .input('C', Items.CHEST)
                .input('L', Items.LIGHT_BLUE_CONCRETE)
                .criterion(hasItem(Items.CHEST), conditionsFromItem(Items.CHEST))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "mail_box"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.POST_BOX, 1)
                .pattern("III")
                .pattern("LCL")
                .pattern("LPL")
                .input('I', Items.IRON_INGOT)
                .input('C', Items.CHEST)
                .input('L', Items.LIGHT_BLUE_CONCRETE)
                .input('P', Items.PAPER)
                .criterion(hasItem(Items.CHEST), conditionsFromItem(Items.CHEST))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "post_box"));
    }

    private void registerSleepingBags(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BLACK_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.BLACK_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.BLACK_WOOL), conditionsFromItem(Items.BLACK_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "black_sleeping_bag_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BLUE_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.BLUE_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.BLUE_WOOL), conditionsFromItem(Items.BLUE_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "blue_sleeping_bag_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BROWN_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.BROWN_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.BROWN_WOOL), conditionsFromItem(Items.BROWN_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "brown_sleeping_bag_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.CYAN_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.CYAN_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.CYAN_WOOL), conditionsFromItem(Items.CYAN_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "cyan_sleeping_bag_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.GRAY_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.GRAY_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.GRAY_WOOL), conditionsFromItem(Items.GRAY_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "gray_sleeping_bag_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.GREEN_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.GREEN_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.GREEN_WOOL), conditionsFromItem(Items.GREEN_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "green_sleeping_bag_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.LIGHT_BLUE_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.LIGHT_BLUE_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.LIGHT_BLUE_WOOL), conditionsFromItem(Items.LIGHT_BLUE_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "light_blue_sleeping_bag_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.LIGHT_GRAY_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.LIGHT_GRAY_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.LIGHT_GRAY_WOOL), conditionsFromItem(Items.LIGHT_GRAY_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "light_gray_sleeping_bag_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.LIME_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.LIME_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.LIME_WOOL), conditionsFromItem(Items.LIME_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "lime_sleeping_bag_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MAGENTA_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.MAGENTA_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.MAGENTA_WOOL), conditionsFromItem(Items.MAGENTA_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "magenta_sleeping_bag_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ORANGE_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.ORANGE_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.ORANGE_WOOL), conditionsFromItem(Items.ORANGE_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "orange_sleeping_bag_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.PINK_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.PINK_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.PINK_WOOL), conditionsFromItem(Items.PINK_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "pink_sleeping_bag_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.PURPLE_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.PURPLE_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.PURPLE_WOOL), conditionsFromItem(Items.PURPLE_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "purple_sleeping_bag_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.RED_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.RED_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.RED_WOOL), conditionsFromItem(Items.RED_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "red_sleeping_bag_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.WHITE_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.WHITE_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "white_sleeping_bag_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.YELLOW_SLEEPING_BAG, 1)
                .pattern("   ")
                .pattern("   ")
                .pattern("WCC")
                .input('W', Items.WHITE_WOOL)
                .input('C', Items.YELLOW_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.YELLOW_WOOL), conditionsFromItem(Items.YELLOW_WOOL))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "yellow_sleeping_bag_recipe"));


        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BLACK_SLEEPING_BAG, 1)
                .input(Items.BLACK_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.BLACK_DYE), conditionsFromItem(Items.BLACK_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "black_sleeping_bag_dye_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BLUE_SLEEPING_BAG, 1)
                .input(Items.BLUE_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.BLUE_DYE), conditionsFromItem(Items.BLUE_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "blue_sleeping_bag_dye_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BROWN_SLEEPING_BAG, 1)
                .input(Items.BROWN_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.BROWN_DYE), conditionsFromItem(Items.BROWN_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "brown_sleeping_bag_dye_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.CYAN_SLEEPING_BAG, 1)
                .input(Items.CYAN_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.CYAN_DYE), conditionsFromItem(Items.CYAN_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "cyan_sleeping_bag_dye_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.GRAY_SLEEPING_BAG, 1)
                .input(Items.GRAY_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.GRAY_DYE), conditionsFromItem(Items.GRAY_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "gray_sleeping_bag_dye_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.GREEN_SLEEPING_BAG, 1)
                .input(Items.GREEN_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.GREEN_DYE), conditionsFromItem(Items.GREEN_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "green_sleeping_bag_dye_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.LIGHT_BLUE_SLEEPING_BAG, 1)
                .input(Items.LIGHT_BLUE_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.LIGHT_BLUE_DYE), conditionsFromItem(Items.LIGHT_BLUE_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "light_blue_sleeping_bag_dye_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.LIGHT_GRAY_SLEEPING_BAG, 1)
                .input(Items.LIGHT_GRAY_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.LIGHT_GRAY_DYE), conditionsFromItem(Items.LIGHT_GRAY_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "light_gray_sleeping_bag_dye_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.LIME_SLEEPING_BAG, 1)
                .input(Items.LIME_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.LIME_DYE), conditionsFromItem(Items.LIME_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "lime_sleeping_bag_dye_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MAGENTA_SLEEPING_BAG, 1)
                .input(Items.MAGENTA_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.MAGENTA_DYE), conditionsFromItem(Items.MAGENTA_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "magenta_sleeping_bag_dye_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ORANGE_SLEEPING_BAG, 1)
                .input(Items.ORANGE_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.ORANGE_DYE), conditionsFromItem(Items.ORANGE_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "orange_sleeping_bag_dye_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.PINK_SLEEPING_BAG, 1)
                .input(Items.PINK_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.PINK_DYE), conditionsFromItem(Items.PINK_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "pink_sleeping_bag_dye_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.PURPLE_SLEEPING_BAG, 1)
                .input(Items.PURPLE_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.PURPLE_DYE), conditionsFromItem(Items.PURPLE_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "purple_sleeping_bag_dye_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.RED_SLEEPING_BAG, 1)
                .input(Items.RED_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.RED_DYE), conditionsFromItem(Items.RED_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "red_sleeping_bag_dye_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.WHITE_SLEEPING_BAG, 1)
                .input(Items.WHITE_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.WHITE_DYE), conditionsFromItem(Items.WHITE_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "white_sleeping_bag_dye_recipe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.YELLOW_SLEEPING_BAG, 1)
                .input(Items.YELLOW_DYE)
                .input(ModItemTags.SLEEPING_BAGS)
                .criterion(hasItem(Items.YELLOW_DYE), conditionsFromItem(Items.YELLOW_DYE))
                .criterion("has_sleeping_bag", conditionsFromTag(ModItemTags.SLEEPING_BAGS))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "yellow_sleeping_bag_dye_recipe"));


    }
}
