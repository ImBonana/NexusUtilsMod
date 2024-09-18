package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.enchantment.componentTypes.ModEnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;

public class PhoenixEnchantment extends NexusEnchantment {
    public PhoenixEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                        Enchantment.definition(
                                itemLookup.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(65),
                                8,
                                AttributeModifierSlot.HEAD
                        )
                ).addEffect(
                        ModEnchantmentEffectComponentTypes.PHOENIX
                        // the chances are in the living entity mixin
                )
        );
    }
}
