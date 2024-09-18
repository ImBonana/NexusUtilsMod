package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.entity.ModEntityAttributes;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.AttributeEnchantmentEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;

public class JumpEnchantment extends NexusEnchantment {

    public JumpEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                        Enchantment.definition(
                                itemLookup.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                                2,
                                2,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(65),
                                8,
                                AttributeModifierSlot.FEET
                        )
                ).addEffect(
                        EnchantmentEffectComponentTypes.ATTRIBUTES,
                        new AttributeEnchantmentEffect(
                                NexusUtils.idOf("enchantment.jump"),
                                ModEntityAttributes.GENERIC_JUMP_COUNT,
                                EnchantmentLevelBasedValue.linear(1),
                                EntityAttributeModifier.Operation.ADD_VALUE
                        )
                )
        );
    }
}
