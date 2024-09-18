package me.imbanana.nexusutils;

import me.imbanana.nexusutils.discord.DiscordBot;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class NexusUtilsServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        NexusUtils.LOGGER.info("Started On The Server");
        ServerLifecycleEvents.SERVER_STARTED.register(DiscordBot::initializeDiscordBot);
    }
}
