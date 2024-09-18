package me.imbanana.nexusutils.discord;

import me.imbanana.nexusutils.NexusUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.UserCache;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;

public class DiscordBot {
    public static JDA DISCORD_BOT;

    public static void initializeDiscordBot(MinecraftServer server) {
        String token = NexusUtils.DISCORD_BOT_CONFIG.token();
        String guildId = NexusUtils.DISCORD_BOT_CONFIG.guildId();
        if (token.isBlank()) {
            NexusUtils.LOGGER.error("<NexusUtils | DiscordBot> Failed to initialize: Invalid token");
            return;
        }

        if(guildId.isBlank()) {
            NexusUtils.LOGGER.error("<NexusUtils | DiscordBot> Failed to initialize: Invalid guildId");
            return;
        }

        //        DiscordBotConfig
        DISCORD_BOT = JDABuilder.createDefault(token)
                .addEventListeners((EventListener) event -> {
                    if (event instanceof ReadyEvent) {
                        NexusUtils.LOGGER.info("<NexusUtils | DiscordBot> Logged In As {}#{}", event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getDiscriminator());

                        Guild guild = DISCORD_BOT.getGuildById(guildId);
                        if (guild != null) {
                            guild.updateCommands().addCommands(
                                    Commands.slash("ping", "Ping the bot")
                                            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR)),
                                    Commands.slash("im-real", "If you are playing on premium minecraft.")
                                            .addOption(OptionType.STRING, "minecraft_username", "Your minecraft username", true),
                                    Commands.slash("im-fake", "If you are playing on cracked minecraft.")
                                            .addOption(OptionType.STRING, "minecraft_username", "Your minecraft username", true)
                            ).onSuccess(commands -> NexusUtils.LOGGER.info("<NexusUtils | DiscordBot> Added all slash commands (%s)".formatted(commands.size()))).queue();
                        }
                    }
                })
                .addEventListeners(new ListenerAdapter() {
                    @Override
                    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
                        switch (event.getName()) {
                            case "im-real": {
                                if (server == null) break;
                                String username = event.getOption("minecraft_username", OptionMapping::getAsString);

                                UserCache userCache = server.getUserCache();
                                if (userCache == null) break;
                                if (userCache.findByName(username).isEmpty()) {
                                    event.reply("Please connect to the server with this username first!").setEphemeral(true).queue();
                                    return;
                                }

                                List<String> rawRealPlayers = NexusUtils.REAL_PLAYERS_SERVER_CONFIG.realPlayers();
                                HashSet<String> realPlayers = new HashSet<>(rawRealPlayers);

                                if (realPlayers.add(username)) {
                                    NexusUtils.REAL_PLAYERS_SERVER_CONFIG.realPlayers(realPlayers.stream().toList());
                                    NexusUtils.REAL_PLAYERS_SERVER_CONFIG.save();
                                }
                                event.reply("Success!").setEphemeral(true).queue();
                                return;
                            }

                            case "im-fake": {
                                String username = event.getOption("minecraft_username", OptionMapping::getAsString);

                                List<String> rawRealPlayers = NexusUtils.REAL_PLAYERS_SERVER_CONFIG.realPlayers();
                                HashSet<String> realPlayers = new HashSet<>(rawRealPlayers);


                                if (realPlayers.remove(username)) {
                                    NexusUtils.REAL_PLAYERS_SERVER_CONFIG.realPlayers(realPlayers.stream().toList());
                                    NexusUtils.REAL_PLAYERS_SERVER_CONFIG.save();
                                }

                                event.reply("Success!").setEphemeral(true).queue();
                                return;
                            }

                            case "ping": {
                                long time = System.currentTimeMillis();
                                event.reply("Pong!").setEphemeral(true)
                                        .flatMap(v ->
                                                event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) // then edit original
                                        ).queue();
                                return;
                            }

                            default: {
                                event.reply("Unknown command '%s'".formatted(event.getName())).setEphemeral(true).queue();
                            }
                        }

                        event.reply("Something went wrong, Please try again later.").setEphemeral(true).queue();
                    }
                })
                .setActivity(Activity.playing("Minecraft!"))
                .build();
    }
}
