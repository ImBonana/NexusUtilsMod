package me.imbanana.nexusutils.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public abstract class MultipleTargetsEnchantment extends Enchantment {
    EnchantmentTarget[] enchantmentTargets;
    public MultipleTargetsEnchantment(Rarity rarity, EnchantmentTarget[] targets, EquipmentSlot... slotTypes) {
        super(rarity, targets[0], slotTypes);
        this.enchantmentTargets = targets;
    }

    public EnchantmentTarget[] getEnchantmentTargets() {
        return this.enchantmentTargets;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        if(getEnchantmentTargets() != null) {
            for (EnchantmentTarget t : getEnchantmentTargets()) {
                if (t.isAcceptableItem(stack.getItem())) return true;
            }
        }

        return super.isAcceptableItem(stack);
    }
}
