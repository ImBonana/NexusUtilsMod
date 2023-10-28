package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.MultipleTargetsEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class TelepathyEnchantment extends MultipleTargetsEnchantment {
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
}
