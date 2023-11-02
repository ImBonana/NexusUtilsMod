package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.MultipleTargetsEnchantment;
import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;

import java.util.Random;

public class LightningEnchantment extends MultipleTargetsEnchantment implements TradableEnchantment {
    public LightningEnchantment(Rarity rarity, EnchantmentTarget[] target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(!user.getWorld().isClient()) {
            if(new Random().nextInt(1, 21) == 1) {
                for (int i = 0; i < level; i++) {
                    LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, user.getWorld());
                    lightning.setPosition(target.getPos());
                    user.getWorld().spawnEntity(lightning);
                }
            }
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
    public int getMaxPrice() {
        return 45;
    }

    @Override
    public int getMinPrice() {
        return 33;
    }

    @Override
    public int getMaxLevelToGet() {
        return this.getMaxLevel();
    }
}
