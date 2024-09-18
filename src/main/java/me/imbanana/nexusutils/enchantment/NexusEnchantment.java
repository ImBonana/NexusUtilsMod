package me.imbanana.nexusutils.enchantment;

import kotlin.jvm.functions.Function4;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.registry.*;

public class NexusEnchantment {
    private final Function4<RegistryEntryLookup<DamageType>, RegistryEntryLookup<Enchantment>, RegistryEntryLookup<Item>, RegistryEntryLookup<Block>, Enchantment.Builder> builder;
    private final RegistryKey<Enchantment> key;

    protected NexusEnchantment(RegistryKey<Enchantment> key, Function4<RegistryEntryLookup<DamageType>, RegistryEntryLookup<Enchantment>, RegistryEntryLookup<Item>, RegistryEntryLookup<Block>, Enchantment.Builder> builder) {
        this.builder = builder;
        this.key = key;
    }

    public Enchantment.Builder getBuilder(RegistryWrapper.WrapperLookup registries) {
        RegistryEntryLookup<DamageType> damageLookup = registries.getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE);
        RegistryEntryLookup<Enchantment> enchantmentLookup = registries.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        RegistryEntryLookup<Item> itemLookup = registries.getWrapperOrThrow(RegistryKeys.ITEM);
        RegistryEntryLookup<Block> blockLookup = registries.getWrapperOrThrow(RegistryKeys.BLOCK);

        return this.builder.invoke(damageLookup, enchantmentLookup, itemLookup, blockLookup);
    }

    public RegistryKey<Enchantment> getRegistryKey() {
        return key;
    }
}
