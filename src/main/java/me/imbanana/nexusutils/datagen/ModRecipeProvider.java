package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CRAFTING_ON_A_STICK, 1)
                .pattern("  C")
                .pattern(" S ")
                .pattern("S  ")
                .input('C', Items.CRAFTING_TABLE)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.CRAFTING_TABLE), conditionsFromItem(Items.CRAFTING_TABLE))
                .offerTo(exporter, new Identifier(NexusUtils.MOD_ID, "crafting_on_stick_recipe_right"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CRAFTING_ON_A_STICK, 1)
                .pattern("C  ")
                .pattern(" S ")
                .pattern("  S")
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
    }
}
