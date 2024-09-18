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
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.loot.condition.AllOfLootCondition;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.provider.number.EnchantmentLevelLootNumberProvider;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntryList;

public class PerishEnchantment extends NexusEnchantment {
    public PerishEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                        Enchantment.definition(
                                itemLookup.getOrThrow(ModItemTags.RANGED_WEAPON_ENCHANTABLE),
                                3,
                                1,
                                Enchantment.constantCost(5),
                                Enchantment.constantCost(20),
                                2,
                                AttributeModifierSlot.MAINHAND
                        )
                ).exclusiveSet(enchantmentLookup.getOrThrow(ModEnchantmentTags.CHAOS_EXCLUSIVE_SET))
                .addEffect(
                        EnchantmentEffectComponentTypes.POST_ATTACK,
                        EnchantmentEffectTarget.ATTACKER,
                        EnchantmentEffectTarget.VICTIM,
                        new ApplyMobEffectEnchantmentEffect(
                                RegistryEntryList.of(StatusEffects.WITHER),
                                EnchantmentLevelBasedValue.constant(10),
                                EnchantmentLevelBasedValue.constant(15),
                                EnchantmentLevelBasedValue.constant(0),
                                EnchantmentLevelBasedValue.constant(0)
                        ),
                        AllOfLootCondition.builder(
                                RandomChanceLootCondition.builder(EnchantmentLevelLootNumberProvider.create(EnchantmentLevelBasedValue.constant(0.10f))),
                                EntityPropertiesLootCondition.builder(LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.create().type(EntityType.TRIDENT))
                        )
                )
        );
    }
}
