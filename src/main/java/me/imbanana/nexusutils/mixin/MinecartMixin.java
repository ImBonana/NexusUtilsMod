package me.imbanana.nexusutils.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecartEntity.class)
public abstract class MinecartMixin extends Entity {
    public MinecartMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "getMaxSpeed", at = @At(value = "RETURN"), cancellable = true)
    private void InjectGetMaxSpeed(CallbackInfoReturnable<Double> info) {
        if(this.getType() == EntityType.MINECART) {
            info.setReturnValue(info.getReturnValue() * 5);
        }
    }
}
