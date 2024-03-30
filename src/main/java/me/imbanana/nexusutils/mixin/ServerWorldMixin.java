package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.util.MailBox;
import me.imbanana.nexusutils.util.MailDeliveryService;
import me.imbanana.nexusutils.util.accessors.IServerWorld;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.RandomSequencesState;
import net.minecraft.village.raid.RaidManager;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin implements IServerWorld {
    @Shadow public abstract PersistentStateManager getPersistentStateManager();

    @Shadow public abstract ServerWorld toServerWorld();

    @Unique
    private MailDeliveryService mailDeliveryService;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void injectInit(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey worldKey, DimensionOptions dimensionOptions, WorldGenerationProgressListener worldGenerationProgressListener, boolean debugWorld, long seed, List spawners, boolean shouldTickTime, RandomSequencesState randomSequencesState, CallbackInfo ci) {
        mailDeliveryService = this.getPersistentStateManager().getOrCreate(MailDeliveryService.getPersistentStateType(this.toServerWorld()), "mail_boxes");
    }

    @Override
    public MailDeliveryService nexusUtils$getMailDeliveryService() {
        return this.mailDeliveryService;
    }
}
