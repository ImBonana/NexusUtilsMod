package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.ModEnchantments;
import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.Random;

public class ChaosEnchantment extends Enchantment implements TradableEnchantment {
    public ChaosEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(!(target instanceof LivingEntity livingTarget)) return;

        if(new Random().nextInt(1, 11) == 1) {
            livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 60, 0));
            livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 0));
        }
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) &&  other != ModEnchantments.PERISH;
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
