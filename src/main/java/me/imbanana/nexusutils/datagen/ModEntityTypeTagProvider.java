package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public ModEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.EntityTypes.NO_BLEEDING)
                .add(EntityType.GUARDIAN)
                .add(EntityType.ELDER_GUARDIAN)
                .add(EntityType.PUFFERFISH)
                .add(EntityType.SILVERFISH)
                .add(EntityType.SLIME)
                .add(EntityType.MAGMA_CUBE)
                .add(EntityType.ARMADILLO)
                .add(EntityType.BEE);
    }
}
