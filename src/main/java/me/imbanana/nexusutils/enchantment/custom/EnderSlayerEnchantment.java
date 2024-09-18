package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.loot.condition.LocationCheckLootCondition;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.world.World;

public class EnderSlayerEnchantment extends NexusEnchantment {
    public EnderSlayerEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                        Enchantment.definition(
                                itemLookup.getOrThrow(ItemTags.CHEST_ARMOR),
                                10,
                                5,
                                Enchantment.leveledCost(1, 11),
                                Enchantment.leveledCost(12, 11),
                                1,
                                AttributeModifierSlot.CHEST
                        )
                ).addEffect(
                        EnchantmentEffectComponentTypes.DAMAGE,
                        new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(3, 1)),
                        LocationCheckLootCondition.builder(LocationPredicate.Builder.create().dimension(World.END))
                )
        );
    }
}
