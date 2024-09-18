package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.DamageImmunityEnchantmentEffect;
import net.minecraft.loot.condition.AllOfLootCondition;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.provider.number.EnchantmentLevelLootNumberProvider;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;

public class ProjectileDeflectEnchantment extends NexusEnchantment {
    public ProjectileDeflectEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ItemTags.LEG_ARMOR_ENCHANTABLE),
                        5,
                        4,
                        Enchantment.leveledCost(3, 8),
                        Enchantment.leveledCost(9, 8),
                        1,
                        AttributeModifierSlot.LEGS
                )
            ).addEffect(
                EnchantmentEffectComponentTypes.DAMAGE_IMMUNITY,
                DamageImmunityEnchantmentEffect.INSTANCE,
                AllOfLootCondition.builder(
                        DamageSourcePropertiesLootCondition.builder(
                                DamageSourcePredicate.Builder.create()
                                        .tag(TagPredicate.expected(DamageTypeTags.IS_PROJECTILE))
                                        .tag(TagPredicate.unexpected(DamageTypeTags.BYPASSES_INVULNERABILITY))
                        ),
                        RandomChanceLootCondition.builder(EnchantmentLevelLootNumberProvider.create(EnchantmentLevelBasedValue.linear(0.15f))))
            )
        );
    }
}
