package me.imbanana.nexusutils.mixin;


import me.imbanana.nexusutils.NexusUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.ServerLoginPacketListener;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.network.packet.s2c.login.LoginHelloS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.SERVER)
@Mixin(ServerLoginNetworkHandler.class)
public abstract class ServerLoginNetworkHandlerMixin implements ServerLoginPacketListener, TickablePacketListener {
    @Shadow
    @Nullable String profileName;

    @Shadow private volatile ServerLoginNetworkHandler.State state;

    @Shadow @Final
    ClientConnection connection;

    @Shadow @Final
    MinecraftServer server;

    @Shadow @Final private byte[] nonce;

    @Inject(method = "onHello", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerLoginNetworkHandler;startVerify(Lcom/mojang/authlib/GameProfile;)V", shift = At.Shift.BEFORE, ordinal = 1), cancellable = true)
    private void injectOnHello(LoginHelloC2SPacket packet, CallbackInfo ci) {
        if(NexusUtils.REAL_PLAYERS_SERVER_CONFIG.realPlayers().contains(this.profileName)) {
            this.state = ServerLoginNetworkHandler.State.KEY;
            this.connection.send(new LoginHelloS2CPacket("", this.server.getKeyPair().getPublic().getEncoded(), this.nonce, true));
            ci.cancel();
        }
    }
}
