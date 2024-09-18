package me.imbanana.nexusutils.enchantment.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record LaunchEntityForwardEnchantmentEffect(EnchantmentLevelBasedValue power) implements EnchantmentEntityEffect {
    public static final MapCodec<LaunchEntityForwardEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    EnchantmentLevelBasedValue.CODEC.fieldOf("power").forGetter(LaunchEntityForwardEnchantmentEffect::power)
            ).apply(instance, LaunchEntityForwardEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if(context.owner() instanceof PlayerEntity player) {
            player.setVelocity(player.getRotationVector().multiply(power.getValue(level)));
            player.velocityModified = true;
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
