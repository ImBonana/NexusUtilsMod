package me.imbanana.nexusutils.effect.custom;

import me.imbanana.nexusutils.damageSources.ModDamageSources;
import me.imbanana.nexusutils.tags.ModEntityTypeTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.RegistryKeys;

public class BleedStatusEffect extends StatusEffect {
    public BleedStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 10027008);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(!entity.getWorld().isClient() && !entity.getType().isIn(ModEntityTypeTags.NO_BLEEDING_APPLY_MOBS)) {
            DamageSource damageSource = new DamageSource(entity.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(ModDamageSources.BLEED));
            entity.damage(damageSource, 1.0f);
        }

        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 40 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        }
        return true;
    }
}
