package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.ModEnchantments;
import me.imbanana.nexusutils.enchantment.MultipleTargetsEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.Random;

public class PerishEnchantment extends MultipleTargetsEnchantment {
    public PerishEnchantment(Rarity rarity, EnchantmentTarget[] targets, EquipmentSlot... slotTypes) {
        super(rarity, targets, slotTypes);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(target instanceof LivingEntity livingTarget) {
            if(new Random().nextInt(1,6) == 1) {
                livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 160));
            }
            super.onTargetDamaged(user, target, level);
        }
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != ModEnchantments.CHAOS;
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
}
