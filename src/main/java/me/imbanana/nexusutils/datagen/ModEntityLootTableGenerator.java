package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.entity.ModEntities;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantedCountIncreaseLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModEntityLootTableGenerator extends SimpleFabricLootTableProvider {
    private final RegistryWrapper.WrapperLookup registries;

    public ModEntityLootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(dataOutput, registries, LootContextTypes.ENTITY);
        this.registries = registries.join();
    }

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
        lootTableBiConsumer.accept(
                ModEntities.SNAIL.getLootTableKey().get(),
                LootTable.builder()
                    .pool(
                            LootPool.builder()
                                    .rolls(ConstantLootNumberProvider.create(3))
                                    .with(
                                            ItemEntry.builder(Items.NAUTILUS_SHELL)
                                                    .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)))
                                                    .apply(EnchantedCountIncreaseLootFunction.builder(this.registries, UniformLootNumberProvider.create(0.0F, 1.0F)))
                                                    .conditionally(RandomChanceLootCondition.builder(0.025f))
                                    )
                                    .with(
                                            ItemEntry.builder(Items.SLIME_BALL)
                                                    .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)))
                                                    .apply(EnchantedCountIncreaseLootFunction.builder(this.registries, UniformLootNumberProvider.create(1.0F, 2.0F)))
                                                    .conditionally(RandomChanceLootCondition.builder(0.2f))
                                    )
                    )
        );
    }
}
