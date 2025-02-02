package me.imbanana.nexusutils.world.gen;

import me.imbanana.nexusutils.entity.ModEntities;
import me.imbanana.nexusutils.entity.custom.SnailEntity;
import me.imbanana.nexusutils.tags.ModTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;

public class ModEntitySpawn {
    public static void addEntitySpawn() {
        BiomeModifications.addSpawn(BiomeSelectors.tag(ModTags.Biomes.SNAIL_SPAWN), SpawnGroup.AMBIENT, ModEntities.SNAIL, 20, 3, 6);

        SpawnRestriction.register(ModEntities.SNAIL, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SnailEntity::canSpawn);
    }
}
