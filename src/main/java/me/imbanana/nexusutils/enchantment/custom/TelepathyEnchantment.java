package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.enchantment.componentTypes.ModEnchantmentEffectComponentTypes;
import me.imbanana.nexusutils.tags.ModItemTags;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;

public class TelepathyEnchantment extends NexusEnchantment {
    public TelepathyEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ModItemTags.TELEPATHY_ENCHANTABLE),
                        1,
                        1,
                        Enchantment.constantCost(15),
                        Enchantment.constantCost(65),
                        8,
                        AttributeModifierSlot.HAND
                )
            ).addEffect(
                    ModEnchantmentEffectComponentTypes.TELEPARHY
            )
        );
    }
}
