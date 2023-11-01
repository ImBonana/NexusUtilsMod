package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class ImpactEnchantment extends Enchantment implements TradableEnchantment {
    public ImpactEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
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
