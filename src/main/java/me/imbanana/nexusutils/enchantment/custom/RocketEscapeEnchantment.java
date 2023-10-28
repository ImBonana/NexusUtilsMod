package me.imbanana.nexusutils.enchantment.custom;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Vec3d;

public class RocketEscapeEnchantment extends Enchantment {
    public RocketEscapeEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        if(!user.getWorld().isClient()) {
            if(user.getHealth() <= 4.0f && !user.isDead()) {
                Vec3d rotationVec = user.getRotationVector();
                Vec3d pushTo = new Vec3d(rotationVec.getX(), 0, rotationVec.getZ()).multiply(-3).add(0, 2, 0);
                user.setVelocity(pushTo);
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200));
            }
        }
        super.onUserDamaged(user, attacker, level);
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
