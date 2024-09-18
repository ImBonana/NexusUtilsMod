package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.enchantment.componentTypes.ModEnchantmentEffectComponentTypes;
import me.imbanana.nexusutils.enchantment.effects.DamageMobsFallBasedEnchantmentEffect;
import me.imbanana.nexusutils.enchantment.lootConditions.EntityFallDistanceLootCondition;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.NumberRange;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;

public class PlummetEnchantment extends NexusEnchantment {
    public PlummetEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                        Enchantment.definition(
                                itemLookup.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                                3,
                                1,
                                Enchantment.constantCost(5),
                                Enchantment.constantCost(20),
                                2,
                                AttributeModifierSlot.FEET
                        )
                ).addEffect(
                        ModEnchantmentEffectComponentTypes.ON_FALL,
                        new DamageMobsFallBasedEnchantmentEffect(
                                EnchantmentLevelBasedValue.constant(4),
                                EnchantmentLevelBasedValue.constant(1),
                                EnchantmentLevelBasedValue.constant(15)
                        ),
                        EntityFallDistanceLootCondition.builder(LootContext.EntityTarget.THIS, NumberRange.DoubleRange.atLeast(3.5))
                )
        );
    }
}
