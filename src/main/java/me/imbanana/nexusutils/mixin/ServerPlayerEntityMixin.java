package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.block.custom.SleepingBagBlock;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(method = "setSpawnPoint", at = @At(value = "HEAD"), cancellable = true)
    private void injectSetSpawnPoint(RegistryKey<World> dimension, @Nullable BlockPos pos, float angle, boolean forced, boolean sendMessage, CallbackInfo info) {
        if(pos == null) return;
        if(((ServerPlayerEntity) (Object) this).getWorld().getBlockState(pos).getBlock() instanceof SleepingBagBlock) info.cancel();
    }
}
