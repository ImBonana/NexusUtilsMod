package me.imbanana.nexusutils.enchantment.effects;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public record SwingHandEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<SwingHandEnchantmentEffect> CODEC = MapCodec.unit(SwingHandEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if(context.owner() instanceof PlayerEntity player) {
            Hand hand = null;

            if(context.slot() == EquipmentSlot.MAINHAND) hand = Hand.MAIN_HAND;
            if(context.slot() == EquipmentSlot.OFFHAND) hand = Hand.OFF_HAND;
            if(hand == null) return;

            player.swingHand(hand, !player.getWorld().isClient);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
