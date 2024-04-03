package me.imbanana.nexusutils.configs;

import io.wispforest.owo.config.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@Config(name = "real_player-server", wrapperName = "RealPlayersConfig")
public class RealPlayersServerConfigModel {
    public List<String> realPlayers = new ArrayList<>();
}
