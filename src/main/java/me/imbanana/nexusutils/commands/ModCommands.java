package me.imbanana.nexusutils.commands;

import me.imbanana.nexusutils.NexusUtils;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import java.util.ArrayList;
import java.util.List;

public class ModCommands {
    private static final List<ICommand> commands = new ArrayList<>(){{
        add(new ReloadRealPlayerConfigCommand());
        add(new RecoverCommand());
    }};

    public static void registerModCommands() {
        NexusUtils.LOGGER.info("Registering Mod Commands for " + NexusUtils.MOD_ID);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            for (ICommand command : commands) {
                if(command.shouldRegister(dispatcher, registryAccess, environment)) {
                    command.register(dispatcher, registryAccess, environment);
                }
            }
        });
    }
}
