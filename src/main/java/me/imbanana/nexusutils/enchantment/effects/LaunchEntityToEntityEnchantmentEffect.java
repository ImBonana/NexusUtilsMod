package me.imbanana.nexusutils.enchantment.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record LaunchEntityToEntityEnchantmentEffect(EnchantmentLevelBasedValue power) implements EnchantmentEntityEffect {
    public static final MapCodec<LaunchEntityToEntityEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    EnchantmentLevelBasedValue.CODEC.fieldOf("power").forGetter(LaunchEntityToEntityEnchantmentEffect::power)
            ).apply(instance, LaunchEntityToEntityEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if(context.owner() == null) return;
        target.setVelocity(context.owner().getRotationVector().multiply(-this.power.getValue(level)));
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
