package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.enchantment.lootConditions.DayTimeLootCondition;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;

public class NightOwlEnchantment extends NexusEnchantment {
    public NightOwlEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                            2,
                            1,
                            Enchantment.leveledCost(5, 9),
                            Enchantment.leveledCost(20, 9),
                            4,
                            AttributeModifierSlot.MAINHAND
                    )
            ).addEffect(
                    EnchantmentEffectComponentTypes.DAMAGE,
                    new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(3, 1)),
                    DayTimeLootCondition.builder(DayTimeLootCondition.DayTime.NIGHT)
            )
        );
    }
}
