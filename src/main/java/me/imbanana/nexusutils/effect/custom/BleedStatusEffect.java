package me.imbanana.nexusutils.effect.custom;

import me.imbanana.nexusutils.damageSources.ModDamageTypes;
import me.imbanana.nexusutils.tags.ModTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;

public class BleedStatusEffect extends StatusEffect {
    public BleedStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 10027008);
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if(!entity.getWorld().isClient() && !entity.getType().isIn(ModTags.EntityTypes.NO_BLEEDING)) {
            DamageSource damageSource = new DamageSource(entity.getWorld().getRegistryManager().getOrThrow(RegistryKeys.DAMAGE_TYPE).getOrThrow(ModDamageTypes.BLEED));
            entity.damage(world, damageSource, 1.0f);
        }

        return super.applyUpdateEffect(world, entity, amplifier);
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
