package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.enchantment.effects.LaunchEntityToEntityEnchantmentEffect;
import me.imbanana.nexusutils.tags.ModTags;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.EntityTypeTags;

public class AttractionEnchantment extends NexusEnchantment {
    public AttractionEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup, entityTypeLookup) -> Enchantment.builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ModTags.Items.RANGED_PROJECTILE_ENCHANTABLE),
                        2,
                        2,
                        Enchantment.leveledCost(1, 10),
                        Enchantment.leveledCost(16, 10),
                        1,
                        AttributeModifierSlot.MAINHAND
                )
            ).addEffect(
                EnchantmentEffectComponentTypes.POST_ATTACK,
                EnchantmentEffectTarget.ATTACKER,
                EnchantmentEffectTarget.VICTIM,
                new LaunchEntityToEntityEnchantmentEffect(EnchantmentLevelBasedValue.linear(0.5F)),
                EntityPropertiesLootCondition.builder(LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.create().type(entityTypeLookup, EntityTypeTags.ARROWS).build())
            ).exclusiveSet(enchantmentLookup.getOrThrow(ModTags.Enchantments.ATTRACTION_EXCLUSIVE_SET))
        );
    }
}
