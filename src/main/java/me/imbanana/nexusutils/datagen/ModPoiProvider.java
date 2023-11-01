package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.tag.TagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.PointOfInterestTypeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.concurrent.CompletableFuture;

public class ModPoiProvider extends TagProvider<PointOfInterestType> {
    public ModPoiProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture) {
        super(output, RegistryKeys.POINT_OF_INTEREST_TYPE, registryLookupFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        getOrCreateTagBuilder(PointOfInterestTypeTags.ACQUIRABLE_JOB_SITE)
                .addOptional(new Identifier(NexusUtils.MOD_ID, "enchanting_table_poi"));
    }
}
