package me.imbanana.nexusutils.enchantment.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public record DamageMobsFallBasedEnchantmentEffect(EnchantmentLevelBasedValue radius, EnchantmentLevelBasedValue power, EnchantmentLevelBasedValue maxDamage) implements EnchantmentEntityEffect {
    public static final MapCodec<DamageMobsFallBasedEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    EnchantmentLevelBasedValue.CODEC.fieldOf("radius").forGetter(DamageMobsFallBasedEnchantmentEffect::radius),
                    EnchantmentLevelBasedValue.CODEC.fieldOf("power").forGetter(DamageMobsFallBasedEnchantmentEffect::power),
                    EnchantmentLevelBasedValue.CODEC.fieldOf("maxDamage").forGetter(DamageMobsFallBasedEnchantmentEffect::maxDamage)
            ).apply(instance, DamageMobsFallBasedEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if(context.owner() instanceof LivingEntity) {
            Box myBox = new Box(new BlockPos(context.owner().getBlockPos())).expand(radius.getValue(level));
            List<LivingEntity> entitiyList = world.getEntitiesByClass(LivingEntity.class, myBox, LivingEntity::isAlive);

            float damage = Math.min((context.owner().fallDistance / 2f) * this.power.getValue(level), this.maxDamage.getValue(level));

            for (LivingEntity mobEntity : entitiyList) {
                if(mobEntity.getId() == context.owner().getId()) continue;
                mobEntity.damage(world.getDamageSources().mobAttack(context.owner()), damage);
            }
        }

    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
