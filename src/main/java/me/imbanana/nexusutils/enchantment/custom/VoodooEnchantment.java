package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Random;

public class VoodooEnchantment extends Enchantment implements TradableEnchantment {
    public VoodooEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        if(!(attacker instanceof LivingEntity)) return;

        if(new Random().nextInt(1, 11) == 1) {
            ((LivingEntity) attacker).addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 60, 0));
        }
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
        return 35;
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
