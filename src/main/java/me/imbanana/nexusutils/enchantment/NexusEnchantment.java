package me.imbanana.nexusutils.enchantment;

import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.registry.*;

public class NexusEnchantment {
    private final Function5<RegistryEntryLookup<DamageType>, RegistryEntryLookup<Enchantment>, RegistryEntryLookup<Item>, RegistryEntryLookup<Block>, RegistryEntryLookup<EntityType<?>>, Enchantment.Builder> builder;
    private final RegistryKey<Enchantment> key;

    protected NexusEnchantment(RegistryKey<Enchantment> key, Function5<RegistryEntryLookup<DamageType>, RegistryEntryLookup<Enchantment>, RegistryEntryLookup<Item>, RegistryEntryLookup<Block>, RegistryEntryLookup<EntityType<?>>, Enchantment.Builder> builder) {
        this.builder = builder;
        this.key = key;
    }

    public Enchantment.Builder getBuilder(RegistryWrapper.WrapperLookup registries) {
        RegistryEntryLookup<DamageType> damageLookup = registries.getOrThrow(RegistryKeys.DAMAGE_TYPE);
        RegistryEntryLookup<Enchantment> enchantmentLookup = registries.getOrThrow(RegistryKeys.ENCHANTMENT);
        RegistryEntryLookup<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
        RegistryEntryLookup<Block> blockLookup = registries.getOrThrow(RegistryKeys.BLOCK);
        RegistryEntryLookup<EntityType<?>> entityTypeLookup = registries.getOrThrow(RegistryKeys.ENTITY_TYPE);

        return this.builder.invoke(damageLookup, enchantmentLookup, itemLookup, blockLookup, entityTypeLookup);
    }

    public RegistryKey<Enchantment> getRegistryKey() {
        return key;
    }
}
