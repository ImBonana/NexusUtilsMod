package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.enchantment.componentTypes.ModEnchantmentEffectComponentTypes;
import me.imbanana.nexusutils.tags.ModEnchantmentTags;
import me.imbanana.nexusutils.tags.ModItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;

import java.util.ArrayList;
import java.util.List;

public class TimberEnchantment extends NexusEnchantment  {
    public TimberEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                        Enchantment.definition(
                                itemLookup.getOrThrow(ModItemTags.AXES_ENCHANTABLE),
                                1,
                                2,
                                Enchantment.leveledCost(15, 9),
                                Enchantment.leveledCost(65, 9),
                                8,
                                AttributeModifierSlot.MAINHAND
                        )
                ).addEffect(
                        ModEnchantmentEffectComponentTypes.TIMBER
                ).exclusiveSet(enchantmentLookup.getOrThrow(ModEnchantmentTags.MULTIMINING_EXCLUSIVE_SET))
        );
    }

    private final static List<Block> whitelistedBlocks = new ArrayList<>(){{
        add(Blocks.ACACIA_LOG);
        add(Blocks.STRIPPED_ACACIA_LOG);
        add(Blocks.BIRCH_LOG);
        add(Blocks.STRIPPED_BIRCH_LOG);
        add(Blocks.CHERRY_LOG);
        add(Blocks.STRIPPED_CHERRY_LOG);
        add(Blocks.DARK_OAK_LOG);
        add(Blocks.STRIPPED_DARK_OAK_LOG);
        add(Blocks.JUNGLE_LOG);
        add(Blocks.STRIPPED_JUNGLE_LOG);
        add(Blocks.MANGROVE_LOG);
        add(Blocks.STRIPPED_MANGROVE_LOG);
        add(Blocks.OAK_LOG);
        add(Blocks.DARK_OAK_LOG);
        add(Blocks.SPRUCE_LOG);
        add(Blocks.STRIPPED_SPRUCE_LOG);
        add(Blocks.MANGROVE_ROOTS);
        add(Blocks.WARPED_STEM);
        add(Blocks.STRIPPED_WARPED_STEM);
        add(Blocks.CRIMSON_STEM);
        add(Blocks.STRIPPED_CRIMSON_STEM);
    }};

    public static boolean canBreak(Block block) {
        return whitelistedBlocks.contains(block);
    }
}
