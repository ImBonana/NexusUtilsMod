package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.enchantment.effects.StealHealthEnchantmentEffect;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.provider.number.EnchantmentLevelLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;

public class LifestealEnchantment extends NexusEnchantment {
    public LifestealEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                        Enchantment.definition(
                                itemLookup.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                                2,
                                2,
                                Enchantment.leveledCost(15, 9),
                                Enchantment.leveledCost(65, 9),
                                4,
                                AttributeModifierSlot.MAINHAND
                        )
                ).addEffect(
                        EnchantmentEffectComponentTypes.POST_ATTACK,
                        EnchantmentEffectTarget.ATTACKER,
                        EnchantmentEffectTarget.ATTACKER,
                        new StealHealthEnchantmentEffect(
                                EnchantmentLevelBasedValue.linear(3, 2)
                        ),
                        RandomChanceLootCondition.builder(EnchantmentLevelLootNumberProvider.create(EnchantmentLevelBasedValue.constant(0.25f)))
                )
        );
    }
}
