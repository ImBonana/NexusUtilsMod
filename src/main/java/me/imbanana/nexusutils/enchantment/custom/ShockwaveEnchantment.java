package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.enchantment.effects.LaunchEntityIntoAirEnchantment;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.provider.number.EnchantmentLevelLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;

public class ShockwaveEnchantment extends NexusEnchantment {
    public ShockwaveEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                        Enchantment.definition(
                                itemLookup.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                1,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(65),
                                8,
                                AttributeModifierSlot.CHEST
                        )
                ).addEffect(
                        EnchantmentEffectComponentTypes.POST_ATTACK,
                        EnchantmentEffectTarget.VICTIM,
                        EnchantmentEffectTarget.ATTACKER,
                        new LaunchEntityIntoAirEnchantment(
                                EnchantmentLevelBasedValue.constant(-2),
                                EnchantmentLevelBasedValue.constant(1)
                        ),
                        RandomChanceLootCondition.builder(EnchantmentLevelLootNumberProvider.create(EnchantmentLevelBasedValue.constant(0.2f)))
                )
        );
    }
}
