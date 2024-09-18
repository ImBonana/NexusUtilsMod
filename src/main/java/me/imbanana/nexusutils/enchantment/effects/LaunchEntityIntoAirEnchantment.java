package me.imbanana.nexusutils.enchantment.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record LaunchEntityIntoAirEnchantment (EnchantmentLevelBasedValue forwardPower, EnchantmentLevelBasedValue upPower) implements EnchantmentEntityEffect {
    public static final MapCodec<LaunchEntityIntoAirEnchantment> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    EnchantmentLevelBasedValue.CODEC.fieldOf("forwardPower").forGetter(LaunchEntityIntoAirEnchantment::forwardPower),
                    EnchantmentLevelBasedValue.CODEC.fieldOf("upPower").forGetter(LaunchEntityIntoAirEnchantment::upPower)
            ).apply(instance, LaunchEntityIntoAirEnchantment::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        Vec3d rotationVec = target.getRotationVector();
        Vec3d pushTo = new Vec3d(rotationVec.getX(), 0, rotationVec.getZ()).multiply(this.forwardPower.getValue(level)).add(0, this.upPower.getValue(level), 0);
        target.setVelocity(pushTo);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}