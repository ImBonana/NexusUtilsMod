package me.imbanana.nexusutils.mixin.sit;

import me.imbanana.nexusutils.util.SitHandler;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class SitLivingEntityDismountMixin extends Entity implements Attackable {
    public SitLivingEntityDismountMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onDismounted", at = @At(value = "TAIL"))
    protected void InjectOnDismount(Entity vehicle, CallbackInfo ci) {
        if(!(vehicle instanceof DisplayEntity.TextDisplayEntity)) return;
        SitHandler.removeSeat(vehicle.getId());
        this.requestTeleport(vehicle.getBlockX(), vehicle.getBlockY() + 1, vehicle.getBlockZ());
    }
}
