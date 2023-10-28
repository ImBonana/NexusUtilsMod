package me.imbanana.nexusutils;

import me.imbanana.nexusutils.events.KeyInputHandler;
import me.imbanana.nexusutils.networking.ModPackets;
import net.fabricmc.api.ClientModInitializer;

public class NexusUtilsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();

        ModPackets.registerS2CPackets();
    }
}
