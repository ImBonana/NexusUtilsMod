package me.imbanana.nexusutils.mixin.enchantments;

import me.imbanana.nexusutils.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.MilkBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(MilkBucketItem.class)
public abstract class EnchantmentMilkBucketItemMixin {

    @Redirect(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"))
    public boolean RedirectClearEffects(LivingEntity instance) {
        StatusEffectInstance statusEffectInstance = instance.getStatusEffect(ModEffects.MORE_HEALTH);
        boolean result = clearAllEffects(instance, ModEffects.MORE_HEALTH);
        if(statusEffectInstance != null) instance.addStatusEffect(statusEffectInstance);

        return result;
    }

    @Unique
    boolean clearAllEffects(LivingEntity livingEntity, StatusEffect... effectsToKeep) {
        if (livingEntity.getWorld().isClient) return false;

        List<StatusEffectInstance> effectsToRemove = new ArrayList<>();

        for(StatusEffectInstance statusEffectInstance : livingEntity.getStatusEffects())
            if(!Arrays.stream(effectsToKeep).toList().contains(statusEffectInstance.getEffectType())) effectsToRemove.add(statusEffectInstance);


        for(StatusEffectInstance statusEffectInstance : effectsToRemove)
            livingEntity.removeStatusEffect(statusEffectInstance.getEffectType());

        return true;
    }
}
