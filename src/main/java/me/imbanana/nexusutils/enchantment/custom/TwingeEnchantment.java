package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.effect.ModEffects;
import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;

public class TwingeEnchantment extends Enchantment implements TradableEnchantment {
    public TwingeEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(!(target instanceof LivingEntity)) return;
        LivingEntity livingTarget = (LivingEntity) target;

        livingTarget.addStatusEffect(new StatusEffectInstance(ModEffects.BLEED, (10 + (4*level)) * 20, 0, false, false, true));
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return super.isAcceptableItem(stack) || stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem;
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
        return 45;
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
