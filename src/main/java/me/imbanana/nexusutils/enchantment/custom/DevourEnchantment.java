package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

public class DevourEnchantment extends Enchantment implements TradableEnchantment {
    public DevourEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return super.isAcceptableItem(stack) || stack.getItem() instanceof AxeItem;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public int getMaxPrice() {
        return 40;
    }

    @Override
    public int getMinPrice() {
        return 25;
    }

    @Override
    public int getMaxLevelToGet() {
        return this.getMaxLevel();
    }
}
