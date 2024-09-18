package me.imbanana.nexusutils.networking.packets;

import io.wispforest.owo.network.ServerAccess;
import me.imbanana.nexusutils.util.SitHandler;

public record SitPacket() {
    public static void receiveServer(SitPacket message, ServerAccess access) {
        access.runtime().execute(() -> SitHandler.tryToSit(access.player()));
    }
}
