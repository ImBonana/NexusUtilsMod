package me.imbanana.nexusutils;

import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.block.entity.ModBlockEntities;
import me.imbanana.nexusutils.effect.ModEffects;
import me.imbanana.nexusutils.enchantment.ModEnchantments;
import me.imbanana.nexusutils.events.ModEvents;
import me.imbanana.nexusutils.item.ModItemGroups;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.networking.ModPackets;
import me.imbanana.nexusutils.screen.ModScreenHandlers;
import me.imbanana.nexusutils.villager.ModCustomTrades;
import me.imbanana.nexusutils.villager.ModVillagers;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NexusUtils implements ModInitializer {
	public static final String MOD_ID = "nexusutils";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEvents.registerEvents();
		ModPackets.registerC2SPackets();

		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();

		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();

		ModEnchantments.registerModEnchantments();
		ModEffects.registerEffects();

		ModVillagers.registerVillagers();
		ModCustomTrades.registerTrades();

		ModScreenHandlers.registerScreenHandlers();
	}

}