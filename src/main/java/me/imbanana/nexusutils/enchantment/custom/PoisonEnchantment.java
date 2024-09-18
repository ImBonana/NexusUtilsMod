package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.enchantment.effect.entity.ApplyMobEffectEnchantmentEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.provider.number.EnchantmentLevelLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.ItemTags;

public class PoisonEnchantment extends NexusEnchantment {
    public PoisonEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                            2,
                            2,
                            Enchantment.leveledCost(4, 8),
                            Enchantment.leveledCost(25, 8),
                            4,
                            AttributeModifierSlot.MAINHAND
                    )
            ).addEffect(
                    EnchantmentEffectComponentTypes.POST_ATTACK,
                    EnchantmentEffectTarget.ATTACKER,
                    EnchantmentEffectTarget.VICTIM,
                    new ApplyMobEffectEnchantmentEffect(
                            RegistryEntryList.of(StatusEffects.POISON),
                            EnchantmentLevelBasedValue.linear(5),
                            EnchantmentLevelBasedValue.linear(5),
                            EnchantmentLevelBasedValue.constant(0),
                            EnchantmentLevelBasedValue.constant(0)
                    ),
                    RandomChanceLootCondition.builder(EnchantmentLevelLootNumberProvider.create(EnchantmentLevelBasedValue.linear(0.10f)))
            )
        );
    }
}
