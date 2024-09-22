package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.enchantment.componentTypes.ModEnchantmentEffectComponentTypes;
import me.imbanana.nexusutils.tags.ModItemTags;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;

public class ReplanterEnchantment extends NexusEnchantment {
    public ReplanterEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(ModItemTags.HOES_ENCHANTABLE),
                            1,
                            1,
                            Enchantment.constantCost(15),
                            Enchantment.constantCost(45),
                            4,
                            AttributeModifierSlot.MAINHAND
                    )
            ).addEffect(
                    ModEnchantmentEffectComponentTypes.REPLANTER
            )
        );
    }
}
