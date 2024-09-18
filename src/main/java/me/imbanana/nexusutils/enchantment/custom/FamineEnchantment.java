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

public class FamineEnchantment extends NexusEnchantment {
    public FamineEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                        Enchantment.definition(
                                itemLookup.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(5),
                                Enchantment.constantCost(20),
                                2,
                                AttributeModifierSlot.MAINHAND
                        )
                ).addEffect(
                        EnchantmentEffectComponentTypes.POST_ATTACK,
                        EnchantmentEffectTarget.ATTACKER,
                        EnchantmentEffectTarget.VICTIM,
                        new ApplyMobEffectEnchantmentEffect(
                                RegistryEntryList.of(StatusEffects.HUNGER),
                                EnchantmentLevelBasedValue.constant(7.5f),
                                EnchantmentLevelBasedValue.constant(15),
                                EnchantmentLevelBasedValue.constant(1),
                                EnchantmentLevelBasedValue.constant(1)
                        ),
                        RandomChanceLootCondition.builder(EnchantmentLevelLootNumberProvider.create(EnchantmentLevelBasedValue.constant(0.1f)))
                )
        );
    }
}
