package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.enchantment.effects.LaunchEntityIntoAirEnchantment;
import me.imbanana.nexusutils.enchantment.lootConditions.EntityStatusLootCondition;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.loot.condition.AllOfLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.provider.number.EnchantmentLevelLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;

public class RocketEscapeEnchantment extends NexusEnchantment {
    public RocketEscapeEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ItemTags.LEG_ARMOR_ENCHANTABLE),
                        2,
                        1,
                        Enchantment.constantCost(10),
                        Enchantment.constantCost(25),
                        3,
                        AttributeModifierSlot.LEGS
                )
            ).addEffect(
                EnchantmentEffectComponentTypes.POST_ATTACK,
                EnchantmentEffectTarget.VICTIM,
                EnchantmentEffectTarget.VICTIM,
                new LaunchEntityIntoAirEnchantment(
                        EnchantmentLevelBasedValue.constant(-3),
                        EnchantmentLevelBasedValue.constant(3)
                ),
                AllOfLootCondition.builder(
                        EntityStatusLootCondition.builder(LootContext.EntityTarget.THIS, EntityStatusLootCondition.StatusType.HEALTH, NumberRange.DoubleRange.atMost(4)),
                        RandomChanceLootCondition.builder(EnchantmentLevelLootNumberProvider.create(EnchantmentLevelBasedValue.constant(0.25f)))
                )
            )
        );
    }
}
