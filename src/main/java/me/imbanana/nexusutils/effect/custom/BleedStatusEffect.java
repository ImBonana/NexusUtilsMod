package me.imbanana.nexusutils.effect.custom;

import me.imbanana.nexusutils.damageSources.ModDamageSources;
import me.imbanana.nexusutils.tags.ModEntityTypeTags;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class BleedStatusEffect extends StatusEffect {
    public BleedStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(!entity.getWorld().isClient() && !entity.getType().isIn(ModEntityTypeTags.NO_BLEEDING_APPLY_MOBS) && entity.getGroup() != EntityGroup.UNDEAD)
            entity.damage(ModDamageSources.BLEED.create(entity.getWorld()), 1.0f);
        super.applyUpdateEffect(entity, amplifier);
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
