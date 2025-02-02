package me.imbanana.nexusutils.recipes;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {
    public static final RecipeSerializer<BackpackUpgradeRecipe> BACKPACK_UPGRADE = register("crafting_special_backpack_upgrade", new SpecialCraftingRecipe.SpecialRecipeSerializer<>(BackpackUpgradeRecipe::new));

    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, NexusUtils.idOf(id), serializer);
    }

    public static void registerModRecipes() {
        NexusUtils.LOGGER.info("Registering Mod Recipes for " + NexusUtils.MOD_ID);
    }
}
