package me.imbanana.nexusutils.enchantment.custom;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class ShockwaveEnchantment extends Enchantment {
    public ShockwaveEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        if(!user.getWorld().isClient()) {
            if(new Random().nextInt(1, 6) == 1) {
                Vec3d rotationVector = attacker.getRotationVector();
                Vec3d pushTo = new Vec3d(rotationVector.getX(), 0, rotationVector.getZ()).multiply(-2).add(0, 1, 0);
                attacker.setVelocity(pushTo);
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
