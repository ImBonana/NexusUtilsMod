package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.tags.ModEnchantmentTags;
import net.minecraft.block.Blocks;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.entity.ReplaceDiskEnchantmentEffect;
import net.minecraft.fluid.Fluids;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.Optional;

public class LavaWalkerEnchantment extends NexusEnchantment {
    public LavaWalkerEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                            2,
                            2,
                            Enchantment.leveledCost(10, 10),
                            Enchantment.leveledCost(25, 10),
                            4,
                            AttributeModifierSlot.FEET
                    )
                ).exclusiveSet(enchantmentLookup.getOrThrow(ModEnchantmentTags.LAVA_WALKER_EXCLUSIVE_SET))
                .addEffect(
                        EnchantmentEffectComponentTypes.LOCATION_CHANGED,
                        new ReplaceDiskEnchantmentEffect(
                                new EnchantmentLevelBasedValue.Clamped(EnchantmentLevelBasedValue.linear(3.0F, 1.0F), 0.0F, 16.0F),
                                EnchantmentLevelBasedValue.constant(1.0F),
                                new Vec3i(0, -1, 0),
                                Optional.of(
                                        BlockPredicate.allOf(
                                                BlockPredicate.matchingBlockTag(new Vec3i(0, 1, 0), BlockTags.AIR),
                                                BlockPredicate.matchingBlocks(Blocks.LAVA),
                                                BlockPredicate.matchingFluids(Fluids.LAVA),
                                                BlockPredicate.unobstructed()
                                        )
                                ),
                                BlockStateProvider.of(ModBlocks.FROZEN_LAVA),
                                Optional.of(GameEvent.BLOCK_PLACE)
                        ),
                        EntityPropertiesLootCondition.builder(
                                LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().flags(EntityFlagsPredicate.Builder.create().onGround(true))
                        )
                )
        );
    }
}
