package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.ModEnchantments;
import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;

public class BlastEnchantment extends Enchantment implements TradableEnchantment {
    public BlastEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof PickaxeItem;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != ModEnchantments.TIMBER && other != ModEnchantments.ORE_EXCAVATION;
    }

    @Override
    public int getMaxLevel() {
        return 1;
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
        return 64;
    }

    @Override
    public int getMinPrice() {
        return 35;
    }

    @Override
    public int getMaxLevelToGet() {
        return this.getMaxLevel();
    }
}
