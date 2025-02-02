package me.imbanana.nexusutils.recipes;

import me.imbanana.nexusutils.components.ModComponents;
import me.imbanana.nexusutils.components.custom.BackpackTierComponent;
import me.imbanana.nexusutils.item.backpack.BackpackItem;
import me.imbanana.nexusutils.item.custom.BackpackUpgradeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class BackpackUpgradeRecipe extends SpecialCraftingRecipe {
    public BackpackUpgradeRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        if(input.getStackCount() < 2) return false;

        BackpackTierComponent.Tier backpackTier = null;
        BackpackTierComponent.Tier upgradeTier = null;

        for (int i = 0; i < input.size(); i++) {
            ItemStack itemStack = input.getStackInSlot(i);
            if(itemStack.isEmpty()) continue;

            if(itemStack.getItem() instanceof BackpackItem && itemStack.contains(ModComponents.BACKPACK_TIER)) {
                if(backpackTier != null) return false;
                backpackTier = itemStack.get(ModComponents.BACKPACK_TIER).tier();
            } else if(itemStack.getItem() instanceof BackpackUpgradeItem upgradeItem) {
                if(upgradeTier != null) return false;
                upgradeTier = upgradeItem.getTier();
            } else {
                return false;
            }
        }

        if(backpackTier == null || upgradeTier == null) return false;

        return backpackTier.asNumber() < upgradeTier.asNumber();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        ItemStack result = ItemStack.EMPTY;
        BackpackUpgradeItem upgradeItem = null;

        for (int i = 0; i < input.size(); i++) {
            ItemStack itemStack = input.getStackInSlot(i);

            if(itemStack.isEmpty()) continue;

            if(itemStack.getItem() instanceof BackpackItem && itemStack.contains(ModComponents.BACKPACK_TIER)) {
                if(!result.isEmpty()) return ItemStack.EMPTY;
                result = itemStack;
            } else if(itemStack.getItem() instanceof BackpackUpgradeItem upgradeItemStack) {
                if(upgradeItem != null) return ItemStack.EMPTY;
                upgradeItem = upgradeItemStack;
            } else {
                return ItemStack.EMPTY;
            }
        }

        if(result.isEmpty() || upgradeItem == null) return ItemStack.EMPTY;

        BackpackTierComponent.Tier backpackTier = result.get(ModComponents.BACKPACK_TIER).tier();
        BackpackTierComponent.Tier upgradeTier = upgradeItem.getTier();
        if(backpackTier.asNumber() >= upgradeTier.asNumber()) return ItemStack.EMPTY;

        result = result.copy();
        result.set(ModComponents.BACKPACK_TIER, new BackpackTierComponent(upgradeTier));

        return result;
    }

    @Override
    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipes.BACKPACK_UPGRADE;
    }
}
