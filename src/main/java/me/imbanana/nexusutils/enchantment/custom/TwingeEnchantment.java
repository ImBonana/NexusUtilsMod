package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.effect.ModEffects;
import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.enchantment.effect.entity.ApplyMobEffectEnchantmentEffect;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.ItemTags;

public class TwingeEnchantment extends NexusEnchantment {
    public TwingeEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(ItemTags.TRIDENT_ENCHANTABLE),
                            2,
                            3,
                            Enchantment.constantCost(5),
                            Enchantment.constantCost(20),
                            2,
                            AttributeModifierSlot.MAINHAND
                    )
            )
            .addEffect(
                    EnchantmentEffectComponentTypes.POST_ATTACK,
                    EnchantmentEffectTarget.ATTACKER,
                    EnchantmentEffectTarget.VICTIM,
                    new ApplyMobEffectEnchantmentEffect(
                            RegistryEntryList.of(ModEffects.BLEED),
                            EnchantmentLevelBasedValue.linear(2, 4),
                            EnchantmentLevelBasedValue.linear(3, 4),
                            EnchantmentLevelBasedValue.constant(0),
                            EnchantmentLevelBasedValue.constant(0)
                    )
            )
        );
    }
}
