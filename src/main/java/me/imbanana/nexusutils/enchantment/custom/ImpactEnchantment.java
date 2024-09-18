package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.value.MultiplyEnchantmentEffect;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.condition.AllOfLootCondition;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.provider.number.EnchantmentLevelLootNumberProvider;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;

public class ImpactEnchantment extends NexusEnchantment {
    public ImpactEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(ItemTags.TRIDENT_ENCHANTABLE),
                            2,
                            1,
                            Enchantment.constantCost(5),
                            Enchantment.constantCost(20),
                            2,
                            AttributeModifierSlot.MAINHAND
                    )
            ).addEffect(
                EnchantmentEffectComponentTypes.DAMAGE,
                new MultiplyEnchantmentEffect(EnchantmentLevelBasedValue.constant(2)),
                AllOfLootCondition.builder(
                        RandomChanceLootCondition.builder(EnchantmentLevelLootNumberProvider.create(EnchantmentLevelBasedValue.constant(0.10f))),
                        EntityPropertiesLootCondition.builder(LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.create().type(EntityType.TRIDENT))
                )
            )
        );
    }
}
