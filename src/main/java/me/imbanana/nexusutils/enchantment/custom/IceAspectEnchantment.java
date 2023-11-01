package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.Random;

public class IceAspectEnchantment extends Enchantment implements TradableEnchantment {
    public IceAspectEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(!(target instanceof LivingEntity)) return;

        LivingEntity livingTarget = (LivingEntity) target;

        if(new Random().nextInt(1, 6) <= level) {
            livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 30 + (level * 30), level-1));
        }

        super.onTargetDamaged(user, target, level);
    }

    @Override
    public int getMaxLevel() {
        return 2;
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
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Enchantments.FIRE_ASPECT;
    }

    @Override
    public int getMaxPrice() {
        return 50;
    }

    @Override
    public int getMinPrice() {
        return 30;
    }

    @Override
    public int getMaxLevelToGet() {
        return this.getMaxLevel();
    }
}
