package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.entity.ApplyMobEffectEnchantmentEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.ItemTags;

public class HeadlightEnchantment extends NexusEnchantment {
    public HeadlightEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                            4,
                            1,
                            Enchantment.constantCost(5),
                            Enchantment.constantCost(20),
                            1,
                            AttributeModifierSlot.HEAD
                    )
            ).addEffect(
                    EnchantmentEffectComponentTypes.TICK,
                    new ApplyMobEffectEnchantmentEffect(
                            RegistryEntryList.of(StatusEffects.NIGHT_VISION),
                            EnchantmentLevelBasedValue.constant(1f),
                            EnchantmentLevelBasedValue.constant(1f),
                            EnchantmentLevelBasedValue.constant(0),
                            EnchantmentLevelBasedValue.constant(0)
                    )
            )
        );
    }
}
