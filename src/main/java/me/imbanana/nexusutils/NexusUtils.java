package me.imbanana.nexusutils;

import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.block.entity.ModBlockEntities;
import me.imbanana.nexusutils.commands.ModCommands;
import me.imbanana.nexusutils.components.ModComponents;
import me.imbanana.nexusutils.configs.DiscordBotConfig;
import me.imbanana.nexusutils.configs.RealPlayersConfig;
import me.imbanana.nexusutils.effect.ModEffects;
import me.imbanana.nexusutils.enchantment.ModEnchantments;
import me.imbanana.nexusutils.entity.ModEntities;
import me.imbanana.nexusutils.entity.ModEntityAttributes;
import me.imbanana.nexusutils.entity.custom.SnailEntity;
import me.imbanana.nexusutils.events.ModEvents;
import me.imbanana.nexusutils.fluids.ModFluids;
import me.imbanana.nexusutils.item.ModItemGroups;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.networking.ModNetwork;
import me.imbanana.nexusutils.screen.ModScreenHandlers;
import me.imbanana.nexusutils.world.gen.ModWorldGen;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NexusUtils implements ModInitializer {
	public static final String MOD_ID = "nexusutils";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final DiscordBotConfig DISCORD_BOT_CONFIG = DiscordBotConfig.createAndLoad();
	public static final RealPlayersConfig REAL_PLAYERS_SERVER_CONFIG = RealPlayersConfig.createAndLoad();

	@Override
	public void onInitialize() {
		ModNetwork.registerNetwork();
		ModEvents.registerEvents();

		ModComponents.registerModComponents();
		ModItems.registerModItems();

		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();

		ModFluids.registerFluids();
		ModEntityAttributes.registerAttributes();

		ModWorldGen.generateWorldGen();
		ModItemGroups.registerItemGroups();

		ModEnchantments.registerModEnchantments();
		ModEffects.registerEffects();

		ModScreenHandlers.registerScreenHandlers();
		ModCommands.registerModCommands();

		FabricDefaultAttributeRegistry.register(ModEntities.SNAIL, SnailEntity.createSnailAttributes());
//		FabricDefaultAttributeRegistry.register(ModEntities.TRIDENT_OF_FIRE, TridentOfFireEntity);
	}

	public static Identifier idOf(String id) {
		return Identifier.of(MOD_ID, id);
	}
}