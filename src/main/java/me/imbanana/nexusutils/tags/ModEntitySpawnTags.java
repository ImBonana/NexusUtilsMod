package me.imbanana.nexusutils.tags;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModEntitySpawnTags {
    public static final TagKey<Biome> SNAIL_SPAWN_BIOMES = TagKey.of(RegistryKeys.BIOME, new Identifier(NexusUtils.MOD_ID, "snail_spawnable_biomes"));
    public static final TagKey<Block> SNAIL_SPAWN_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(NexusUtils.MOD_ID, "snail_spawnable_blocks"));
}
