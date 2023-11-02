package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.Random;

public class DisappearEnchantment extends Enchantment implements TradableEnchantment {
    public DisappearEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        if(!user.getWorld().isClient()) {
            if (user.getHealth() <= 4.0f && !user.isDead()) {
                if (new Random().nextInt(1, 5) == 1) {
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 600));
                }
            }
        }

        super.onUserDamaged(user, attacker, level);
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
        return 50;
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
