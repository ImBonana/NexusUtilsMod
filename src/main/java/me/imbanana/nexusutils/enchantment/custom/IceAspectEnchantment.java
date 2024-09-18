package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.tags.ModEnchantmentTags;
import me.imbanana.nexusutils.tags.ModItemTags;
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

public class IceAspectEnchantment extends NexusEnchantment {
    public IceAspectEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(ModItemTags.ICE_ASPECT_ENCHANTABLE),
                            2,
                            2,
                            Enchantment.leveledCost(10, 20),
                            Enchantment.leveledCost(60, 20),
                            4,
                            AttributeModifierSlot.MAINHAND
                    )
            ).addEffect(
                        EnchantmentEffectComponentTypes.POST_ATTACK,
                        EnchantmentEffectTarget.ATTACKER,
                        EnchantmentEffectTarget.VICTIM,
                        new ApplyMobEffectEnchantmentEffect(
                                RegistryEntryList.of(StatusEffects.SLOWNESS),
                                EnchantmentLevelBasedValue.constant(2),
                                EnchantmentLevelBasedValue.constant(4),
                                EnchantmentLevelBasedValue.constant(0),
                                EnchantmentLevelBasedValue.constant(0)
                        ),
                        RandomChanceLootCondition.builder(EnchantmentLevelLootNumberProvider.create(EnchantmentLevelBasedValue.constant(0.20f)))
            ).exclusiveSet(enchantmentLookup.getOrThrow(ModEnchantmentTags.ICE_ASPECT_EXCLUSIVE_SET))
        );
    }
}
