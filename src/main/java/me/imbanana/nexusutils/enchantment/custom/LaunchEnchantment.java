package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.enchantment.componentTypes.ModEnchantmentEffectComponentTypes;
import me.imbanana.nexusutils.enchantment.effects.ItemCooldownEnchantmentEffect;
import me.imbanana.nexusutils.enchantment.effects.LaunchEntityForwardEnchantmentEffect;
import me.imbanana.nexusutils.enchantment.effects.SwingHandEnchantmentEffect;
import me.imbanana.nexusutils.enchantment.lootConditions.ItemCooldownLootCondition;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.AllOfEnchantmentEffects;
import net.minecraft.loot.context.LootContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;

public class LaunchEnchantment extends NexusEnchantment {
    public LaunchEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                        Enchantment.definition(
                                itemLookup.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                                2,
                                2,
                                Enchantment.leveledCost(5, 9),
                                Enchantment.leveledCost(20, 9),
                                4,
                                AttributeModifierSlot.HAND
                        )
                ).addEffect(
                        ModEnchantmentEffectComponentTypes.ON_RIGHT_CLICK,
                        AllOfEnchantmentEffects.allOf(
                                new LaunchEntityForwardEnchantmentEffect(EnchantmentLevelBasedValue.linear(1f)),
                                new ItemCooldownEnchantmentEffect(EnchantmentLevelBasedValue.linear(50, 20)),
                                new SwingHandEnchantmentEffect()
                        ),
                    ItemCooldownLootCondition.builder(
                            LootContext.EntityTarget.THIS,
                            false
                    )
                )
        );
    }
}
