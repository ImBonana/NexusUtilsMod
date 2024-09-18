package me.imbanana.nexusutils.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface SitLivingEntityAccessor {
    @Invoker float invokeGetJumpVelocity();
}
