package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.MultipleTargetsEnchantment;
import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class TelepathyEnchantment extends MultipleTargetsEnchantment implements TradableEnchantment {
    public TelepathyEnchantment(Rarity rarity, EnchantmentTarget[] targets, EquipmentSlot... slotTypes) {
        super(rarity, targets, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public int getMaxPrice() {
        return 45;
    }

    @Override
    public int getMinPrice() {
        return 27;
    }

    @Override
    public int getMaxLevelToGet() {
        return this.getMaxLevel();
    }
}
