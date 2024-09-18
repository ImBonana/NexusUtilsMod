package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.AttributeEnchantmentEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;

public class ExtraHealthEnchantment extends NexusEnchantment {
    public ExtraHealthEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                        Enchantment.definition(
                                itemLookup.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                3,
                                5,
                                Enchantment.leveledCost(25, 9),
                                Enchantment.leveledCost(65, 9),
                                8,
                                AttributeModifierSlot.ARMOR
                        )
                ).addEffect(
                        EnchantmentEffectComponentTypes.ATTRIBUTES,
                        new AttributeEnchantmentEffect(
                                NexusUtils.idOf("enchantment.extra_health"),
                                EntityAttributes.GENERIC_MAX_HEALTH,
                                EnchantmentLevelBasedValue.linear(1f),
                                EntityAttributeModifier.Operation.ADD_VALUE
                        )
                )
        );
    }
}
