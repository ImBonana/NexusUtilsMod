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

public record ItemCooldownEnchantmentEffect(EnchantmentLevelBasedValue cooldown) implements EnchantmentEntityEffect {
    public static final MapCodec<ItemCooldownEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    EnchantmentLevelBasedValue.CODEC.fieldOf("cooldown").forGetter(ItemCooldownEnchantmentEffect::cooldown)
            ).apply(instance, ItemCooldownEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if(context.owner() instanceof PlayerEntity player) {
            player.getItemCooldownManager().set(context.stack().getItem(), (int) this.cooldown.getValue(level));
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
