package me.imbanana.nexusutils.configs;

import io.wispforest.owo.config.annotation.Config;

@Config(name = "discord-bot-server", wrapperName = "DiscordBotConfig")
public class DiscordBotConfigModal {
    public String guildId = "";
    public String token = "";
}
