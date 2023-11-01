package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class EnderSlayerEnchantment extends Enchantment implements TradableEnchantment {
    public EnderSlayerEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 5;
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
        return 50;
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
