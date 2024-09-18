package me.imbanana.nexusutils.enchantment.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record StealFoodEnchantmentEffect(EnchantmentLevelBasedValue foodAmount, EnchantmentLevelBasedValue saturationModifier) implements EnchantmentEntityEffect {
    public static final MapCodec<StealFoodEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    EnchantmentLevelBasedValue.CODEC.fieldOf("foodAmount").forGetter(StealFoodEnchantmentEffect::foodAmount),
                    EnchantmentLevelBasedValue.CODEC.fieldOf("saturationModifier").forGetter(StealFoodEnchantmentEffect::saturationModifier)
            ).apply(instance, StealFoodEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if(target instanceof LivingEntity) {
            if(context.owner() != null && context.owner() instanceof PlayerEntity player) {
                player.getHungerManager().add(Math.round(foodAmount.getValue(level)), saturationModifier.getValue(level));
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
