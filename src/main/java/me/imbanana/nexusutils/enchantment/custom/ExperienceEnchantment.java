package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.tags.ModItemTags;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.value.MultiplyEnchantmentEffect;
import net.minecraft.registry.RegistryKey;

public class ExperienceEnchantment extends NexusEnchantment {
    public ExperienceEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(ModItemTags.EXPERIENCE_ENCHANTABLE),
                            2,
                            3,
                            Enchantment.leveledCost(15, 9),
                            Enchantment.leveledCost(65, 9),
                            4,
                            AttributeModifierSlot.MAINHAND
                    )
            ).addEffect(
                    EnchantmentEffectComponentTypes.MOB_EXPERIENCE,
                    new MultiplyEnchantmentEffect(EnchantmentLevelBasedValue.linear(1.3f, 0.1f))
            ).addEffect(
                    EnchantmentEffectComponentTypes.BLOCK_EXPERIENCE,
                    new MultiplyEnchantmentEffect(EnchantmentLevelBasedValue.linear(1.3f, 0.1f))
            )
        );
    }
}
