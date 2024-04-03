package me.imbanana.nexusutils.commands;

import com.mojang.brigadier.CommandDispatcher;
import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.*;

public class ReloadRealPlayerConfigCommand implements ICommand {
    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(
                literal("nexus-reload")
                        .requires(source -> source.hasPermissionLevel(2))
                        .executes(context -> {
                            NexusUtils.REAL_PLAYERS_SERVER_CONFIG.load();
                            context.getSource().sendFeedback(() -> Text.literal("Reloading the config!"), true);
                            return 1;
                        })
        );
    }
}
