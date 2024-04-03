package me.imbanana.nexusutils.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.wispforest.owo.offline.OfflineAdvancementLookup;
import io.wispforest.owo.offline.OfflineDataLookup;
import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.UserCache;
import net.minecraft.util.Uuids;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static net.minecraft.server.command.CommandManager.*;

public class RecoverCommand implements ICommand{
    private void recoverPlayer(ServerPlayerEntity newPlayer, MinecraftServer server, String oldPlayerName) {
        UUID newPlayerUuid = newPlayer.getUuid();
        UserCache userCache = server.getUserCache();
        if(userCache != null) {
            NexusUtils.LOGGER.info("Started Proccessing");
            UUID oldPlayerUuid = Uuids.getOfflinePlayerUuid(oldPlayerName);
            newPlayer.networkHandler.disconnect(Text.literal("Recovering your items... \nPlease reconnect to the server"));
            new Thread(() -> {
                while (server.getPlayerManager().getPlayer(newPlayerUuid) != null);
                NbtCompound oldPlayerNBT = OfflineDataLookup.get(oldPlayerUuid);
                NexusUtils.LOGGER.info("(%s) %s is recovering from old account".formatted(newPlayer.getUuid(), newPlayer.getName()));
                if(oldPlayerNBT != null) {
                    Map<Identifier, AdvancementProgress> oldPlayerAdvancement = OfflineAdvancementLookup.get(oldPlayerUuid);

                    if(oldPlayerAdvancement != null) {
                        OfflineAdvancementLookup.put(newPlayerUuid, oldPlayerAdvancement);
                    }
                    OfflineDataLookup.put(newPlayerUuid, oldPlayerNBT);
                    NexusUtils.LOGGER.info("save!");
                }
            }).start();
        }
    }

    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(
                literal("recover")
                        .executes(context -> {
                            ServerPlayerEntity newPlayer = context.getSource().getPlayer();
                            if(newPlayer != null) recoverPlayer(newPlayer, context.getSource().getServer(), newPlayer.getName().getString());
                            return 1;
                        })
                        .then(argument("username", StringArgumentType.string())
                                .requires(source -> source.hasPermissionLevel(2))
                                .executes(context -> {
                                    ServerPlayerEntity newPlayer = context.getSource().getPlayer();
                                    if(newPlayer != null) recoverPlayer(newPlayer, context.getSource().getServer(), StringArgumentType.getString(context, "username"));
                                    return 1;
                                }))
        );
    }
}
