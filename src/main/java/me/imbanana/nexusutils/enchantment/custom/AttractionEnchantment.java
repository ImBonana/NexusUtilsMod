package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.MultipleTargetsEnchantment;
import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class AttractionEnchantment extends MultipleTargetsEnchantment implements TradableEnchantment {
    public AttractionEnchantment(Rarity rarity, EnchantmentTarget[] targets, EquipmentSlot... slotTypes) {
        super(rarity, targets, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(user.getWorld().isClient()) return;
        target.setVelocity(user.getRotationVector().multiply(-level));
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Enchantments.PUNCH;
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
        return 30;
    }

    @Override
    public int getMinPrice() {
        return 15;
    }

    @Override
    public int getMaxLevelToGet() {
        return this.getMaxLevel();
    }
}
