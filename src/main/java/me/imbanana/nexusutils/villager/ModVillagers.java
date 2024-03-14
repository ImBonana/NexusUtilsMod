package me.imbanana.nexusutils.villager;

import com.google.common.collect.ImmutableSet;
import me.imbanana.nexusutils.NexusUtils;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class ModVillagers {
    public static final  RegistryKey<PointOfInterestType> ENCHANTING_TABLE_POI_KEY = poiKey("enchanting_table_poi");

    public static final VillagerProfession ENCHANTER = registerProfession("enchanter", ENCHANTING_TABLE_POI_KEY);


    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, new Identifier(NexusUtils.MOD_ID, name), new VillagerProfession(
            name,
            entry -> entry.matchesKey(type),
            entry -> entry.matchesKey(type),
            ImmutableSet.of(),
            ImmutableSet.of(),
            SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN
        ));
    }

    private static PointOfInterestType registerPoi(String name, Block block) {
        return PointOfInterestHelper.register(new Identifier(NexusUtils.MOD_ID, name), 1, 1, block);
    }

    private static RegistryKey<PointOfInterestType> poiKey(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, new Identifier(NexusUtils.MOD_ID, name));
    }

    public static void registerVillagers() {
        NexusUtils.LOGGER.info("Registering Villagers for " + NexusUtils.MOD_ID);
    }
}
