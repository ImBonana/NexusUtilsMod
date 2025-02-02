package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagProvider extends FabricTagProvider<Biome> {
    public ModBiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Biomes.SNAIL_SPAWN)
                .add(BiomeKeys.MANGROVE_SWAMP)
                .add(BiomeKeys.SWAMP)
                .add(BiomeKeys.BAMBOO_JUNGLE)
                .add(BiomeKeys.JUNGLE)
                .add(BiomeKeys.SPARSE_JUNGLE)
                .add(BiomeKeys.FOREST)
                .add(BiomeKeys.FLOWER_FOREST)
                .add(BiomeKeys.BIRCH_FOREST)
                .add(BiomeKeys.OLD_GROWTH_BIRCH_FOREST);
    }
}
