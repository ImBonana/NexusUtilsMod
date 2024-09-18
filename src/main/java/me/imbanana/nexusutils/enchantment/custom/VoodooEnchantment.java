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

public class VoodooEnchantment extends NexusEnchantment {
    public VoodooEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                            2,
                            1,
                            Enchantment.constantCost(10),
                            Enchantment.constantCost(35),
                            4,
                            AttributeModifierSlot.HEAD
                    )
            ).addEffect(
                EnchantmentEffectComponentTypes.POST_ATTACK,
                EnchantmentEffectTarget.VICTIM,
                EnchantmentEffectTarget.ATTACKER,
                new ApplyMobEffectEnchantmentEffect(
                        RegistryEntryList.of(StatusEffects.WEAKNESS),
                        EnchantmentLevelBasedValue.constant(6),
                        EnchantmentLevelBasedValue.constant(6),
                        EnchantmentLevelBasedValue.constant(0),
                        EnchantmentLevelBasedValue.constant(0)
                ),
                RandomChanceLootCondition.builder(EnchantmentLevelLootNumberProvider.create(EnchantmentLevelBasedValue.constant(0.10f)))
            )
        );
    }
}
