package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;

public class PoseidonEnchantment extends NexusEnchantment {
    public PoseidonEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(ItemTags.TRIDENT_ENCHANTABLE),
                            2,
                            5,
                            Enchantment.leveledCost(4, 8),
                            Enchantment.leveledCost(25, 8),
                            4,
                            AttributeModifierSlot.MAINHAND
                    )
            ).addEffect(
                    EnchantmentEffectComponentTypes.DAMAGE,
                    new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(3F, 1.5f))
            )
        );
    }
}
