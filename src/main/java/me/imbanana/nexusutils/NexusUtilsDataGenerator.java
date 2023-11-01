package me.imbanana.nexusutils;

import me.imbanana.nexusutils.datagen.ModBlockTagProvider;
import me.imbanana.nexusutils.datagen.ModLootTableProvider;
import me.imbanana.nexusutils.datagen.ModPoiProvider;
import me.imbanana.nexusutils.datagen.ModRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class NexusUtilsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModPoiProvider::new);
	}
}
